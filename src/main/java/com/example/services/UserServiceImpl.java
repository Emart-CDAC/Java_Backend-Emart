package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer login(String email, String password) {

		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!customer.getPassword().equals(password)) {
			throw new RuntimeException("Invalid credentials");
		}

		return customer;
	}

	@Override
	public Customer registerUser(Customer customer) {

		return customerRepository.save(customer);
	}

	@Override
	public Customer getUserById(int userId) {
		return customerRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public Customer updateUser(int userId, Customer updatedCustomer) {

		Customer existingCustomer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		existingCustomer.setFullName(updatedCustomer.getFullName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		existingCustomer.setMobile(updatedCustomer.getMobile());
		existingCustomer.setBirthDate(updatedCustomer.getBirthDate());
		existingCustomer.setInterests(updatedCustomer.getInterests());

		return customerRepository.save(existingCustomer);
	}

	@Override
	public String changePassword(int userId, String oldPassword, String newPassword) {

		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!customer.getPassword().equals(oldPassword)) {
			throw new RuntimeException("Old password is incorrect");
		}

		customer.setPassword(newPassword);
		customerRepository.save(customer);

		return "Password updated successfully";
	}
}
