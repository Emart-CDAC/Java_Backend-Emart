package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

public class OAuthUserService 
{
	@Autowired
	private CustomerRepository customerrepository;
	
	public Customer savecustomer(OAuth2User oauthuser)
	{
		String email = oauthuser.getAttribute("email");
		String name = oauthuser.getAttribute("name");
		return customerrepository.findByEmail(email).orElseGet(() ->{
			Customer c = new Customer();
			c.setEmail(email);
			c.setFullName(name);
			
			return customerrepository.save(c);
		});
	}
	
	
	

}
