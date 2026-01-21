package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.EPointsService;

@RestController
@RequestMapping("/api/epoints")
public class EPointsController {

	@Autowired
	private EPointsService eService;

	@PostMapping("/credit/{userId}/{epoints}")
	public int credit(@PathVariable int userId, @PathVariable int epoints) {
		return eService.creditPoints(userId, epoints);
	}

	@PostMapping("/redeem/{userId}/{epoints}")
	public int redeem(@PathVariable int userId, @PathVariable int epoints) {
		return eService.redeemPoints(userId, epoints);
	}

	@GetMapping("/balance/{userId}")
	public int balance(@PathVariable int userId) {
		return eService.getBalance(userId);
	}
}
