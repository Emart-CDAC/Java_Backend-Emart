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

    //  List Categories
    @GetMapping
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    // View sub-categories by category id
    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategory> getSubCategories(@PathVariable int categoryId) {
        return subCategoryService.getSubCategoriesByCategory(categoryId);
    }
}
