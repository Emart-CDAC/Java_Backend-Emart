package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Orders;
import com.example.repository.OrdersRepository;

@Service
public class OrdersServiceImp implements OrdersService 
{
	@Autowired
	 OrdersRepository ordersRepo;
	
	@Override
	public Orders addOrder(Orders orders) 
	{

		return ordersRepo.save(orders);
	}

	@Override
	public Optional<List<Orders>> getOrderById(int id) 
	{
		return Optional.empty();
	}
	
	
	
}
