package com.example.converter;

import com.example.model.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name(); // Always save as Uppercase
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return OrderStatus.valueOf(dbData.toUpperCase()); // Convert to Uppercase before mapping
        } catch (IllegalArgumentException e) {
            return OrderStatus.PENDING; // Default to PENDING if unknown
        }
    }
}
