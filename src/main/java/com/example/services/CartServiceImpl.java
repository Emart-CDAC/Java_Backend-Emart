package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.CartItems;
import com.example.model.Customer;
import com.example.model.Product;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItems addToCart(int userId, int productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomer(customer);
                    c.setTotalAmount(0.0);
                    return cartRepository.save(c);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(new CartItems());

        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(item.getQuantity() + quantity);

        
        double subtotal = product.getNormalPrice() * item.getQuantity();
        item.setSubtotal(subtotal);
        item.setTotalPrice(subtotal);

        CartItems savedItem = cartItemRepository.save(item);
        updateCartTotal(cart);

        return savedItem;
    }

    @Override
    public void removeFromCart(int cartItemId) {

        CartItems item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();
        cartItemRepository.delete(item);

        updateCartTotal(cart);
    }

    @Override
    public CartItems updateQuantity(int cartItemId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        CartItems item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);

        
        double subtotal = item.getProduct().getNormalPrice() * quantity;
        item.setSubtotal(subtotal);
        item.setTotalPrice(subtotal);

        CartItems updatedItem = cartItemRepository.save(item);
        updateCartTotal(item.getCart());

        return updatedItem;
    }

    @Override
    public List<CartItems> viewCart(int userId) {

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCart(cart);
    }

    private void updateCartTotal(Cart cart) {

        List<CartItems> items = cartItemRepository.findByCart(cart);

        double total = items.stream()
                .mapToDouble(CartItems::getTotalPrice)
                .sum();

        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }
}
