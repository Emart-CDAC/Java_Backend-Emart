package com.example.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(int id);

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO updateProduct(int id, ProductRequestDTO dto);

    void deleteProduct(int id);

    void uploadProducts(MultipartFile file);

    List<ProductResponseDTO> searchProducts(String keyword);
}
