package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    /**
     * The main method serves as the entry point for the Spring Boot application.
     * It launches the application using the SpringApplication class.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
