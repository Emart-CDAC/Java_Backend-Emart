package com.example.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.PlaceOrderRequest;
import com.example.dto.OrderResponseDTO;
import com.example.mapper.OrderMapper;
import com.example.model.Orders;
import com.example.services.OrdersService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrdersService orderService;

	// ============================
	// PLACE ORDER
	// ============================
	@PostMapping("/place")
	public ResponseEntity<?> placeOrder(
			@RequestBody PlaceOrderRequest request) {

		try {
			Orders order = orderService.placeOrder(request);
			return ResponseEntity.ok(OrderMapper.toDTO(order));
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Cart is empty")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorResponse("CART_EMPTY", "Cart is empty. Please add items before checkout."));
			}
			if (e.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse("NOT_FOUND", e.getMessage()));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("ORDER_ERROR", e.getMessage()));
		}
	}

	// ============================
	// GET ORDER BY ID
	// ============================
	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderById(
			@PathVariable int orderId) {

		System.out.println("🔍 API Request: Get Order ID " + orderId);
		try {
			Orders order = orderService.getOrderById(orderId);
			System.out.println("✅ Order found in DB: " + (order != null ? order.getOrderId() : "null"));

			// Test standard properties
			if (order != null) {
				System.out.println(
						"   - Customer: " + (order.getCustomer() != null ? order.getCustomer().getFullName() : "null"));
				System.out.println(
						"   - Address: " + (order.getAddress() != null ? order.getAddress().getCity() : "null"));
			}

			return ResponseEntity.ok(OrderMapper.toDTO(order));
		} catch (Exception e) {
			System.err.println("❌ ERROR in getOrderById: " + e.getMessage());
			e.printStackTrace(); // Print full stack trace to console

			if (e.getMessage() != null && e.getMessage().contains("not found")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse("ORDER_NOT_FOUND", e.getMessage()));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("ERROR", e.getMessage() != null ? e.getMessage() : "Unknown Server Error"));
		}
	}

	// ============================
	// GET USER ORDERS
	// ============================
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderResponseDTO>> viewOrders(
			@PathVariable int userId) {

		List<Orders> orders = orderService.getOrdersByUserId(userId);
		List<OrderResponseDTO> dtos = orders.stream()
				.map(OrderMapper::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}

	// Simple error response class
	public static class ErrorResponse {
		private String code;
		private String message;

		public ErrorResponse(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
