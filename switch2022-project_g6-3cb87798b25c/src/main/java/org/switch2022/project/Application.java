package org.switch2022.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application class is the entry point for the Spring Boot application.
 * It initializes and runs the application using Spring Boot's SpringApplication.
 */
@SpringBootApplication
public class Application {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
