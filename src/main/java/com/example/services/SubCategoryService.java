package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.SubCategory;


public interface SubCategoryService  {
	List<SubCategory> getSubCategoriesByCategory(int categoryId);
}
