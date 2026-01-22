package com.example.dto;

public class UserDTO 
{
	 private int userId;
	 private String name;
	 private String email;
	 private boolean emCardHolder;
	 private int ePointsBalance;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmCardHolder() {
		return emCardHolder;
	}
	public void setEmCardHolder(boolean emCardHolder) {
		this.emCardHolder = emCardHolder;
	}
	public int getePointsBalance() {
		return ePointsBalance;
	}
	public void setePointsBalance(int ePointsBalance) {
		this.ePointsBalance = ePointsBalance;
	}
	 

}
