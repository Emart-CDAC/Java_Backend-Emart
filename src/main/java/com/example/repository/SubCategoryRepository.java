package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Category;
import com.example.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    List<SubCategory> findByCategoryCategoryId(int categoryId);
    Optional<SubCategory> findByCategoryAndBrand(Category category, String brand);
}
