package com.example.model;

import jakarta.persistence.*;

@Entity
public class Store {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Store_id;
	
	private String Store_Name;
	private String City;
	private String Address;
	private String Contact_Number;
	private boolean Availability;
	
	public int getStore_id() {
		return Store_id;
	}
	public void setStore_id(int Store_id) {
		this.Store_id = Store_id;
	}

	public String getStore_name() {
		return Store_Name;
	}

	public void setStore_name(String Store_Name) {
		this.Store_Name = Store_Name;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getContact_number() {
		return Contact_Number;
	}

	public void setContact_number(String Contact_Number) {
		this.Contact_Number = Contact_Number;
	}

	public boolean isAvailability() {
		return Availability;
	}

	public void setAvailability(boolean Availability) {
		this.Availability = Availability;
	}

	
	
}


