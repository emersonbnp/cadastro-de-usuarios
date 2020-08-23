package com.cadastrodeusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        String serverPort = app.getEnvironment().getProperty("server.port");
        System.out.println("\nswagger-ui: http://localhost:" +
                serverPort + "/swagger-ui.html\n" +
                "api: http://localhost:" + serverPort);
    }
}
