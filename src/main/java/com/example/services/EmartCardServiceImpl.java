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
    @org.springframework.transaction.annotation.Transactional
    public EmartCard applyForCard(com.example.dto.ApplyEmartCardRequest request) {

        if (emartCardRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("User already has an eMart Card");
        }

        // Fetch User
        Customer customer = customerRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create new Card
        EmartCard card = new EmartCard();
        card.setUserId(request.getUserId());
        card.setAnnualIncome(request.getAnnualIncome());
        card.setPanCard(request.getPanCard());
        card.setBankDetails(request.getBankDetails());
        card.setOccupation(request.getOccupation());
        card.setEducationQualification(request.getEducationQualification());

        // Set Card Defaults
        card.setPurchaseDate(LocalDate.now());
        card.setExpiryDate(LocalDate.now().plusYears(5)); // Changed to 5 years per requirement
        card.setTotalEpointsUsed(0);
        card.setStatus("ACTIVE");

        // Save Card
        card = emartCardRepository.save(card);

        // Update Customer Profile & Points
        customer.setEmartCard(card); // Link card
        customer.setEpoints(customer.getEpoints() + 100); // Bonus Points

        // Update optional fields if provided
        if (request.getBirthDate() != null) {
            customer.setBirthDate(request.getBirthDate());
        }
        if (request.getInterests() != null) {
            customer.setInterests(request.getInterests());
        }

        customerRepository.save(customer);

        return card;
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
