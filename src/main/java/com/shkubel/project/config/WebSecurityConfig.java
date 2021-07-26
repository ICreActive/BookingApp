package com.shkubel.project.config;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.impl.UserSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;


@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;


    private final CustomAuthProvider customAuthProvider;

    public WebSecurityConfig(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/hotels/**", "/index").permitAll()
                .antMatchers("/users/new", "/users/activate/*", "/forgot_password", "/reset_password", "/login/google").not().fullyAuthenticated()
                .antMatchers("/administrator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .logout()
                .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/resources/css/**",
                "/resources/js/**",
                "/resources/img/**",
                "/resources/fonts/**",
                "/resources/icon/**");
    }


    @Bean
    public PrincipalExtractor principalExtractor(UserSecurityServiceImpl userSecurityService) {
        return map -> {
            String email = (String) map.get("email");
            UserDetails user;
            user = userSecurityService.loadUserByUsername(email);
            if (user == null) {
                User newUser = new User();
                newUser.setUserFirstname((String) map.get("given_name"));
                newUser.setUserLastname((String) map.get("family_name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setUserFirstname((String) map.get("given_name"));
                return newUser;
            }

            user.getAuthorities();

            return user;
        };
    }
}

