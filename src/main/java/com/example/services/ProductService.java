package com.example.services;

import com.example.model.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(int id, Product product);

    void deleteProduct(int id);
    
    void uploadProducts(MultipartFile file);
}
