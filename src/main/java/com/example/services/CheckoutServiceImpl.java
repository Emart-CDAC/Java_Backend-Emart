package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Customer;
import com.example.repository.CartRepository;
import com.example.repository.CustomerRepository;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Object selectDeliveryOption(int userId, String deliveryType) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Example logic (you can store this in cart/order later)
        if (!deliveryType.equalsIgnoreCase("DELIVERY") &&
            !deliveryType.equalsIgnoreCase("PICKUP")) {
            throw new IllegalArgumentException("Invalid delivery type");
        }

        // cart.setDeliveryType(deliveryType);  // if field exists
        cartRepository.save(cart);

        return "Delivery option '" + deliveryType + "' selected successfully";
    }

    @Override
    public Object placeOrder(int userId) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getTotalAmount() == 0) {
            throw new RuntimeException("Cart is empty");
        }

        // Later you will:
        // 1. Create Order
        // 2. Move CartItems → OrderItems
        // 3. Clear cart

        return "Order placed successfully for userId: " + userId;
    }
}
