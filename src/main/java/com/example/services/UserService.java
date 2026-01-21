package com.example.services;

import com.example.model.Customer;

public interface UserService {

    Customer registerUser(Customer customer);

    Customer getUserById(Long userId);

    Customer updateUser(Long userId, Customer customer);

    String changePassword(Long userId, String oldPassword, String newPassword);
}
