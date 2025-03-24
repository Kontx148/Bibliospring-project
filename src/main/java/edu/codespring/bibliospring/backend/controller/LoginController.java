
package edu.codespring.bibliospring.backend.controller;

import edu.codespring.bibliospring.backend.model.User;
import edu.codespring.bibliospring.backend.service.impl.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends HttpServlet {
    @Autowired
    private Logger LOG;

    @Autowired
    private UserService userService;

    @GetMapping("Bibliospring/api/login")
    public ResponseEntity<String> doGet(@RequestParam(required = false)String username,
                                        @RequestParam(required = false)String password) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);

            try {
                boolean isAuthenticated = this.userService.login(user);
                LOG.info("User login status : " + isAuthenticated);
                return ResponseEntity.ok(String.valueOf(isAuthenticated));
            } catch (Exception e) {
                LOG.error("Login error", e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login error");
            }

        } else {
            LOG.info("User provided no username or password for login");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password not provided");
        }
    }

    @PostMapping("Bibliospring/api/login")
    public ResponseEntity<String> doPost(@RequestParam(required = false)String username,
                                         @RequestParam(required = false)String password) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);

            try {
                User createdUser = this.userService.register(user);
                if (createdUser != null) {
                    LOG.info("User registration was successful");
                    return ResponseEntity.ok("User registration was successful");
                } else {
                    LOG.info("User registration failed");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
                }
            } catch (Exception e) {
                LOG.error("Error registering user");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user" + e.getMessage());
            }

        } else {
            LOG.info("User provided no username or password for registering");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password not provided");
        }
    }

}
