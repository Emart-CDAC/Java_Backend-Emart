package com.example.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.model.Customer;
import com.example.services.OAuthUserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private OAuthUserService oauthuserservice;

	@GetMapping("/success")
	public ResponseEntity<?> loginsuccess(@AuthenticationPrincipal OAuth2User user) {
		Customer dbuser = oauthuserservice.saveCustomer(user);

		return ResponseEntity.ok(Map.of(
				"message", "Login successful",
				"email", dbuser.getEmail(),
				"name", dbuser.getFullName()));

	}

	@GetMapping("/me")
	public ResponseEntity<?> currentUser(
			@AuthenticationPrincipal OAuth2User user) {

		if (user == null) {
			return ResponseEntity.status(401).build();
		}

		return ResponseEntity.ok(user.getAttributes());
	}

}
