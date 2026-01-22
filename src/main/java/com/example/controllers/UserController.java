package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.security.JwtUtil;
import com.example.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

		Customer customer = userService.login(request.getEmail(), request.getPassword());

		String token = jwtUtil.generateToken(customer.getEmail(), "ROLE_USER");

		return ResponseEntity.ok(new TokenResponse(token));
	}

	@PostMapping("/register")
	public Customer registerUser(@RequestBody Customer customer) {
		return userService.registerUser(customer);
	}

	@GetMapping("/{userId}")
	public Customer viewProfile(@PathVariable int userId) {
		return userService.getUserById(userId);
	}

	@PutMapping("/{userId}")
	public Customer updateProfile(@PathVariable int userId, @RequestBody Customer customer) {
		return userService.updateUser(userId, customer);
	}
}

