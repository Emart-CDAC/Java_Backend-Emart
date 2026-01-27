package com.example.dto;

import com.example.model.DeliveryType;
import com.example.model.PaymentMethod;

public class PlaceOrderRequest {

    private int userId;
    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;

    private Integer addressId; // for HOME_DELIVERY
    private Integer storeId; // for STORE_PICKUP

    // getters & setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
