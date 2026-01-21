package com.example.services;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            return null;
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

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
