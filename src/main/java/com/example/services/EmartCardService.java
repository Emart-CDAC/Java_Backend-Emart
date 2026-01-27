package com.example.services;

import java.util.Optional;
import com.example.dto.EmartCardDTO;
import com.example.dto.ApplyEmartCardRequest;
import com.example.model.EmartCard;

public interface EmartCardService {

    EmartCard applyForCard(ApplyEmartCardRequest request);

    void useEpoints(int userId, int pointsUsed);

    Optional<EmartCardDTO> getCardDetails(int userId);
}