package com.example.services;

import org.springframework.web.bind.annotation.RequestParam;

public interface CheckoutService 
{
	Object selectDeliveryOption(int userId , String deliveryType );
	Object placeOrder( int userId);
}
