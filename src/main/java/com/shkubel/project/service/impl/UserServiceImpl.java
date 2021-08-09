package com.shkubel.project.service.impl;

import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.Provider;
import com.shkubel.project.models.entity.Role;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.oidc.CustomOidUser;
import com.shkubel.project.models.repo.UserRepository;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.DateTimeParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;

    }

    @Override
    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email.toLowerCase(Locale.ROOT));
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB;
        userFromDB = userRepository.findUserByUsername(user.getUsername().toLowerCase(Locale.ROOT));
        if (userFromDB != null)
            return false;

        userFromDB = userRepository.findUserByEmail(user.getEmail().toLowerCase(Locale.ROOT));
        if (userFromDB != null) {
            if (userFromDB.getProvider().equals(Provider.GOOGLE)) {
                userFromDB.setPassword(user.getPassword());
                userFromDB.setUpdatingDate(DateTimeParser.nowToString());
                return true;
            }
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) throws UserNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setUserActive(false);
        user.setUpdatingDate(DateTimeParser.nowToString());
        return true;
    }


    @Override
    @Transactional
    public void updateUser(Long userId, User user) throws UserNotFoundException {
        if (user.getId().equals(userId)) {
            User userInDB = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
            userInDB.setUserFirstname(user.getUserFirstname());
            userInDB.setUserLastname(user.getUserLastname());
            userInDB.setAddress(user.getAddress());
            userInDB.setUpdatingDate(DateTimeParser.nowToString());
            userRepository.save(userInDB);
        } else {
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
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setUserActive(true);
        user.setUpdatingDate(DateTimeParser.nowToString());
    }


    @Transactional
    @Override
    public boolean activateUser(String code) throws UserNotFoundException {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setActivationCode(null);
        user.setUserActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
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
    }

    @Override
    public User findUserByUserName(String username) throws UserNotFoundException {

        User user;
        user = userRepository.findUserByUsername(username);
        if (user == null) {
            user = userRepository.findUserByProvId(username);
            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
        }
        return user;
    }


    @Override
    public List<User> findAdmins() {
        return userRepository.findAdmins();
    }

    @Override
    public void processOAuthPostLogin(CustomOidUser oidUser) {

        String email = oidUser.getEmail();
        String sub = oidUser.getAttribute("sub");

        User existUser = userRepository.findUserByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(bCryptPasswordEncoder.encode(sub));
            newUser.setUsername(oidUser.getAttribute("sub"));
            newUser.setUserFirstname(oidUser.getAttribute("given_name"));
            newUser.setUserLastname(oidUser.getAttribute("family_name"));
            newUser.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            newUser.setProvider(Provider.GOOGLE);
            newUser.setUserActive(true);
            newUser.setCreatingDate(DateTimeParser.nowToString());
            userRepository.save(newUser);
        } else if (existUser.getProvider().equals(Provider.GOOGLE)) {
            existUser.setProvId(sub);
        }
    }
}
