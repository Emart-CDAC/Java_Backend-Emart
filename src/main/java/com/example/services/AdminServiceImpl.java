package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.repository.AdminRepository;
import com.example.security.JwtUtil;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(String email, String password) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!admin.isActive()) {
            throw new RuntimeException("Admin inactive");
        }

        return jwtUtil.generateToken(email, "ROLE_ADMIN");
    }
}
