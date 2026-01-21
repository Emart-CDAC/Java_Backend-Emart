package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

public interface EPointsService {

    int creditPoints(int userId, int epoints);

    int redeemPoints(int userId, int epoints);

    int getBalance(int userId);
}