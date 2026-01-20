package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Cart;
import com.example.model.CartItems;
import com.example.model.Product;

public interface CartItemRepository extends JpaRepository<CartItems, Integer> {
    List<CartItems> findByCart(Cart cart);
    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);
}

