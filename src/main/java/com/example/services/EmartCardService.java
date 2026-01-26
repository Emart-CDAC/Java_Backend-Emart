package com.example.services;

import java.util.Optional;
import com.example.dto.EmartCardDTO;
import com.example.model.EmartCard;

public interface EmartCardService {

    EmartCard applyForCard(EmartCard card);

    void useEpoints(int userId, int pointsUsed);

    Optional<EmartCardDTO> getCardDetails(int userId);
}