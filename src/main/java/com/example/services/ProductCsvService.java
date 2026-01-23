package com.example.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Product;
import com.example.model.SubCategory;
import com.example.repository.ProductRepository;
import com.example.repository.SubCategoryRepository;

@Service
public class ProductCsvService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Map<String, Object> processProductCsv(MultipartFile file) {

        int processed = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {

                if (line.toLowerCase().contains("subcategoryid"))
                    continue;
                if (line.trim().isEmpty())
                    continue;

                try {
                    String[] data = line.split(",");

                    Product product = new Product();
                    product.setName(data[0].trim());
                    product.setDescription(data[1].trim());
                    product.setNormalPrice(Double.parseDouble(data[2].trim()));
                    product.setAvailableQuantity(Integer.parseInt(data[3].trim()));
                    product.setImageUrl(data[5].trim());
                    product.setStoreId(1);

                    Integer subCategoryId = Integer.parseInt(data[4].trim());
                    SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                            .orElseThrow(() -> new RuntimeException("Invalid SubCategory ID: " + subCategoryId));

                    product.setSubCategory(subCategory);

                    productRepository.save(product);
                    processed++;

                } catch (Exception ex) {
                    errors.add(ex.getMessage());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV processing failed: " + e.getMessage());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("processed", processed);
        result.put("errors", errors.size());
        result.put("errorDetails", errors);

        return result;
    }
}
