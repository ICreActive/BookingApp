package com.shkubel.project.config;

import com.shkubel.project.service.security.CustomOidUserService;
import com.shkubel.project.service.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOidUserService oauthUserService;

    private final UserDetailServiceImpl userSecurityService;

    private final OAth2LoginSuccessHandler oAth2LoginSuccessHandler;

    private final CustomAuthProvider customAuthProvider;

    public WebSecurityConfig(CustomAuthProvider customAuthProvider, CustomOidUserService oauthUserService, UserDetailServiceImpl userSecurityService, OAth2LoginSuccessHandler oAth2LoginSuccessHandler) {
        this.customAuthProvider = customAuthProvider;
        this.oauthUserService = oauthUserService;
        this.userSecurityService = userSecurityService;
        this.oAth2LoginSuccessHandler = oAth2LoginSuccessHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .antMatchers("/hotels/**", "/index").permitAll()
                .antMatchers("/users/new", "/users/activate/*", "/forgot_password", "/reset_password", "/login/google").not().fullyAuthenticated()
                .antMatchers("/administrator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .oidcUserService(oauthUserService)
                .and()
                .successHandler(oAth2LoginSuccessHandler);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider)
                .userDetailsService(userSecurityService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/resources/css/**",
                "/resources/js/**",
                "/resources/img/**",
                "/resources/fonts/**",
                "/resources/icon/**");
    }


}


