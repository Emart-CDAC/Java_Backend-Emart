package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Address;
import com.example.model.Customer;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    // Get all addresses of a customer
    List<Address> findByCustomer(Customer customer);

    // Get all addresses using customerId
    List<Address> findByCustomerUserId(Integer customerId);
}
