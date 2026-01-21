package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.SubCategory;
import com.example.repository.SubCategoryRepository;

@Service
public class SubCategoryServiceImpl {
	@Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<SubCategory> getSubCategoriesByCategory(int categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }
}
