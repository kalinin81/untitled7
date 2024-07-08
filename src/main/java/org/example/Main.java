package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.example");
        UserService userService = applicationContext.getBean(UserService.class);
        User user = userService.create("user1");
        user = userService.read(user.getId());
        List<User> users = userService.readAll();
        userService.delete(user.getId());
    }
}