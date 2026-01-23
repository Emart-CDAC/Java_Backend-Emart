package com.example.repository;


import com.example.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> 
{
	@Query("""
			SELECT p FROM Product p
			WHERE
			   LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
			   OR LOWER(p.subCategory.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
			   OR LOWER(p.subCategory.category.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			   OR LOWER(p.subCategory.category.parentCategory.categoryName)
			        LIKE LOWER(CONCAT('%', :keyword, '%'))
			""")
	    List<Product> searchProducts(@Param("keyword") String keyword);
}
