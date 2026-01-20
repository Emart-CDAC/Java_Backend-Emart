package com.example.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private int userId;

    @Column(name = "Full_Name" )
    private String fullName;

    @Column(name = "Email" )
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "Mobile")
    private String mobile;

   
    @ManyToOne
    @JoinColumn(name = "Address_Id")
    private Address address;

    @Column(name = "Epoints")
    private int epoints = 0;

  
    @ManyToOne
    @JoinColumn(name = "CardHolder_id")
    private EmartCard emartCard;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "Interests")
    private String interests;

    @Column(name = "Promotional_Email")
    private boolean promotionalEmail;

    @Column(name = "Membership_Number", unique = true)
    private String membershipNumber;

    public Customer() {}

   
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public int getEpoints() { return epoints; }
    public void setEpoints(int epoints) { this.epoints = epoints; }

    public EmartCard getEmartCard() { return emartCard; }
    public void setEmartCard(EmartCard emartCard) { this.emartCard = emartCard; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public boolean isPromotionalEmail() { return promotionalEmail; }
    public void setPromotionalEmail(boolean promotionalEmail) {
        this.promotionalEmail = promotionalEmail;
    }

    public String getMembershipNumber() { return membershipNumber; }
    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }
}
