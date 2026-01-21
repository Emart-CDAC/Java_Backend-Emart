package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@Service
public class EPointsServiceImpl implements EPointsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public int creditPoints(int userId, int epoints) {
		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not Found"));

		customer.setEpoints(customer.getEpoints() + epoints);
		customerRepository.save(customer);
		
		return customer.getEpoints();
	}

	@Override
	public int redeemPoints(int userId, int epoints) {
		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if(customer.getEpoints()<epoints) {
			throw new RuntimeException("Insufficient Epoints");
		}
		
		customer.setEpoints(customer.getEpoints()-epoints);
		customerRepository.save(customer);
		
		return customer.getEpoints();
	}

	@Override
	public int getBalance(int userId) {
		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		return customer.getEpoints();

	}

}
