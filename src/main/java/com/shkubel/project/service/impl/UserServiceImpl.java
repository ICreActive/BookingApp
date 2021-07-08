package com.shkubel.project.service.impl;

import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.Role;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.repo.UserRepository;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.DateTimeParser;
import com.shkubel.project.util.MailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final MailSender mailSender;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, MailSender mailSender) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email.toLowerCase(Locale.ROOT));
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
        if (userFromDB != null)
            return false;

        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        String message = String.format(
                "Hello, %s! \n + Welcome to BookingService." +
                        "Please, visit next link: http://localhost:8080/users/activate/%s",
                user.getUserFirstname(),
                user.getActivationCode());

        mailSender.send(user.getEmail(), "Activation code", message);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseGet(() -> {
            try {
                throw new Exception("User not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        if (user != null) {
            user.setUserActive(false);
            user.setUpdatingDate(DateTimeParser.nowToString());
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean updateUser(Long userId, User user) {
        if (user.getId().equals(userId)) {
            if (userRepository.findById(userId).isPresent()) {
                User userInDB = userRepository.findById(userId).get();
                userInDB.setUserFirstname(user.getUserFirstname());
                userInDB.setUserLastname(user.getUserLastname());
                userInDB.setAddress(user.getAddress());
                userInDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userInDB.setUpdatingDate(DateTimeParser.nowToString());
                userRepository.save(userInDB);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<User> findUsersByStatus(@NotNull boolean userActive) {
        return userRepository.findUsersByUserActive();
    }

    @Override
    @Transactional
    public boolean restoreUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setUserActive(true);
            user.setUpdatingDate(DateTimeParser.nowToString());
            return true;
        }
        return false;
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public boolean activateUser(String code) {
        User user = userRepository.findUserByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setUserActive(true);
        userRepository.save(user);
        return true;

    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findUserByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
