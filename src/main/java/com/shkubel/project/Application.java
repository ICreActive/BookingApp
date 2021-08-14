package com.shkubel.project;

import com.shkubel.project.log.InjectLogger;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    @InjectLogger
    private static Logger LOGGER;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

        LOGGER.info("Application successfully started");
    }

}
