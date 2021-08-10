package com.shkubel.project.config;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.security.UserDetailServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LogManager.getLogger(CustomAuthProvider.class.getName());

    @Autowired
    private UserDetailServiceImpl userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = (User) userService.loadUserByUsername(username);
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (!user.isUserActive()) {
                throw new AuthenticationServiceException("user is not active");
            }
            LOGGER.info("User with id:{} has been authorized, role:{}", user.getId(), user.getAuthorities());
            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        }
        throw new AuthenticationServiceException("Invalid credentials.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
