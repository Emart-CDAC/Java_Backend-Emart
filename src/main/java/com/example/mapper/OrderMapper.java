package com.example.mapper;

import com.example.dto.OrderResponseDTO;
import com.example.model.Orders;
import com.example.model.Address;
import com.example.model.Store;
import com.example.model.Customer;

import com.example.dto.OrderItemDTO;

public class OrderMapper {

    public static OrderResponseDTO toDTO(Orders order) {
        if (order == null) {
            return null;
        }

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setPaymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null);
        dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);
        dto.setDeliveryType(order.getDeliveryType() != null ? order.getDeliveryType().name() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setEpointsUsed(order.getEpointsUsed());
        dto.setEpointsEarned(order.getEpointsEarned());

        // Customer info
        Customer customer = order.getCustomer();
        if (customer != null) {
            dto.setCustomerId(customer.getUserId());
            dto.setCustomerName(customer.getFullName());
            dto.setCustomerEmail(customer.getEmail());
        }

        // Address info (for home delivery)
        Address address = order.getAddress();
        if (address != null) {
            dto.setAddressId(address.getAddressId());
            dto.setAddressLine(
                    (address.getHouseNumber() != null ? address.getHouseNumber() : "") +
                            (address.getLandmark() != null ? ", " + address.getLandmark() : ""));
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setPincode(address.getPincode());
        }

        // Store info (for store pickup)
        Store store = order.getStore();
        if (store != null) {
            dto.setStoreId(store.getStoreId());
            dto.setStoreName(store.getStoreName());
            dto.setStoreCity(store.getCity());
        }

        // Order Items
        if (order.getOrderItems() != null) {
            java.util.List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                    .map(item -> new OrderItemDTO(
                            item.getProduct() != null ? item.getProduct().getName() : "Unknown Product",
                            item.getQuantity(),
                            item.getPrice(),
                            item.getSubtotal()))
                    .collect(java.util.stream.Collectors.toList());
            dto.setItems(itemDTOs);
        }

        return dto;
    }
}
