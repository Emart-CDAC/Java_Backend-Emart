package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

@Configuration
public class DataSeeder {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (adminRepository.findByEmail("admin@emart.com").isEmpty()) {
                Admin admin = new Admin();
                admin.setEmail("admin@emart.com");
                admin.setUsername("admin"); // Satisfy legacy DB column
                admin.setPassword(passwordEncoder.encode("admin")); // Encrypt password
                admin.setActive(true);
                admin.setRole("ADMIN");
                adminRepository.save(admin);
                System.out.println("✅ Default Admin User Created: admin@emart.com / admin");
            } else {
                System.out.println("ℹ️ Admin User already exists.");
            }
        };
    }
}
