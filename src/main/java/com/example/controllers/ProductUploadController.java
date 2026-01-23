package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.services.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class ProductUploadController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file) {

        productService.uploadProducts(file);
        return ResponseEntity.ok("Products uploaded successfully");
    }
}
