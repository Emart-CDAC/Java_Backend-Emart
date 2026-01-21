package com.example.services;

import java.util.List;

import com.example.model.SubCategory;

public interface SubCategoryService {
	List<SubCategory> getSubCategoriesByCategory(int categoryId);
}
