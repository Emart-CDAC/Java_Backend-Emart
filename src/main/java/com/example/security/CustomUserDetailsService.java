package com.example.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.model.Customer;
import com.example.repository.AdminRepository;
import com.example.repository.CustomerRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // ✅ 1) check Admin table first
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return admin; // ✅ Admin implements UserDetails
        }

        // ✅ 2) check Customer table
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            return customer; // ✅ Customer implements UserDetails
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
