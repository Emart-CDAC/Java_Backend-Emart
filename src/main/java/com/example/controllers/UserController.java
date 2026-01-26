package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.security.JwtUtil;
import com.example.services.UserService;
import com.example.services.UserServiceImpl;
import com.example.dto.LoginRequest;
import com.example.dto.TokenResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    // ===================== LOCAL LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        Customer customer = userService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtUtil.generateToken(
                customer.getEmail(),
                "ROLE_USER",
                customer.getUserId());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    // ===================== NORMAL REGISTER =====================
    @PostMapping("/register")
    public Customer registerUser(@RequestBody Customer customer) {
        return userService.registerUser(customer);
    }

    // ===================== GOOGLE SSO CALLBACK =====================
    @PostMapping("/google-login")
    public ResponseEntity<Customer> googleLogin(
            @RequestParam String email,
            @RequestParam String fullName) {

        Customer customer = userServiceImpl.processGoogleLogin(email, fullName);

        return ResponseEntity.ok(customer);
    }

    // ===================== COMPLETE REGISTRATION (SSO USER) =====================
    @PutMapping("/complete-registration/{userId}")
    public ResponseEntity<TokenResponse> completeRegistration(
            @PathVariable int userId,
            @RequestBody Customer customer) {

        Customer updatedCustomer = userServiceImpl.completeRegistration(userId, customer);

        String token = jwtUtil.generateToken(
                updatedCustomer.getEmail(),
                "ROLE_USER",
                updatedCustomer.getUserId());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    // ===================== PROFILE =====================
    @GetMapping("/{userId}")
    public Customer viewProfile(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public Customer updateProfile(
            @PathVariable int userId,
            @RequestBody Customer customer) {

        return userService.updateUser(userId, customer);
    }
}
