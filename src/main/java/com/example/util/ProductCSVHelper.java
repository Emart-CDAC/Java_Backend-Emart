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

    private static java.math.BigDecimal parseDiscount(String value) {
        if (value == null) {
            return java.math.BigDecimal.ZERO;
        }

        value = value.trim();

        // Accept only numeric values (e.g. 10 or 10.5)
        if (!value.matches("\\d+(\\.\\d+)?")) {
            return java.math.BigDecimal.ZERO;
        }

        java.math.BigDecimal discount = new java.math.BigDecimal(value);

        // Business rule: cap discount between 0–90
        if (discount.compareTo(java.math.BigDecimal.ZERO) < 0 ||
                discount.compareTo(java.math.BigDecimal.valueOf(90)) > 0) {
            return java.math.BigDecimal.ZERO;
        }

        return discount;
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

                // Split by comma, but ignore commas inside quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Clean up quotes
                for (int i = 0; i < data.length; i++) {
                    String val = data[i].trim();
                    if (val.startsWith("\"") && val.endsWith("\"") && val.length() > 1) {
                        val = val.substring(1, val.length() - 1);
                    }
                    data[i] = val.trim();
                }

                if (data.length < 10) {
                    throw new RuntimeException("Invalid CSV row (columns=" + data.length + "): " + line);
                }

                String parentCategoryName = data[0].trim();
                String childCategoryName = data[1].trim();
                String brand = data[2].trim();

                // Parent category
                Category parentCategory = categoryRepo
                        .findByCategoryNameIgnoreCase(parentCategoryName)
                        .orElseGet(() -> {
                            Category c = new Category();
                            c.setCategoryName(parentCategoryName);
                            c.setParentCategory(null);
                            return categoryRepo.save(c);
                        });

                // Child category
                Category childCategory = categoryRepo
                        .findByCategoryNameAndParentCategory(
                                childCategoryName, parentCategory)
                        .orElseGet(() -> {
                            Category c = new Category();
                            c.setCategoryName(childCategoryName);
                            c.setParentCategory(parentCategory);
                            return categoryRepo.save(c);
                        });

                // SubCategory = Brand
                SubCategory subCategory = subCategoryRepo
                        .findByCategoryAndBrandIgnoreCase(childCategory, brand)
                        .orElseGet(() -> {
                            SubCategory sc = new SubCategory();
                            sc.setCategory(childCategory);
                            sc.setBrand(brand);
                            return subCategoryRepo.save(sc);
                        });

                // Product
                Product p = new Product();
                try {
                    p.setName(data[3].trim());
                    p.setImageUrl(data[4].trim());

                    try {
                        p.setNormalPrice(new java.math.BigDecimal(data[5].trim()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing Normal Price (col 6). Value: '" + data[5] + "'");
                    }

                    try {
                        p.setEcardPrice(new java.math.BigDecimal(data[6].trim()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing Ecard Price (col 7). Value: '" + data[6] + "'");
                    }

                    try {
                        p.setAvailableQuantity(Integer.parseInt(data[7].trim()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing Quantity (col 8). Value: '" + data[7] + "'");
                    }

                    p.setDescription(data[8].trim());

                    try {
                        p.setStoreId(Integer.parseInt(data[9].trim()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing StoreId (col 10). Value: '" + data[9] + "'");
                    }

                    p.setSubCategory(subCategory);

                    // Discount Percent (Optional)
                    // Index 10 is 'Offer' (TRUE/FALSE), so we look at index 11 for
                    // 'DiscountPercent'
                    if (data.length > 11 && !data[11].trim().isEmpty()) {
                        try {
                            p.setDiscountPercent(parseDiscount(data[11].trim()));
                        } catch (Exception e) {
                            throw new RuntimeException(
                                    "Error parsing Discount Percent (col 12). Value: '" + data[11] + "'");
                        }
                    } else {
                        // usage of parseDiscount prevents invalid number errors for null/empty
                        p.setDiscountPercent(java.math.BigDecimal.ZERO);
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Row " + (products.size() + 2) + ": " + e.getMessage()); // +2 for
                                                                                                        // 1-based index
                                                                                                        // and header
                }

                products.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV parsing failed: " + e.getMessage());
        }

        return products;
    }
}
