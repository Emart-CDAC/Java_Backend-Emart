package com.example.services;

import com.example.model.Customer;

public interface UserService {

	Customer login(String username, String password);
	
    Customer registerUser(Customer customer);

    Customer getUserById(int userId);

    Customer updateUser(int userId, Customer customer);

    String changePassword(int userId, String oldPassword, String newPassword);
}
