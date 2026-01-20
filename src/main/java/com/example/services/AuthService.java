package com.example.services;
public interface AuthService {

    String login(String email, String password);

    String logout();

    String forgotPassword(String email);

    String resetPassword(String email, String newPassword);
}
