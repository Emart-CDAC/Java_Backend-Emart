package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin login(String username, String password) {

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!admin.isActive()) {
            throw new RuntimeException("Admin inactive");
        }

        return admin;
    }
}

