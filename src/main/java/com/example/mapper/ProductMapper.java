package com.example.mapper;

import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.model.Product;
import com.example.model.SubCategory;

public class ProductMapper 
{
	public static Product toEntity(ProductRequestDTO dto, SubCategory subCategory) {

        Product product = new Product();
        product.setName(dto.getName());
        product.setImageUrl(dto.getImageUrl());
        product.setNormalPrice(dto.getNormalPrice());
        product.setEcardPrice(dto.getEcardPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setDescription(dto.getDescription());
        product.setSubCategory(subCategory);
        product.setStoreId(dto.getStoreId());

        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImageUrl(product.getImageUrl());
        dto.setNormalPrice(product.getNormalPrice());
        dto.setEcardPrice(product.getEcardPrice());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setDescription(product.getDescription());

        return dto;
    }

}
