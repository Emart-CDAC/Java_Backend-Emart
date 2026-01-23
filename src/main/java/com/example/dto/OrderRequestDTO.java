package com.example.dto;

public class OrderRequestDTO 
{
	private int userId;
    private boolean useEPoints;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isUseEPoints() {
		return useEPoints;
	}
	public void setUseEPoints(boolean useEPoints) {
		this.useEPoints = useEPoints;
	}
    

}
