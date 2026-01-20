package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Emart_Card")
public class EmartCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CardHolder_id")
    private int cardHolderId;

    @Column(name = "EducationQualification")
    private String educationQualification;

    @Column(name = "Occupation")
    private String occupation;

    @Column(name = "Annual_Income", nullable = false)
    private double annualIncome;

    @Column(name = "Pan_Card", nullable = false)
    private String panCard;

    @Column(name = "Bank_Details", nullable = false)
    private String bankDetails;

	public int getCardHolderId() {
		return cardHolderId;
	}

	public void setCardHolderId(int cardHolderId) {
		this.cardHolderId = cardHolderId;
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

	public double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(double annualIncome) {
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
