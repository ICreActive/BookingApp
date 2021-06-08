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

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername().toLowerCase(Locale.ROOT));
        if (userFromDB != null && !userFromDB.equals(user)) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(Long userId, User user) {
        if (userRepository.findById(userId).isPresent()) {
            User userInDB = userRepository.findById(userId).get();
            userInDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(userInDB);
            return true;
        }
        return false;
    }

}
