package com.shkubel.project.config;

import com.shkubel.project.log.InjectLogger;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.security.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @InjectLogger
    private static Logger LOGGER;

    private final UserDetailServiceImpl userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthProvider(UserDetailServiceImpl userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


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
