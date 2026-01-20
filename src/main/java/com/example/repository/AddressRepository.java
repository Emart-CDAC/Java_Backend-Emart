package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Address;
import com.example.model.Customer;

public interface AddressRepository extends JpaRepository<Address,Long> {
	
	List<Address>  findById(Customer customer);
	void deleteByIdAndCustomer(Long id,Customer customer);
	List<Address> findByCustomerUserId(Long customerId);
	
}
