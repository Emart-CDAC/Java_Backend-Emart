package com.example.services;

import java.util.Optional;

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
		customer.setProfileCompleted(false);

		return customerRepository.save(customer);
	}

}
