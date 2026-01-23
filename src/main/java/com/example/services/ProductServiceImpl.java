package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.mapper.ProductMapper;
import com.example.model.Product;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import com.example.repository.SubCategoryRepository;
import com.example.util.ProductCSVHelper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private SubCategoryRepository subCategoryRepo;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(int id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toDTO(product);
    }

   
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        SubCategory subCategory = subCategoryRepo.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        Product product = ProductMapper.toEntity(dto, subCategory);
        Product saved = productRepository.save(product);

        return ProductMapper.toDTO(saved);
    }

   
    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setImageUrl(dto.getImageUrl());
        product.setNormalPrice(dto.getNormalPrice());
        product.setEcardPrice(dto.getEcardPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setDescription(dto.getDescription());
        product.setStoreId(dto.getStoreId());

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void uploadProducts(MultipartFile file) {

        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new RuntimeException("Invalid CSV file");
        }

        try {
            List<Product> products = ProductCSVHelper.parseCSV(
                    file.getInputStream(),
                    categoryRepo,
                    subCategoryRepo
            );

            productRepository.saveAll(products);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDTO> searchProducts(String keyword) {

        return productRepository.searchProducts(keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }
}
