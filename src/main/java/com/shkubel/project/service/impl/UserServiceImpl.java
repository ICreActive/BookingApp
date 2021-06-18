package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.Role;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.repo.RoleRepository;
import com.shkubel.project.models.repo.UserRepository;
import com.shkubel.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public List <User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername().toLowerCase(Locale.ROOT));
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        user.setUserActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            user.setUserActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateUser(Long userId, User user) {
        if (userRepository.findById(userId).isPresent()) {
            User userInDB = userRepository.findById(userId).get();
            userInDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(userInDB);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findUsersByUserActive(@NotNull boolean userActive) {
        return userRepository.findUsersByUserActive();
    }

}
