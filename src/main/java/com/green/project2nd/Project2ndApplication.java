package com.green.project2nd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Project2ndApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project2ndApplication.class, args);
    }

}
