package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/cart")
public class CartController 
{
	@Autowired
	CartService cartservice;
	
	@PostMapping("/add")
	public ResponseEntity<?>addToCart(@RequestParam int userId, @RequestParam int productId , @RequestParam(defaultValue = "1") int quantity)
	{
		return ResponseEntity.ok(cartservice.addToCart(userId,productId,quantity));
		
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<?> removeFromCart(@PathVariable int cartItemId)
	{
		cartservice.removeFromCart(cartItemId);
		return ResponseEntity.ok("Item removed.");
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> UpdateQuantity(@RequestParam int cartItemId,@RequestParam int quantity )
	{
		return ResponseEntity.ok(cartservice.updateQuantity(cartItemId , quantity));
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<?> viewCart(@PathVariable int userId)
	{
		return ResponseEntity.ok(cartservice.viewCart(userId));
	}

}
