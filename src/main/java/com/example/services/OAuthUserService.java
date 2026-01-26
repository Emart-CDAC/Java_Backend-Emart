package com.example.services;

import java.util.Optional;
import com.example.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@Service
public class OAuthUserService {
	@Autowired
	private CustomerRepository customerRepository;

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email).orElse(null);
	}

	public Customer saveCustomer(OAuth2User oauthUser) {

		String email = oauthUser.getAttribute("email");

		Customer existing = findByEmail(email);
		if (existing != null) {
			return existing;
		}

		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setFullName(oauthUser.getAttribute("name"));
		customer.setAuthProvider(com.example.model.AuthProvider.GOOGLE);
		customer.setProfileCompleted(true); // Auto-complete
		customer.setPassword("OAUTH_USER");

		// Auto-create default Address to satisfy DB constraints
		Address address = new Address();
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setPincode("400001");
		address.setTown("Mumbai");
		address.setHouseNumber("N/A");
		address.setLandmark("N/A");
		address.setCustomer(customer);
		customer.setAddress(address);

		return customerRepository.save(customer);
	}

}
