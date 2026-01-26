package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// =============================
	// ADD TO CART
	// =============================
	@PostMapping("/add")
	public ResponseEntity<?> addToCart(
			@RequestParam int userId,
			@RequestParam int productId,
			@RequestParam(defaultValue = "1") int quantity,
			@RequestParam(defaultValue = "NORMAL") String purchaseType,
			@RequestParam(defaultValue = "0") int epointsUsed) {

		cartService.addToCart(userId, productId, quantity, purchaseType, epointsUsed);
		return ResponseEntity.ok(cartService.getCartSummary(userId));
	}

	// =============================
	// REMOVE ITEM
	// =============================
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<?> removeFromCart(@PathVariable int cartItemId) {
		cartService.removeFromCart(cartItemId);
		return ResponseEntity.ok("Item removed");
	}

	// =============================
	// UPDATE QUANTITY
	// =============================
	@PutMapping("/update")
	public ResponseEntity<?> updateQuantity(
			@RequestParam int cartItemId,
			@RequestParam int quantity) {

		cartService.updateQuantity(cartItemId, quantity);
		return ResponseEntity.ok("Quantity updated");
	}

	// =============================
	// CART SUMMARY
	// =============================
	@GetMapping("/summary/{userId}")
	public ResponseEntity<?> getCartSummary(@PathVariable int userId) {
		return ResponseEntity.ok(cartService.getCartSummary(userId));
	}

	// =============================
	// CLEAR CART
	// =============================
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<?> clearCart(@PathVariable int userId) {
		cartService.clearCartByUser(userId);
		return ResponseEntity.ok("Cart cleared");
	}
}
