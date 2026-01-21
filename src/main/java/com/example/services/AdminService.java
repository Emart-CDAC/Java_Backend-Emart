package com.example.services;

import com.example.model.Admin;

public interface AdminService {
    Admin login(String username, String password);
}
