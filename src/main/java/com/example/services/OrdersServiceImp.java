package com.example.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.model.Orders;
import com.example.repository.CustomerRepository;
import com.example.repository.OrdersRepository;

import java.util.List;

import com.example.model.*;
import com.example.repository.*;

@Service
public class OrdersServiceImp implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Orders> getOrdersByUserId(int userId) {
        return ordersRepository.findByCustomerUserId(userId);
    }

    @Override
    public Orders placeOrder(int userId, BigDecimal totalAmount, int useEpoints, String deliveryType,
            String addressStr) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItems> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.pending);
        order.setPaymentStatus(PaymentStatus.PAID);

        if ("pickup".equalsIgnoreCase(deliveryType)) {
            order.setDeliveryType(DeliveryType.STORE);
        } else {
            order.setDeliveryType(DeliveryType.HOME_DELIVERY);
        }

        order.setAddress(customer.getAddress());

        // ✅ Step 1: Check customer epoints
        int availableEpoints = customer.getEpoints();

        // ✅ Step 2: Validate points user wants to use
        if (useEpoints > availableEpoints) {
            throw new RuntimeException("Not enough epoints!");
        }

        // ✅ Step 3: Deduct epoints from customer
        customer.setEpoints(availableEpoints - useEpoints);
        order.setEpointsUsed(useEpoints);

        // ✅ Step 4: Earn new epoints
        int earnedPoints = totalAmount.intValue() / 100;
        order.setEpointsEarned(earnedPoints);
        customer.setEpoints(customer.getEpoints() + earnedPoints);

        // ✅ Save order first to get ID
        Orders savedOrder = ordersRepository.save(order);

        // ✅ Step 5: Convert CartItems to OrderItems
        for (CartItems item : cartItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getNormalPrice());
            orderItem.setSubtotal(item.getTotalPrice());
            orderItemRepository.save(orderItem);
        }

        // ✅ Step 6: Clear Cart
        cartItemRepository.deleteAll(cartItems);
        cart.setTotalAmount(0.0);
        cartRepository.save(cart);

        // ✅ Save customer update
        customerRepository.save(customer);

        return savedOrder;
    }

    @Override
    public Orders updateOrderStatus(int orderId, OrderStatus status) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return ordersRepository.save(order);
    }
}
