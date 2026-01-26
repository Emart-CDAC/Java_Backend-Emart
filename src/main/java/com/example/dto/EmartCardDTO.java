package com.example.dto;

import java.time.LocalDate;

public class EmartCardDTO {
    private int cardId;
    private int userId;
    private String fullName;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private String status;

    public EmartCardDTO() {
    }

    public EmartCardDTO(int cardId, int userId, String fullName, LocalDate purchaseDate, LocalDate expiryDate,
            String status) {
        this.cardId = cardId;
        this.userId = userId;
        this.fullName = fullName;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
