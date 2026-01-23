package com.example.controllers;

import com.example.dto.ProductResponseDTO;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;

    
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

   
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @RequestBody Product updatedProduct) {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = existingProduct.get();
        product.setName(updatedProduct.getName());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setNormalPrice(updatedProduct.getNormalPrice());
        product.setEcardPrice(updatedProduct.getEcardPrice());
        product.setAvailableQuantity(updatedProduct.getAvailableQuantity());
        product.setDescription(updatedProduct.getDescription());
        product.setSubCategory(updatedProduct.getSubCategory());
        product.setStoreId(updatedProduct.getStoreId());

        return ResponseEntity.ok(productRepository.save(product));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public List<ProductResponseDTO> search(@RequestParam String q) {
        return productService.searchProducts(q);
    }
}
