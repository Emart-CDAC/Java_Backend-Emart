package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.*;

public interface OrderRepository extends JpaRepository<Orders, Integer>
{

}
