package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.SubCategoryRepository;

public class ProductCSVHelper {

    public static List<Product> parseCSV(
            InputStream is,
            CategoryRepository categoryRepo,
            SubCategoryRepository subCategoryRepo
    ) {

        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                Product product = new Product();
                product.setName(data[0]);
                product.setNormalPrice(Double.parseDouble(data[1]));
                product.setEcardPrice(Double.parseDouble(data[2]));
                product.setAvailableQuantity(Integer.parseInt(data[3]));
                product.setDescription(data[4]);

                                String categoryName = data[5];
                Category category = categoryRepo
                        .findByCategoryNameIgnoreCase(categoryName)
                        .orElseThrow(() ->
                                new RuntimeException("Category not found: " + categoryName)
                        );

                String brand = data[6];
                SubCategory subCategory = subCategoryRepo
                        .findByCategoryAndBrandIgnoreCase(category, brand)
                        .orElseThrow(() ->
                                new RuntimeException("SubCategory not found for brand: " + brand)
                        );

                product.setSubCategory(subCategory);

       
                products.add(product);
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV parse error: " + e.getMessage());
        }

        return products;
    }
}

