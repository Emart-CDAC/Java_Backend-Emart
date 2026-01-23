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
                .filter(o -> o.getStatus() == com.example.model.OrderStatus.pending)
                .count();
        stats.put("pendingOrders", pendingOrders);

        return stats;
    }

    public Page<Orders> getAllOrders(int page, int size) {
        return ordersRepository.findAll(PageRequest.of(page, size));
    }
}
