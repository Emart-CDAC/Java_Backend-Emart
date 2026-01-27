package com.example.dto;

import com.example.model.DeliveryType;
import com.example.model.PaymentMethod;

public class PlaceOrderRequest {

    private int userId;
    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;

    private Long addressId; // for HOME_DELIVERY
    private Long storeId; // for STORE_PICKUP

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

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
