package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import org.example.controller.PingController;
import org.example.controller.AuthController;
import org.example.controller.BookController;
import org.example.controller.UserController;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "org.example.controller")
// BookController.class, UserController.class, AuthController.class
@Import({ PingController.class, BookController.class, UserController.class, AuthController.class  })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}