package com.example.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

import com.example.dto.PlaceOrderRequest;
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
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private InvoiceService invoiceService;

    @Override
    @Transactional
    public Orders placeOrder(PlaceOrderRequest req) {
        try {
            return placeOrderInternal(req);
        } catch (Exception e) {
            try {
                java.io.FileWriter fw = new java.io.FileWriter("backend_error.log", true);
                java.io.PrintWriter pw = new java.io.PrintWriter(fw);
                e.printStackTrace(pw);
                pw.close();
                fw.close();
            } catch (Exception io) {
            }
            throw e;
        }
    }

    private Orders placeOrderInternal(PlaceOrderRequest req) {

        Customer customer = customerRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItems> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        if (cart.getUsedEpoints() > customer.getEpoints()) {
            throw new RuntimeException("Insufficient e-points");
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setCart(cart);
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryType(req.getDeliveryType());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setTotalAmount(cart.getFinalPayableAmount());
        order.setEpointsUsed(cart.getUsedEpoints());
        order.setEpointsEarned(cart.getEarnedEpoints());
        order.setStatus(OrderStatus.CONFIRMED); // Case-insensitive handled by Converter now

        // DELIVERY
        if (req.getDeliveryType() == DeliveryType.HOME_DELIVERY) {
            Address address = addressRepository.findById(req.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            order.setAddress(address);
        } else {
            Store store = storeRepository.findById(req.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            order.setStore(store);
        }

        // PAYMENT
        order.setPaymentStatus(
                req.getPaymentMethod() == PaymentMethod.CASH
                        ? PaymentStatus.PENDING
                        : PaymentStatus.PAID);

        Orders savedOrder = ordersRepository.save(order);

        // CART → ORDER ITEMS
        List<OrderItems> orderItemsList = new java.util.ArrayList<>();
        for (CartItems ci : cartItems) {
            OrderItems oi = new OrderItems();
            oi.setOrder(savedOrder);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getNormalPrice());
            oi.setSubtotal(ci.getSubtotal());
            orderItemRepository.save(oi);

            // Add to list for PDF generation
            orderItemsList.add(oi);
        }
        savedOrder.setOrderItems(orderItemsList);

        // UPDATE EPOINTS
        customer.setEpoints(
                customer.getEpoints()
                        - cart.getUsedEpoints()
                        + cart.getEarnedEpoints());
        customerRepository.save(customer);

        // CLEAR CART
        cartItemRepository.deleteByCart_CartId(cart.getCartId());
        cart.setTotalMrp(BigDecimal.ZERO);
        cart.setFinalPayableAmount(BigDecimal.ZERO);
        cart.setUsedEpoints(0);
        cart.setEpointDiscount(BigDecimal.ZERO);
        cart.setEarnedEpoints(0);
        cartRepository.save(cart);

        // GENERATE INVOICE ENTITY
        try {
            // We need to inject InvoiceService here or use a bean if circular dependency
            // allows.
            // Usually InvoiceService depends on OrderService (for lookup), but here
            // OrderService depends on InvoiceService (for creation).
            // Circular dependency risk: OrderService -> InvoiceService -> OrderService.
            // InvoiceServiceImp injects OrdersRepository, not OrdersService, so it should
            // be fine.
            // We need to AutoWire InvoiceService.
        } catch (Exception e) {
            System.err.println("Failed to create invoice entity: " + e.getMessage());
        }

        // SEND EMAIL WITH PDF
        try {
            // Construct Email Body
            String subject = "Order Confirmation - Order #" + savedOrder.getOrderId();
            String body = "Dear " + customer.getFullName() + ",\n\n" +
                    "Thank you for shopping with e-MART!\n" +
                    "Your order has been placed successfully.\n\n" +
                    "Order ID: " + savedOrder.getOrderId() + "\n" +
                    "Amount Paid: Rs." + savedOrder.getTotalAmount() + "\n\n" +
                    "Please find your invoice attached (if generated).\n\n" +
                    "e-MART Team";

            // Generate PDF
            byte[] pdfBytes = invoiceService.generateInvoicePdf(savedOrder);

            if (pdfBytes != null) {
                emailService.sendPdf(customer.getEmail(), subject, body, pdfBytes);
                System.out.println("✅ Invoice PDF sent to: " + customer.getEmail());
            } else {
                // Fallback to simple email (same body)
                emailService.sendEmail(customer.getEmail(), subject, body);
            }

        } catch (Exception e) {
            System.err.println("⚠️ Failed to send email: " + e.getMessage());
        }

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
    @Transactional
    public List<Orders> getOrdersByUserId(int userId) {
        List<Orders> orders = ordersRepository.findByCustomerUserId(userId);
        // Initialize lazy collection for each order
        for (Orders order : orders) {
            if (order.getOrderItems() != null) {
                order.getOrderItems().size();
            }
        }
        return orders;
    }

    @Override
    @Transactional
    public Orders getOrderById(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Initialize lazy collection
        if (order.getOrderItems() != null) {
            order.getOrderItems().size();
        }
        return order;
    }
}
