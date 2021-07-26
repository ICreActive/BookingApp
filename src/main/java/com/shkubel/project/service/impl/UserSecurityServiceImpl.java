package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;


    public UserSecurityServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        user = userRepository.findUserByUsername(login);
        if (user == null) {
            user = userRepository.findUserByEmail(login);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
        }
        return user;
    }

}
