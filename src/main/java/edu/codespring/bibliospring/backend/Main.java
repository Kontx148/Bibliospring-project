package edu.codespring.bibliospring.backend;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.service.ServiceException;
import edu.codespring.bibliospring.backend.service.UserServiceInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    @Autowired
    private UserServiceInterface userService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void initialize() {
        User u1 = new User();

//        u1.setUsername("admin_52");
//        u1.setPassword("kadcsa");
//
//        try {
//            userService.register(u1);
//        } catch (ServiceException e) {
//
//            System.out.println("REgistration error: " + e.getMessage());
//        }
//        try {
//            boolean k = userService.login(u1);
//            if (k == true) {
//                System.out.println("sikeresen beloginelt" + u1);
//            } else {
//                System.out.println("Nem loginelt be!");
//            }
//
//        } catch (ServiceException e) {
//            System.out.println("Login error: " + e.getMessage());
//        }

    }
}