package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Cart_Items;
import com.example.model.Customer;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.CustomerRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartrepository;

    @Autowired
    CartItemRepository cartitemrepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Object addToCart(int userId, int productId, int quantity) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartrepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomer(customer);
                    c.setTotalAmount(BigDecimal.ZERO);
                    return cartrepository.save(c);
                });

        Cart_Items item = new Cart_Items();   
        item.setCart(cart);
        item.setProduct(productId);
        item.setQuantity(quantity);

        return cartitemrepository.save(item);
    }

    @Override
    public void removeFromCart(int cartItemId) {
        cartitemrepository.deleteById(cartItemId);
    }

    @Override
    public Object updateQuantity(int cartItemId, int quantity) {

        Cart_Items item = cartitemrepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);
        return cartitemrepository.save(item);
    }

    @Override
    public Object viewCart(int userId) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartrepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartitemrepository.findByCart(cart);
    }
}
