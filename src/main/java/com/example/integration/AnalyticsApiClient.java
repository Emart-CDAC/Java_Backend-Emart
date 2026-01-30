package com.example.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.DailySalesDTO;
import com.example.dto.LoyaltyProductDTO;
import com.example.dto.ProductOfferInventoryDTO;
import com.example.dto.WeeklyRevenueDTO;

@Service
public class AnalyticsApiClient {

	@Autowired
	private RestTemplate restTemplate;

	private static final String BASE_URL = "http://localhost:5169/api/reports";

	public List<ProductOfferInventoryDTO> getProductOffersInventory() {
		try {
			return Arrays.asList(restTemplate.getForObject(BASE_URL + "/product-offers-inventory",
					ProductOfferInventoryDTO[].class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to .NET backend: " + e.getMessage());
		}
	}

}
