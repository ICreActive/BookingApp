package com.shkubel.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCconfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("**/login").setViewName("login");
        registry.addViewController("/").setViewName("/static/home");
        registry.addViewController("/administrator").setViewName("/users/administrator");
        registry.addViewController("/about").setViewName("/info/about-us");
        registry.addViewController("/home").setViewName("/users/userPage");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

