package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Address_Id")
	private int addressId;

	@ManyToOne
	@JoinColumn(name = "User_id")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private Customer customer;

	@Column(name = "Town", nullable = true)
	private String town;

	@Column(name = "State", nullable = true)
	private String state;

	@Column(name = "Country", nullable = true)
	private String country;

	@Column(name = "City", nullable = true)
	private String city;

	@Column(name = "Pincode", nullable = true)
	private String pincode;

	@Column(name = "Landmark")
	private String landmark;

	@Column(name = "HouseNumber")
	private String houseNumber;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return houseNumber + ", " + landmark + ", " + town + ", " + city + ", " + state + ", " + country + " - "
				+ pincode;
	}

}
