package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.SubCategoryRepository;

public class ProductCSVHelper {

    public static boolean isCSVFile(String filename) {
        return filename != null && filename.toLowerCase().endsWith(".csv");
    }

    public static List<Product> parseCSV(
            InputStream is,
            CategoryRepository categoryRepo,
            SubCategoryRepository subCategoryRepo) {

        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {

                if (header) {
                    header = false;
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 10) {
                    throw new RuntimeException("Invalid CSV format: " + line);
                }

                String parentCategoryName = data[0].trim();
                String childCategoryName  = data[1].trim();
                String brand              = data[2].trim();

                // 🔹 1. Parent Category (Electronics, Fashion, etc.)
                Category parentCategory = categoryRepo
                        .findByCategoryNameIgnoreCase(parentCategoryName)
                        .orElseGet(() -> {
                            Category c = new Category();
                            c.setCategoryName(parentCategoryName);
                            c.setParentCategory(null);
                            return categoryRepo.save(c);
                        });

                // 🔹 2. Child Category (Camera, TV, Mobile, etc.)
                Category childCategory = categoryRepo
                        .findByCategoryNameAndParentCategory(
                                childCategoryName,
                                parentCategory
                        )
                        .orElseGet(() -> {
                            Category c = new Category();
                            c.setCategoryName(childCategoryName);
                            c.setParentCategory(parentCategory);
                            return categoryRepo.save(c);
                        });

                // 🔹 3. SubCategory = Brand
                SubCategory subCategory = subCategoryRepo
                        .findByCategoryAndBrandIgnoreCase(childCategory, brand)
                        .orElseGet(() -> {
                            SubCategory sc = new SubCategory();
                            sc.setCategory(childCategory);
                            sc.setBrand(brand);
                            return subCategoryRepo.save(sc);
                        });

                // 🔹 4. Product
                Product p = new Product();
                p.setName(data[3].trim());
                p.setImageUrl(data[4].trim());
                p.setNormalPrice(Double.parseDouble(data[5]));
                p.setEcardPrice(Double.parseDouble(data[6]));
                p.setAvailableQuantity(Integer.parseInt(data[7]));
                p.setDescription(data[8].trim());
                p.setStoreId(Integer.parseInt(data[9]));
                p.setSubCategory(subCategory);

                products.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV parsing failed: " + e.getMessage());
        }

        return products;
    }
}
