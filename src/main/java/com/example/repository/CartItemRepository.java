package com.example.repository;

import java.util.List;
import com.example.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<Cart_Items, Integer>
{
	List<Cart_Items> findByCartId(int cartId);

}
