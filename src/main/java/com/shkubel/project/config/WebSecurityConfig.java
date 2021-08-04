package com.shkubel.project.config;

import com.shkubel.project.service.oidcService.CustomOidUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOidUserService oauthUserService;

    @Autowired
    private OAth2LoginSuccessHandler oAth2LoginSuccessHandler;


    private final CustomAuthProvider customAuthProvider;

    public WebSecurityConfig(CustomAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .oidcUserService(oauthUserService)
                .and()
                .successHandler(oAth2LoginSuccessHandler);

    }


//                .csrf()
//                .disable()
////                .addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/", "/hotels/**", "/index").permitAll()
//                .antMatchers("/users/new", "/users/activate/*", "/forgot_password", "/reset_password", "/login/google").not().fullyAuthenticated()
//                .antMatchers("/administrator/**").hasRole("ADMIN")
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()

        @Override
        protected void configure (AuthenticationManagerBuilder auth){
            auth.authenticationProvider(customAuthProvider);
        }

        @Override
        public void configure (WebSecurity web){
            web.ignoring().antMatchers(
                    "/resources/css/**",
                    "/resources/js/**",
                    "/resources/img/**",
                    "/resources/fonts/**",
                    "/resources/icon/**");
        }


    }


