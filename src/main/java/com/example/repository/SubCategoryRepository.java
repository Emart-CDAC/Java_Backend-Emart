package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.SubCategory;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
	
	List<SubCategory> findByCategoryId(int categoryId);
}
