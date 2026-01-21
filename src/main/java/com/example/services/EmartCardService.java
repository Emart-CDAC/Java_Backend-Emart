package com.example.services;

import com.example.model.EmartCard;

public interface EmartCardService {

    EmartCard applyForCard(EmartCard card);

    void useEpoints(int userId, int pointsUsed);

    EmartCard getCardDetails(int userId);
}