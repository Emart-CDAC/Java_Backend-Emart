package com.example.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.*;
import com.example.dto.*;
import com.example.repository.*;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	private static final double PLATFORM_FEE = 23.0;
	private static final double EPOINT_TO_RUPEE = 1.0;

	// ============================
	// ADD TO CART
	// ============================
	@Override
	@Transactional
	public CartItems addToCart(
			int userId,
			int productId,
			int quantity,
			String purchaseType,
			int epointsUsed) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		int availablePoints = customer.getEpoints();

		Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> {
			Cart c = new Cart();
			c.setCustomer(customer);
			c.setTotalMrp(0.0);
			c.setCouponDiscount(0.0);
			c.setPlatformFee(0.0);
			c.setUsedEpoints(0);
			c.setEpointDiscount(0.0);
			c.setEarnedEpoints(0);
			c.setFinalPayableAmount(0.0);
			c.setTotalAmount(0.0);
			return cartRepository.save(c);
		});

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		CartItems item = cartItemRepository
				.findByCartAndProduct(cart, product)
				.orElseGet(() -> {
					CartItems ci = new CartItems();
					ci.setEpointsUsed(0); // 🔥 VERY IMPORTANT
					return ci;
				});

		int finalQuantity = item.getCartItemId() == 0
				? quantity
				: item.getQuantity() + quantity;

		int finalEpointsUsed = 0;
		String finalPurchaseType = purchaseType == null ? "NORMAL" : purchaseType;

		if ("FULL_EP".equals(finalPurchaseType)) {
			finalEpointsUsed = (int) (product.getNormalPrice() * finalQuantity);
			if (finalEpointsUsed > availablePoints) {
				throw new RuntimeException("Insufficient e-points. Available: " + availablePoints);
			}
		} else if ("PARTIAL_EP".equals(finalPurchaseType)) {
			if (epointsUsed < 0 || epointsUsed > availablePoints) {
				throw new RuntimeException("Invalid e-points. Available: " + availablePoints);
			}
			finalEpointsUsed = epointsUsed;
		}

		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(finalQuantity);
		item.setPurchaseType(finalPurchaseType);
		item.setEpointsUsed(finalEpointsUsed);

		double subtotal = product.getNormalPrice() * finalQuantity;
		item.setSubtotal(subtotal);
		item.setTotalPrice(subtotal);

		CartItems savedItem = cartItemRepository.save(item);
		recalculateCartPricing(cart);

		return savedItem;
	}

	// ============================
	// PRICING ENGINE
	// ============================
	private void recalculateCartPricing(Cart cart) {

		List<CartItems> items = cartItemRepository.findByCart(cart);

		double totalMrp = 0.0;
		int totalEpointsUsed = 0;

		for (CartItems item : items) {
			totalMrp += item.getProduct().getNormalPrice() * item.getQuantity();

			Integer used = item.getEpointsUsed();
			totalEpointsUsed += (used == null ? 0 : used);
		}

		cart.setTotalMrp(totalMrp);

		double platformFee = items.isEmpty() ? 0.0 : PLATFORM_FEE;
		cart.setPlatformFee(platformFee);

		double epointDiscount = totalEpointsUsed * EPOINT_TO_RUPEE;
		if (epointDiscount > totalMrp) {
			epointDiscount = totalMrp;
		}

		cart.setUsedEpoints(totalEpointsUsed);
		cart.setEpointDiscount(epointDiscount);

		Double coupon = cart.getCouponDiscount();
		double couponDiscount = coupon == null ? 0.0 : coupon;

		double finalAmount = totalMrp - epointDiscount - couponDiscount + platformFee;
		if (finalAmount < 0)
			finalAmount = 0;

		cart.setFinalPayableAmount(finalAmount);
		cart.setTotalAmount(finalAmount);

		double cashPaid = totalMrp - epointDiscount - couponDiscount;
		if (cashPaid < 0)
			cashPaid = 0;

		cart.setEarnedEpoints((int) (cashPaid * 0.10));

		cartRepository.save(cart);
	}

	// ============================
	// CART SUMMARY
	// ============================
	@Override
	public CartResponseDTO getCartSummary(int userId) {

		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> {
			Cart c = new Cart();
			c.setCustomer(customer);
			c.setTotalMrp(0.0);
			c.setCouponDiscount(0.0);
			c.setPlatformFee(0.0);
			c.setUsedEpoints(0);
			c.setEpointDiscount(0.0);
			c.setEarnedEpoints(0);
			c.setFinalPayableAmount(0.0);
			c.setTotalAmount(0.0);
			return cartRepository.save(c);
		});

		recalculateCartPricing(cart);
		return convertToDTO(cart, customer);
	}

	// ============================
	// DTO MAPPING
	// ============================
	private CartResponseDTO convertToDTO(Cart cart, Customer customer) {

		CartResponseDTO dto = new CartResponseDTO();

		List<CartItemDTO> items = cartItemRepository.findByCart(cart)
				.stream()
				.map(item -> {
					CartItemDTO d = new CartItemDTO();
					d.setCartItemId(item.getCartItemId());
					d.setProductId(item.getProduct().getId());
					d.setProductName(item.getProduct().getName());
					d.setQuantity(item.getQuantity());
					d.setPrice(item.getProduct().getNormalPrice());
					d.setDiscountedPrice(item.getTotalPrice());
					d.setPurchaseType(item.getPurchaseType());
					d.setEpointsUsed(item.getEpointsUsed() == null ? 0 : item.getEpointsUsed());
					d.setImageUrl(item.getProduct().getImageUrl());
					return d;
				}).collect(Collectors.toList());

		dto.setItems(items);
		dto.setTotalMrp(cart.getTotalMrp());
		dto.setEpointDiscount(cart.getEpointDiscount());
		dto.setCouponDiscount(cart.getCouponDiscount() == null ? 0.0 : cart.getCouponDiscount());
		dto.setPlatformFee(cart.getPlatformFee());
		dto.setFinalPayableAmount(cart.getFinalPayableAmount());
		dto.setTotalAmount(cart.getFinalPayableAmount());
		dto.setUsedEpoints(cart.getUsedEpoints());
		dto.setEarnedEpoints(cart.getEarnedEpoints());
		dto.setAvailableEpoints(customer.getEpoints());

		return dto;
	}

	@Override
	@Transactional
	public void removeFromCart(int cartItemId) {
		CartItems item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		Cart cart = item.getCart();
		cartItemRepository.delete(item);

		// 🔹 Recalculate totals after removal
		recalculateCartPricing(cart);
	}

	@Override
	@Transactional
	public CartItems updateQuantity(int cartItemId, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		CartItems item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		item.setQuantity(quantity);

		// 🔹 Always normalize epointsUsed
		if (item.getPurchaseType() == null || "NORMAL".equals(item.getPurchaseType())) {
			item.setEpointsUsed(0);
		}

		if ("FULL_EP".equals(item.getPurchaseType())) {
			int required = (int) (item.getProduct().getNormalPrice() * quantity);
			item.setEpointsUsed(required);
		}

		if ("PARTIAL_EP".equals(item.getPurchaseType())) {
			Integer used = item.getEpointsUsed();
			item.setEpointsUsed(used != null ? used : 0);
		}

		double subtotal = item.getProduct().getNormalPrice() * quantity;
		item.setSubtotal(subtotal);
		item.setTotalPrice(subtotal);

		CartItems saved = cartItemRepository.save(item);

		// 🔹 VERY IMPORTANT
		recalculateCartPricing(item.getCart());

		return saved;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CartItems> viewCart(int userId) {

		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Cart cart = cartRepository.findByCustomer(customer)
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		return cartItemRepository.findByCart(cart);
	}

	@Override
	@Transactional
	public void clearCartByUser(int userId) {

		Customer customer = customerRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Cart cart = cartRepository.findByCustomer(customer)
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		// 🔹 Delete all cart items
		cartItemRepository.deleteByCart_CartId(cart.getCartId());

		// 🔹 Reset cart totals safely
		cart.setTotalMrp(0);
		cart.setCouponDiscount(0.0);
		cart.setPlatformFee(0);
		cart.setUsedEpoints(0);
		cart.setEpointDiscount(0);
		cart.setEarnedEpoints(0);
		cart.setFinalPayableAmount(0);
		cart.setTotalAmount(0);

		cartRepository.save(cart);
	}

}
