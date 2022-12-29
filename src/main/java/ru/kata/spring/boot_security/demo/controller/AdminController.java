package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.Exception.UserUsernameExistException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return  ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
        try {
            userService.createUser(user);
            return  ResponseEntity.ok(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return  ResponseEntity.ok(user);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return  ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody User user) {
         userService.editUser(user);
             return  ResponseEntity.ok(HttpStatus.OK);
    }

}