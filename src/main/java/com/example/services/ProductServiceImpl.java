package com.example.services;

import com.example.model.Product;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import com.example.repository.SubCategoryRepository;
import com.example.util.ProductExcelHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private SubCategoryRepository subCategoryRepo;

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

	@Override
	public void uploadProducts(MultipartFile file) {

        if (!ProductExcelHelper.isExcelFile(file)) {
            throw new RuntimeException("Invalid Excel file");
        }

        try {
            List<Product> products = ProductExcelHelper.parseExcel(
                    file.getInputStream(),
                    categoryRepo,
                    subCategoryRepo
            );

            productRepository.saveAll(products);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
