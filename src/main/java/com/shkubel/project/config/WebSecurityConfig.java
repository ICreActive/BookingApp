package com.shkubel.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomAuthProvider customAuthProvider;


    public WebSecurityConfig(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;

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
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthProvider);
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

//
//    @Bean
//    public PrincipalExtractor principalExtractor(UserSecurityServiceImpl userSecurityService) {
//        return map -> {
//            String email = (String) map.get("email");
//            UserDetails user;
//            user = userSecurityService.loadUserByUsername(email);
//            if (user == null) {
//                User newUser = new User();
//                newUser.setUserFirstname((String) map.get("given_name"));
//                newUser.setUserLastname((String) map.get("family_name"));
//                newUser.setEmail((String) map.get("email"));
//                newUser.setUserFirstname((String) map.get("given_name"));
//                return newUser;
//            }
//            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
//
//        };
//    }
}

