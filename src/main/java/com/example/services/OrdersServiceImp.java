package com.example.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.model.Orders;
import com.example.repository.CustomerRepository;
import com.example.repository.OrdersRepository;

@Service
public class OrdersServiceImp implements OrdersService{

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Orders placeOrder(Customer customer, BigDecimal totalAmount, int useEpoints) {

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        // ✅ Step 1: Check customer epoints
        int availableEpoints = customer.getEpoints();

        // ✅ Step 2: Validate points user wants to use
        if (useEpoints > availableEpoints) {
            throw new RuntimeException("Not enough epoints!");
        }

        // ✅ Step 3: Deduct epoints from customer
        customer.setEpoints(availableEpoints - useEpoints);

        // ✅ Step 4: Save used epoints in Orders table
        order.setEpointsUsed(useEpoints);

        // ✅ Step 5: Earn new epoints (example logic: 1 point per 100 rs)
        int earnedPoints = totalAmount.intValue() / 100;

        order.setEpointsEarned(earnedPoints);

        // ✅ Step 6: Add earned epoints to customer
        customer.setEpoints(customer.getEpoints() + earnedPoints);

        // ✅ Save customer update
        customerRepository.save(customer);

        // ✅ Save order
        return ordersRepository.save(order);
        
    }
}
