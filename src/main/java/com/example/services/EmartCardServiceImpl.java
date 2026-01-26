package com.example.services;

import com.example.dto.EmartCardDTO;
import com.example.model.Customer;
import com.example.model.EmartCard;
import com.example.repository.CustomerRepository;
import com.example.repository.EmartCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmartCardServiceImpl implements EmartCardService {

    private final EmartCardRepository emartCardRepository;
    private final CustomerRepository customerRepository;

    public EmartCardServiceImpl(EmartCardRepository emartCardRepository, CustomerRepository customerRepository) {
        this.emartCardRepository = emartCardRepository;
        this.customerRepository = customerRepository;
    }

    // Apply for eMart Card
    @Override
    public EmartCard applyForCard(EmartCard card) {

        if (emartCardRepository.existsByUserId(card.getUserId())) {
            throw new RuntimeException("User already has an eMart Card");
        }

        // Verify eligibility (100 e-points)
        Customer customer = customerRepository.findByUserId(card.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getEpoints() < 100) {
            throw new RuntimeException(
                    "Eligibility check failed: You need at least 100 e-points to apply for an e-MART card. Currently you have "
                            + customer.getEpoints() + " points.");
        }

        card.setPurchaseDate(LocalDate.now());
        card.setExpiryDate(LocalDate.now().plusYears(1));
        card.setTotalEpointsUsed(0);
        card.setStatus("ACTIVE"); // Automatically granted

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
                card.getTotalEpointsUsed() + pointsUsed);

        emartCardRepository.save(card);
    }

    // Fetch card details
    @Override
    public Optional<EmartCardDTO> getCardDetails(int userId) {

        Optional<EmartCard> cardOpt = emartCardRepository.findByUserId(userId);

        if (cardOpt.isEmpty()) {
            return Optional.empty();
        }

        EmartCard card = cardOpt.get();
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Optional.of(new EmartCardDTO(
                card.getCardId(),
                card.getUserId(),
                customer.getFullName(),
                card.getPurchaseDate(),
                card.getExpiryDate(),
                card.getStatus()));
    }
}
