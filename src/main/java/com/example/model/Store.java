package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Store_id")
    private int storeId;

    @Column(name = "Store_Name", nullable = false)
    private String storeName;

    @Column(name = "City", nullable = false)
    private String city;

    @Column(name = "Address", nullable = false)
    private String address;

    
	@Column(name = "Contact_Number", nullable = false)
    private String contactNumber;

    @Column(name = "Availability")
    private boolean availability;

    public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

}
