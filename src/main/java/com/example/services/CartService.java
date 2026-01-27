package com.example.services;

import java.util.List;
import com.example.model.*;

public interface CartService {

    CartItems addToCart(int userId, int productId, int quantity, String purchaseType, int epointsUsed);

    void removeFromCart(int cartItemId);

    CartItems updateQuantity(int cartItemId, int quantity);

    List<CartItems> viewCart(int userId);

    void clearCartByUser(int userId);

    com.example.dto.CartResponseDTO getCartSummary(int userId);

}
