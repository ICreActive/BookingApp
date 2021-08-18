package com.shkubel.project.service.impl;

import com.shkubel.project.exception.UnuniqueUserException;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.log.InjectLogger;
import com.shkubel.project.models.entity.Provider;
import com.shkubel.project.models.entity.Role;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.oidc.CustomOidUser;
import com.shkubel.project.dao.UserRepository;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.DateTimeParser;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @InjectLogger
    private static Logger LOGGER;

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;

    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email.toLowerCase(Locale.ROOT));
        if (user == null) {
            LOGGER.info("User with email: {} not found", email);
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean saveUser(User user) throws UnuniqueUserException {
        User userFromDB;
        userFromDB = userRepository.findUserByUsername(user.getUsername().toLowerCase(Locale.ROOT));
        if (userFromDB != null) {
            LOGGER.info("User with name '{}' present in DB", userFromDB.getUsername());
            throw new UnuniqueUserException("User with the same name already exists");
        }

        userFromDB = userRepository.findUserByEmail(user.getEmail().toLowerCase(Locale.ROOT));
        if (userFromDB != null) {
            if (userFromDB.getProvider() != null && userFromDB.getProvider().equals(Provider.GOOGLE)) {
                userFromDB.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
                userFromDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userFromDB.setAddress(user.getAddress());
                userFromDB.setUpdatingDate(DateTimeParser.nowToString());
                userRepository.save(userFromDB);
                LOGGER.info("User with name '{}' has been updated ", userFromDB.getUsername());
                return true;
            }
            LOGGER.warn("User with email '{}' present in DB", user.getUsername());
            throw new UnuniqueUserException("User already exists");
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        LOGGER.info("User with name '{}' has been saved", user.getUsername());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) throws UserNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setUserActive(false);
        user.setUpdatingDate(DateTimeParser.nowToString());
        LOGGER.info("User with id: {} has been disabled", userId);
        return true;
    }


    @Override
    @Transactional
    public void updateUser(Long userId, User user) throws UserNotFoundException {
        if (user.getId().equals(userId)) {
            User userInDB = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
            userInDB.setUserFirstname(user.getUserFirstname());
            userInDB.setUserLastname(user.getUserLastname());
            userInDB.setAddress(user.getAddress());
            userInDB.setUpdatingDate(DateTimeParser.nowToString());
            userRepository.save(userInDB);
            LOGGER.info("User with id {} has been successfully updated", userId);
        } else {
            LOGGER.warn("id {} dosn't match user id: {}", userId, user.getId());
            throw new UserNotFoundException("Unknown user");
        }
    }


    @Override
    public List<User> findUsersByStatusActive() {
        return userRepository.findUsersByUserActive();
    }

    @Override
    @Transactional
    public void restoreUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setUserActive(true);
        user.setUpdatingDate(DateTimeParser.nowToString());
        LOGGER.info("User with id{} has been activated", userId);
    }


    @Transactional
    @Override
    public boolean activateUser(String code) throws UserNotFoundException {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null) {
            LOGGER.info("Activation code is null or invalid");
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        user.setActivationCode(null);
        user.setUserActive(true);
        userRepository.save(user);
        LOGGER.info("User with id{} has been successfully activated", user.getId());
        return true;
    }

    @Override
    @Transactional
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
            LOGGER.info("ResetPasswordToken has been assigned to the user with id:{}", user.getId());
        } else {
            LOGGER.info("Could not find any user with the email {}", email);
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findUserByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
        LOGGER.info("The password of the user with id {} has been updated", user.getId());
    }

    @Override
    public User findUserByUserName(String username) throws UserNotFoundException {

        User user;
        user = userRepository.findUserByUsername(username);
        if (user == null) {
            user = userRepository.findUserByProvId(username);
            if (user == null) {
                LOGGER.info("User with username: {} not found in DB", username);
                throw new UserNotFoundException(USER_NOT_FOUND);
            }
        }
        return user;
    }


    @Override
    public List<User> findAdmins() {
        return userRepository.findAdmins();
    }

    /**
     * Auto registration
     * The method creates a user in the database upon successful authorization through Google.
     * If a user with this email is already present in the database, sets an ID for him to login
     * @param oidUser - user from token
     */
    @Override
    public void processOAuthPostLogin(CustomOidUser oidUser) {

        String email = oidUser.getEmail();
        String sub = oidUser.getAttribute("sub");

        User existUser = userRepository.findUserByEmail(email);

        if (existUser == null) {
            LOGGER.info("User with email: {} not found in DB", email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(bCryptPasswordEncoder.encode(sub));
            newUser.setUsername(oidUser.getAttribute("sub"));
            newUser.setUserFirstname(oidUser.getAttribute("given_name"));
            newUser.setUserLastname(oidUser.getAttribute("family_name"));
            newUser.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            newUser.setProvider(Provider.GOOGLE);
            newUser.setProvId(oidUser.getAttribute("sub"));
            newUser.setUserActive(true);
            newUser.setCreatingDate(DateTimeParser.nowToString());
            userRepository.save(newUser);
            LOGGER.info("New user with email: {}, has been saved in DB with Provider {}", email, newUser.getProvider().name());
        } else if (existUser.getProvider() == null) {
            existUser.setProvId(sub);
            userRepository.save(existUser);
            LOGGER.info("User with email: {}, has been updated in DB (updated field: ProvId)", email);
        }
        LOGGER.info("User with email: {}, has been successfully loading", email);
    }
}
