package com.example.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Emart_Card")
public class Emart_Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CardHolder_id")
    private int cardHolderId;

    @Column(name = "User_id", nullable = false)
    private int userId;

    @Column(name = "EducationQualification", length = 100)
    private String educationQualification;

    @Column(name = "Occupation", length = 100)
    private String occupation;

    @Column(
        name = "Annual_Income",
        precision = 10,
        scale = 2,
        nullable = false
    )
    private BigDecimal annualIncome;

    @Column(
        name = "Pan_Card",
        length = 10,
        nullable = false
    )
    private String panCard;

    @Column(
        name = "Bank_Details",
        length = 100,
        nullable = false
    )
    private String bankDetails;

    
    public Emart_Card() {
    }

  
//    public Emart_Card(int userId,
//                      String educationQualification,
//                      String occupation,
//                      BigDecimal annualIncome,
//                      String panCard,
//                      String bankDetails) {
//        this.userId = userId;
//        this.educationQualification = educationQualification;
//        this.occupation = occupation;
//        this.annualIncome = annualIncome;
//        this.panCard = panCard;
//        this.bankDetails = bankDetails;
//    }

   
    public int getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(int cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }
}

