package com.example.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.repository.SubCategoryRepository;

public class ProductExcelHelper {

    public static boolean isExcelFile(MultipartFile file) {
        return file.getOriginalFilename().endsWith(".xlsx");
    }

    public static List<Product> parseExcel(
            InputStream is,
            CategoryRepository categoryRepo,
            SubCategoryRepository subCategoryRepo
    ) {

        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Product> products = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String categoryName = row.getCell(0).getStringCellValue();
                String brand = row.getCell(1).getStringCellValue();

                Category category = categoryRepo
                        .findByCategoryName(categoryName)
                        .orElseThrow(() ->
                                new RuntimeException("Category not found: " + categoryName));

                SubCategory subCategory = subCategoryRepo
                        .findByCategoryAndBrand(category, brand)
                        .orElseThrow(() ->
                                new RuntimeException("SubCategory not found for brand: " + brand));

                Product p = new Product();
                p.setName(row.getCell(2).getStringCellValue());
                p.setImageUrl(row.getCell(3).getStringCellValue());
                p.setNormalPrice(row.getCell(4).getNumericCellValue());
                p.setEcardPrice(row.getCell(5).getNumericCellValue());
                p.setAvailableQuantity((int) row.getCell(6).getNumericCellValue());
                p.setDescription(row.getCell(7).getStringCellValue());
                p.setStoreId((int) row.getCell(8).getNumericCellValue());
                p.setSubCategory(subCategory);

                products.add(p);
            }

            workbook.close();
            return products;

        } catch (Exception e) {
            throw new RuntimeException("Excel parsing failed: " + e.getMessage());
        }
    }
}
