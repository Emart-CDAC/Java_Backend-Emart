package com.example.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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
    @Transactional
    public Orders placeOrder(int userId, String deliveryType, String addressStr) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItems> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        int usedEpoints = cart.getUsedEpoints();
        int earnedEpoints = cart.getEarnedEpoints();

        // ✅ Validate customer balance
        if (usedEpoints > customer.getEpoints()) {
            throw new RuntimeException("Insufficient e-points");
        }

        // ✅ Create Order
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.valueOf(cart.getFinalPayableAmount()));
        order.setEpointsUsed(usedEpoints);
        order.setEpointsEarned(earnedEpoints);
        order.setStatus(OrderStatus.pending);
        order.setPaymentStatus(PaymentStatus.PAID);

        order.setDeliveryType(
                "pickup".equalsIgnoreCase(deliveryType)
                        ? DeliveryType.STORE
                        : DeliveryType.HOME_DELIVERY);

        order.setAddress(customer.getAddress());

        Orders savedOrder = ordersRepository.save(order);

        // ✅ Convert CartItems → OrderItems
        for (CartItems item : cartItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getNormalPrice());
            orderItem.setSubtotal(item.getSubtotal());
            orderItemRepository.save(orderItem);
        }

        // ✅ Update customer e-points
        customer.setEpoints(
                customer.getEpoints() - usedEpoints + earnedEpoints);
        customerRepository.save(customer);

        // ✅ Clear cart
        cartItemRepository.deleteAll(cartItems);
        cart.setTotalAmount(0);
        cart.setTotalMrp(0);
        cart.setEpointDiscount(0);
        cart.setUsedEpoints(0);
        cart.setEarnedEpoints(0);
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public Orders updateOrderStatus(int orderId, OrderStatus status) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return ordersRepository.save(order);
    }

    @Override
    public List<Orders> getOrdersByUserId(int userId) {
        return ordersRepository.findByCustomerUserId(userId);
    }

}
