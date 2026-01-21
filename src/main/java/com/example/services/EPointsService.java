package com.example.services;

public interface EPointsService {

	int creditPoints(int userId, int epoints);

	int redeemPoints(int userId, int epoints);

	int getBalance(int userId);
}