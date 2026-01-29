package com.example.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.AuthProvider;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===================== LOGIN (LOCAL ONLY) =====================
    @Override
    public Customer login(String email, String password) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        if (customer.getAuthProvider() == AuthProvider.GOOGLE) {
            throw new RuntimeException("This email is registered via Google SSO. Please use 'Log in with Google'.");
        }

        if (customer.getPassword() == null || !passwordEncoder.matches(password, customer.getPassword())) {
            throw new RuntimeException("Invalid password. Please try again.");
        }

        return customer;
    }

    // ===================== NORMAL REGISTER (LOCAL) =====================
    @Override
    public Customer registerUser(Customer customer) {

        Optional<Customer> existing = customerRepository.findByEmail(customer.getEmail());
        if (existing.isPresent()) {
            Customer exist = existing.get();
            // Allow update if it's an existing Google user completing profile
            if (exist.getAuthProvider() == AuthProvider.GOOGLE) {
                // We assume the frontend passed AuthProvider.GOOGLE or we infer it?
                // Currently Register.jsx passes data. We should check if we are allowed to
                // update.
                // If profile is NOT completed, we allow update.
                if (!exist.isProfileCompleted()) {
                    exist.setFullName(customer.getFullName());
                    exist.setMobile(customer.getMobile());
                    // Note: Address is separate entity usually mapped via customer.setAddress

                    if (customer.getAddress() != null) {
                        customer.getAddress().setCustomer(exist);
                        exist.setAddress(customer.getAddress());
                    }

                    exist.setProfileCompleted(true);
                    return customerRepository.save(exist);
                } else {
                    throw new RuntimeException("This email is already registered via Google SSO");
                }
            } else {
                throw new RuntimeException("This email is already registered. Please login.");
            }
        }

        customer.setAuthProvider(AuthProvider.LOCAL);
        customer.setProfileCompleted(true);

        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }

        if (customer.getAddress() != null) {
            customer.getAddress().setCustomer(customer);
        }

        return customerRepository.save(customer);
    }

    // ===================== GOOGLE SSO ENTRY =====================
    public Customer processGoogleLogin(String email, String fullName) {

        Optional<Customer> existing = customerRepository.findByEmail(email);

        if (existing.isPresent()) {
            Customer customer = existing.get();
            if (customer.getAuthProvider() == AuthProvider.LOCAL) {
                throw new RuntimeException(
                        "This email is registered with a password. Please login using email/password.");
            }
            return customer;
        }

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFullName(fullName);
        customer.setAuthProvider(AuthProvider.GOOGLE);
        customer.setProfileCompleted(false);
        customer.setPassword(null);

        return customerRepository.save(customer);
    }

    // ===================== COMPLETE REGISTRATION (SSO USER) =====================
    public Customer completeRegistration(int userId, Customer data) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getAuthProvider() != AuthProvider.GOOGLE) {
            throw new RuntimeException("Registration completion is only for Google users");
        }

        customer.setFullName(data.getFullName());
        customer.setMobile(data.getMobile());
        customer.setBirthDate(data.getBirthDate());
        customer.setInterests(data.getInterests());
        customer.setPromotionalEmail(data.isPromotionalEmail());

        if (data.getAddress() != null) {
            data.getAddress().setCustomer(customer);
            customer.setAddress(data.getAddress());
        }

        customer.setProfileCompleted(true);

        return customerRepository.save(customer);
    }

    // ===================== PROFILE =====================
    @Override
    public Customer getUserById(int userId) {
        return customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Customer updateUser(int userId, Customer updatedCustomer) {

        Customer existingCustomer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingCustomer.setFullName(updatedCustomer.getFullName());
        existingCustomer.setMobile(updatedCustomer.getMobile());
        existingCustomer.setBirthDate(updatedCustomer.getBirthDate());
        existingCustomer.setInterests(updatedCustomer.getInterests());

        return customerRepository.save(existingCustomer);
    }

    // ===================== PASSWORD CHANGE (LOCAL ONLY) =====================
    @Override
    public String changePassword(int userId, String oldPassword, String newPassword) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getAuthProvider() == AuthProvider.GOOGLE) {
            throw new RuntimeException("Password change not allowed for Google users");
        }

        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);

        return "Password updated successfully";
    }
}