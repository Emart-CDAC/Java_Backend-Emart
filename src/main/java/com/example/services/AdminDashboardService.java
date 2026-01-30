package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.model.Orders;
import com.example.repository.CustomerRepository;
import com.example.repository.OrdersRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminDashboardService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Total orders
        long totalOrders = ordersRepository.count();
        stats.put("totalOrders", totalOrders);

        // Total revenue
        double totalRevenue = ordersRepository.findAll().stream()
                .map(Orders::getTotalAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        stats.put("totalRevenue", totalRevenue);

        // Total users
        long totalUsers = customerRepository.count();
        stats.put("totalUsers", totalUsers);

        // Pending orders
        long pendingOrders = ordersRepository.findAll().stream()
                .filter(o -> o.getStatus() == com.example.model.OrderStatus.PENDING)
                .count();
        stats.put("pendingOrders", pendingOrders);

        return stats;
    }

    public Page<com.example.dto.OrderResponseDTO> getAllOrders(int page, int size) {
        Page<Orders> ordersPage = ordersRepository.findAll(PageRequest.of(page, size));
        return ordersPage.map(this::convertToDTO);
    }

    private com.example.dto.OrderResponseDTO convertToDTO(Orders order) {
        com.example.dto.OrderResponseDTO dto = new com.example.dto.OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setPaymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null);
        dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);
        dto.setDeliveryType(order.getDeliveryType() != null ? order.getDeliveryType().name() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setEpointsUsed(order.getEpointsUsed());
        dto.setEpointsEarned(order.getEpointsEarned());

        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getUserId());
            dto.setCustomerName(order.getCustomer().getFullName());
            dto.setCustomerEmail(order.getCustomer().getEmail());
        }

        if (order.getAddress() != null) {
            dto.setAddressId(order.getAddress().getAddressId());
            dto.setAddressLine(order.getAddress().getHouseNumber() + ", " + order.getAddress().getTown());
            dto.setCity(order.getAddress().getCity());
            dto.setState(order.getAddress().getState());
            dto.setPincode(order.getAddress().getPincode());
        }

        // Note: Intentionally avoiding lazy lazy loading of orderItems here if not
        // strictly needed
        // to prevent another LazyInitializationException. If items are needed, they
        // should be fetched transactionally.
        // For the dashboard list view, items are usually not required.

        return dto;
    }
}
