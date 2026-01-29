package com.example.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.DailySalesDTO;
import com.example.dto.LoyaltyProductDTO;
import com.example.dto.ProductOfferInventoryDTO;
import com.example.dto.WeeklyRevenueDTO;
import com.example.integration.AnalyticsApiClient;

@RestController
@RequestMapping("/api/admin/analytics")
public class AdminAnalyticsController {

	private final AnalyticsApiClient analyticsApiClient;

	public AdminAnalyticsController(AnalyticsApiClient analyticsApiClient) {
		this.analyticsApiClient = analyticsApiClient;
	}
	
	@GetMapping("/product-offers-inventory")
	public List<ProductOfferInventoryDTO> productOffersInventory() {
	    return analyticsApiClient.getProductOffersInventory();
	}


//	@GetMapping("/daily-sales")
//	public List<DailySalesDTO> dailySales() {
//		return analyticsApiClient.getDailySales();
//	}
//
//	@GetMapping("/weekly-revenue")
//	public List<WeeklyRevenueDTO> weeklyRevenue() {
//		return analyticsApiClient.getWeeklyRevenue();
//	}
//
//	@GetMapping("/loyalty-products")
//	public List<LoyaltyProductDTO> loyaltyProducts() {
//		return analyticsApiClient.getLoyaltyProducts();
//	}
}
