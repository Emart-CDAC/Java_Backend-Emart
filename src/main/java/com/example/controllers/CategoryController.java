package com.example.controllers;

import com.example.model.Category;
import com.example.model.SubCategory;
import com.example.repository.CategoryRepository;
import com.example.services.SubCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryService subCategoryService;

    // List Categories (only Parents)
    @GetMapping
    public List<Category> listCategories() {
        // Simple filtering stream from findAll, or better: add a custom query method in
        // repo
        return categoryRepository.findAll().stream()
                .filter(c -> c.getParentCategory() == null)
                .collect(java.util.stream.Collectors.toList());
    }

    // View sub-categories by category id
    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategory> getSubCategories(@PathVariable int categoryId) {
        return subCategoryService.getSubCategoriesByCategory(categoryId);
    }

    // View Child Categories by Parent ID
    @GetMapping("/{parentId}/children")
    public List<Category> getChildCategories(@PathVariable int parentId) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent Category not found"));
        return categoryRepository.findByParentCategory(parent);
    }
}
