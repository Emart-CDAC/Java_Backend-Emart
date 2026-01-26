package com.example.dto;

public class ApplyEpointsDTO {
    private int userId;
    private int epointsToRedeem;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEpointsToRedeem() {
        return epointsToRedeem;
    }

    public void setEpointsToRedeem(int epointsToRedeem) {
        this.epointsToRedeem = epointsToRedeem;
    }
}
