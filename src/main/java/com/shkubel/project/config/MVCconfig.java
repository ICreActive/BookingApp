package com.shkubel.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCconfig implements WebMvcConfigurer {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("**/login").setViewName("login");
        registry.addViewController("/").setViewName("/static/home");
        registry.addViewController("/administrator").setViewName("/users/administrator");
        registry.addViewController("/about").setViewName("/info/about-us");
        registry.addViewController("/home").setViewName("/users/userPage");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file//" + uploadPath + "/");

    }


}

