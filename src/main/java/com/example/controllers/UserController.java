package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Customer registerUser(@RequestBody Customer customer) {
        return userService.registerUser(customer);
    }

    @GetMapping("/{userId}")
    public Customer viewProfile(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public Customer updateProfile(
            @PathVariable Long userId,
            @RequestBody Customer customer) {
        return userService.updateUser(userId, customer);
    }
}
