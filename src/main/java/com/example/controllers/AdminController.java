package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.LoginRequest;
import com.example.dto.TokenResponse;

import com.example.services.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

		String token = adminService.login(request.getEmail(), request.getPassword());

		return ResponseEntity.ok(new TokenResponse(token));
	}

	@GetMapping("/{id}")
	public ResponseEntity<com.example.model.Admin> getAdminById(
			@org.springframework.web.bind.annotation.PathVariable int id) {
		return ResponseEntity.ok(adminService.getAdminById(id));
	}

}
