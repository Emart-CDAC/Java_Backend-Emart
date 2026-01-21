package com.example.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.EmartCard;
import com.example.repository.EmartCardRepository;

@RestController
@RequestMapping("/emart-card")
public class EmartCardController {

    @Autowired
    private EmartCardRepository emartCardRepository;

    @PostMapping("/apply")
    public ResponseEntity<String> applyEmartCard(@RequestBody EmartCard card) {

        card.setApplicationStatus("PENDING");
        card.setApplyDate(LocalDate.now());

        emartCardRepository.save(card);

        return ResponseEntity.ok("EMart Card application submitted successfully");
    }

    @GetMapping("/all")
    public List<EmartCard> getAllApplications() {
        return emartCardRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmartCard> getApplicationById(@PathVariable int id) {

        return emartCardRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveCard(@PathVariable int id) {

        return emartCardRepository.findById(id).map(card -> {
            card.setApplicationStatus("APPROVED");
            emartCardRepository.save(card);
            return ResponseEntity.ok("EMart Card approved");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectCard(@PathVariable int id) {

        return emartCardRepository.findById(id).map(card -> {
            card.setApplicationStatus("REJECTED");
            emartCardRepository.save(card);
            return ResponseEntity.ok("EMart Card rejected");
        }).orElse(ResponseEntity.notFound().build());
    }
}
