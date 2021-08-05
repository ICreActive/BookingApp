package com.shkubel.project.config;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.oidc.CustomOidUser;
import com.shkubel.project.service.UserService;
import com.shkubel.project.service.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    private UserDetailServiceImpl userSecurityService;

    public OAth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        CustomOidUser oidcUser = (CustomOidUser) authentication.getPrincipal();
        userService.processOAuthPostLogin(oidcUser);

//        User user = (User) userSecurityService.loadUserByUsername(oidcUser.getAttribute("email"));
//        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
