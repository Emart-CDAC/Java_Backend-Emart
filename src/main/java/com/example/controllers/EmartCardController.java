package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmartCardDTO;
import com.example.model.EmartCard;
import com.example.services.EmartCardService;

import java.util.Optional;

@RestController
@RequestMapping("/api/emart-card")
public class EmartCardController {

    private final EmartCardService emartCardService;

    public EmartCardController(EmartCardService emartCardService) {
        this.emartCardService = emartCardService;
    }

    @PostMapping("/apply")
    public EmartCard applyForCard(@RequestBody com.example.dto.ApplyEmartCardRequest request) {
        return emartCardService.applyForCard(request);
    }

    @PostMapping("/use-epoints")
    public String useEpoints(@RequestParam int userId,
            @RequestParam int pointsUsed) {
        emartCardService.useEpoints(userId, pointsUsed);
        return "E-points updated successfully";
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<EmartCardDTO> getCardDetails(@PathVariable int userId) {
        Optional<EmartCardDTO> card = emartCardService.getCardDetails(userId);
        return ResponseEntity.ok(card.orElse(null));
    }
}
