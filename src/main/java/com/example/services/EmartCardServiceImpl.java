
package com.example.services;

import com.example.model.EmartCard;
import com.example.repository.EmartCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmartCardServiceImpl implements EmartCardService {

    private final EmartCardRepository emartCardRepository;

    public EmartCardServiceImpl(EmartCardRepository emartCardRepository) {
        this.emartCardRepository = emartCardRepository;
    }

    // Apply for eMart Card
    @Override
    public EmartCard applyForCard(EmartCard card) {

        if (emartCardRepository.existsByUserId(card.getUserId())) {
            throw new RuntimeException("User already has an eMart Card");
        }

        card.setPurchaseDate(LocalDate.now());
        card.setExpiryDate(LocalDate.now().plusYears(1));
        card.setTotalEpointsUsed(0);
        card.setStatus("ACTIVE");

        return emartCardRepository.save(card);
    }

    // Use e-points
    @Override
    public void useEpoints(int userId, int pointsUsed) {

        EmartCard card = emartCardRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("eMart Card not found"));

        if (!"ACTIVE".equals(card.getStatus())) {
            throw new RuntimeException("Card is not active");
        }

        card.setTotalEpointsUsed(
                card.getTotalEpointsUsed() + pointsUsed
        );

        emartCardRepository.save(card);
    }

    // Fetch card details
    @Override
    public EmartCard getCardDetails(int userId) {

        return emartCardRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("eMart Card not found"));
    }
}
