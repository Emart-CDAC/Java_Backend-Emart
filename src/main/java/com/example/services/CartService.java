package com.example.services;

public interface CartService 
{
	Object addToCart(int userId , int productId , int quantity);
	void removeFromCart(int cartItemId);
	Object updateQuantity(int cartItemId , int quantity);
	Object viewCart(int userId);

}
