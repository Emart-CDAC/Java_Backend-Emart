package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Payment findByOrder_OrderId(int orderId);

}
