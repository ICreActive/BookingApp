package com.shkubel.project.service;

import com.shkubel.project.models.Role;
import com.shkubel.project.models.User;
import com.shkubel.project.repo.RoleRepository;
import com.shkubel.project.repo.UserRepository;
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
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById (Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers () {
        return userRepository.findAll();
    }

    public boolean saveUser (User user) {
        User userFromDB = userRepository.findUsersByUsername(user.getUsername().toLowerCase(Locale.ROOT));
        if (userFromDB != null && !userFromDB.equals(user)) {
            return false;
        }

        user.setRoles (Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser (Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean updateUser (Long userId, User user) {
        if (userRepository.findById(userId).isPresent()) {
            User userInDB = userRepository.findById(userId).get();
            userInDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(userInDB);
            return true;
        }
        return false;
    }
}
