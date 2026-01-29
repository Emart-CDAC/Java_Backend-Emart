package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Customer")
public class Customer implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_id")
	private int userId;

	@Column(name = "Full_Name", nullable = false)
	private String fullName;

	@Column(name = "Email", nullable = false, unique = true)
	private String email;

	@Column(name = "Password", nullable = true)
	private String password;

	@Column(name = "Mobile")
	private String mobile;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Address_Id")
	private Address address;

	@Column(name = "Epoints")
	private int epoints;

	@ManyToOne
	@JoinColumn(name = "CardHolder_id")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private EmartCard emartCard;

	@Column(name = "BirthDate")
	private LocalDate birthDate;

	@Column(name = "Interests")
	private String interests;

	@Column(name = "Promotional_Email")
	private boolean promotionalEmail;

	@Column(name = "Membership_Number", unique = true)
	private String membershipNumber;

	@Column(name = "role")
	private String role = "USER";

	@Enumerated(EnumType.STRING)
	@Column(name = "Auth_Provider", nullable = false)
	private AuthProvider authProvider;

	@Column(name = "Profile_Completed")
	private Boolean profileCompleted; // Changed to Boolean wrapper to handle null

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean isProfileCompleted() {
		return profileCompleted != null ? profileCompleted : false;
	}

	public void setProfileCompleted(Boolean profileCompleted) {
		this.profileCompleted = profileCompleted;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getEpoints() {
		return epoints;
	}

	public void setEpoints(int epoints) {
		this.epoints = epoints;
	}

	public EmartCard getEmartCard() {
		return emartCard;
	}

	public void setEmartCard(EmartCard emartCard) {
		this.emartCard = emartCard;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public boolean isPromotionalEmail() {
		return promotionalEmail;
	}

	public void setPromotionalEmail(boolean promotionalEmail) {
		this.promotionalEmail = promotionalEmail;
	}

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(() -> "ROLE_" + this.role);
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}
}
