package com.example.repository;

import com.example.model.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> 
{
	Optional<Category> findByCategoryName(String categoryName);
	
}
