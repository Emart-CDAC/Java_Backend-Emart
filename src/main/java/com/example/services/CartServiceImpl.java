package com.example.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	private static final BigDecimal PLATFORM_FEE = new BigDecimal("23.00");
	private static final BigDecimal EPOINT_TO_RUPEE = new BigDecimal("1.00");

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
			c.setTotalMrp(BigDecimal.ZERO);
			c.setCouponDiscount(BigDecimal.ZERO);
			c.setPlatformFee(BigDecimal.ZERO);
			c.setUsedEpoints(0);
			c.setEpointDiscount(BigDecimal.ZERO);
			c.setEarnedEpoints(0);
			c.setFinalPayableAmount(BigDecimal.ZERO);
			c.setTotalAmount(BigDecimal.ZERO);
			return cartRepository.save(c);
		});

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		CartItems item = cartItemRepository
				.findByCartAndProduct(cart, product)
				.orElseGet(() -> {
					CartItems ci = new CartItems();
					ci.setEpointsUsed(0);
					return ci;
				});

		int finalQuantity = item.getCartItemId() == 0
				? quantity
				: item.getQuantity() + quantity;

		int finalEpointsUsed = 0;
		String finalPurchaseType = purchaseType == null ? "NORMAL" : purchaseType;

		// Use Normal Price for E-points redemption calculation as per existing logic
		// (or should it be effective price?
		// "ALL calculations must use discounted price if discount_percent > 0".
		// Assuming redemption is also based on effective price to be consistent, but
		// usually points redeem against MRP.
		// However, "ALL calculations must use discounted price" implies this too.
		// Let's stick to getEffectivePrice() for consistency).
		// Wait, user said "Normal price should NEVER be used for rewards". Rewards
		// usually means earning.
		// For spending/redemption, if I have 50% discount, price is half.
		// If I select "FULL_EP", I should pay effective price in points.
		BigDecimal effectivePrice = product.getEffectivePrice();

		if ("FULL_EP".equals(finalPurchaseType)) {
			// finalEpointsUsed = (int) (product.getNormalPrice() * finalQuantity);
			// Using effective price for redemption cost too
			finalEpointsUsed = effectivePrice.multiply(BigDecimal.valueOf(finalQuantity)).intValue();

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

		BigDecimal subtotal = effectivePrice.multiply(BigDecimal.valueOf(finalQuantity));
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

		BigDecimal totalMrp = BigDecimal.ZERO;
		int totalEpointsUsed = 0;

		for (CartItems item : items) {
			// Use effective price for total calculation
			BigDecimal itemPrice = item.getProduct().getEffectivePrice();
			totalMrp = totalMrp.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));

			Integer used = item.getEpointsUsed();
			totalEpointsUsed += (used == null ? 0 : used);
		}

		cart.setTotalMrp(totalMrp);

		BigDecimal platformFee = items.isEmpty() ? BigDecimal.ZERO : PLATFORM_FEE;
		cart.setPlatformFee(platformFee);

		BigDecimal epointDiscount = BigDecimal.valueOf(totalEpointsUsed).multiply(EPOINT_TO_RUPEE);
		if (epointDiscount.compareTo(totalMrp) > 0) {
			epointDiscount = totalMrp;
		}

		cart.setUsedEpoints(totalEpointsUsed);
		cart.setEpointDiscount(epointDiscount);

		BigDecimal coupon = cart.getCouponDiscount();
		BigDecimal couponDiscount = coupon == null ? BigDecimal.ZERO : coupon;

		// finalAmount = totalMrp - epointDiscount - couponDiscount + platformFee
		BigDecimal finalAmount = totalMrp.subtract(epointDiscount).subtract(couponDiscount).add(platformFee);
		if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
			finalAmount = BigDecimal.ZERO;

		// Calculate GST (10%)
		BigDecimal gstAmount = finalAmount.multiply(new BigDecimal("0.10"));
		BigDecimal finalPayableWithGst = finalAmount.add(gstAmount);

		cart.setFinalPayableAmount(finalPayableWithGst);
		cart.setTotalAmount(finalPayableWithGst);

		// cashPaid = totalMrp - epointDiscount - couponDiscount
		BigDecimal cashPaid = totalMrp.subtract(epointDiscount).subtract(couponDiscount);
		if (cashPaid.compareTo(BigDecimal.ZERO) < 0)
			cashPaid = BigDecimal.ZERO;

		// ePoints = cashPaid * 0.10
		cart.setEarnedEpoints(cashPaid.multiply(new BigDecimal("0.10")).intValue());

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
			c.setTotalMrp(BigDecimal.ZERO);
			c.setCouponDiscount(BigDecimal.ZERO);
			c.setPlatformFee(BigDecimal.ZERO);
			c.setUsedEpoints(0);
			c.setEpointDiscount(BigDecimal.ZERO);
			c.setEarnedEpoints(0);
			c.setFinalPayableAmount(BigDecimal.ZERO);
			c.setTotalAmount(BigDecimal.ZERO);
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

		// Calculate Real Total MRP (Sum of Normal Prices)
		BigDecimal realTotalMrp = BigDecimal.ZERO;

		List<CartItemDTO> items = cartItemRepository.findByCart(cart)
				.stream()
				.map(item -> {
					CartItemDTO d = new CartItemDTO();
					d.setCartItemId(item.getCartItemId());
					d.setProductId(item.getProduct().getId());
					d.setProductName(item.getProduct().getName());
					d.setQuantity(item.getQuantity());
					d.setPrice(item.getProduct().getNormalPrice()); // Regular price
					// Use effective price for discounted price
					d.setDiscountedPrice(item.getProduct().getEffectivePrice());
					d.setPurchaseType(item.getPurchaseType());
					d.setEpointsUsed(item.getEpointsUsed() == null ? 0 : item.getEpointsUsed());
					d.setImageUrl(item.getProduct().getImageUrl());
					return d;
				}).collect(Collectors.toList());

		// Re-iterate (or could have done in stream, but stream is map) to get total
		// Real MRP
		for (CartItems item : cartItemRepository.findByCart(cart)) {
			realTotalMrp = realTotalMrp
					.add(item.getProduct().getNormalPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		}

		dto.setItems(items);

		// Set Real MRP in DTO
		dto.setTotalMrp(realTotalMrp);

		// Offer Discount = Real MRP - Effective MRP (which is stored in cart.totalMrp)
		dto.setOfferDiscount(realTotalMrp.subtract(cart.getTotalMrp()));

		dto.setEpointDiscount(cart.getEpointDiscount());
		dto.setCouponDiscount(cart.getCouponDiscount() == null ? BigDecimal.ZERO : cart.getCouponDiscount());
		dto.setPlatformFee(cart.getPlatformFee());

		dto.setFinalPayableAmount(cart.getFinalPayableAmount());
		dto.setTotalAmount(cart.getFinalPayableAmount());

		// Reverse calculate GST or just calculate from Pre-GST base
		// Since we just calculated it in recalculateCartPricing: GST = Payable -
		// (Payable / 1.1) roughly,
		// but cleaner to calculate from implied Pre-GST amount.
		// Pre-GST = Payable / 1.1
		BigDecimal preGstAmount = cart.getFinalPayableAmount().divide(new BigDecimal("1.1"), 2, RoundingMode.HALF_UP);
		dto.setGstAmount(cart.getFinalPayableAmount().subtract(preGstAmount));

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

		BigDecimal effectivePrice = item.getProduct().getEffectivePrice();

		if ("FULL_EP".equals(item.getPurchaseType())) {
			int required = effectivePrice.multiply(BigDecimal.valueOf(quantity)).intValue();
			item.setEpointsUsed(required);
		}

		if ("PARTIAL_EP".equals(item.getPurchaseType())) {
			Integer used = item.getEpointsUsed();
			item.setEpointsUsed(used != null ? used : 0);
		}

		BigDecimal subtotal = effectivePrice.multiply(BigDecimal.valueOf(quantity));
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
		cart.setTotalMrp(BigDecimal.ZERO);
		cart.setCouponDiscount(BigDecimal.ZERO);
		cart.setPlatformFee(BigDecimal.ZERO);
		cart.setUsedEpoints(0);
		cart.setEpointDiscount(BigDecimal.ZERO);
		cart.setEarnedEpoints(0);
		cart.setFinalPayableAmount(BigDecimal.ZERO);
		cart.setTotalAmount(BigDecimal.ZERO);

		cartRepository.save(cart);
	}

}
