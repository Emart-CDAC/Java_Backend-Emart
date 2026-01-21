package com.example.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.EmartCard;
import com.example.repository.EmartCardRepository;

@Service
public class EmartCardService {

    @Autowired
    private EmartCardRepository emartCardRepository;

    // Apply for EMart Card
    public EmartCard applyCard(EmartCard card) {
        card.setApplicationStatus("PENDING");
        card.setApplyDate(LocalDate.now());
        return emartCardRepository.save(card);
    }

    // Get all card applications
    public List<EmartCard> getAllApplications() {
        return emartCardRepository.findAll();
    }

    // Get application by ID
    public EmartCard getApplicationById(int id) {
        return emartCardRepository.findById(id).orElse(null);
    }

    // Approve card
    public boolean approveCard(int id) {
        EmartCard card = emartCardRepository.findById(id).orElse(null);
        if (card != null) {
            card.setApplicationStatus("APPROVED");
            emartCardRepository.save(card);
            return true;
        }
        return false;
    }

    // Reject card
    public boolean rejectCard(int id) {
        EmartCard card = emartCardRepository.findById(id).orElse(null);
        if (card != null) {
            card.setApplicationStatus("REJECTED");
            emartCardRepository.save(card);
            return true;
        }
        return false;
    }
}
