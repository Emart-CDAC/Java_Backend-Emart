package com.example.repository;


import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}
