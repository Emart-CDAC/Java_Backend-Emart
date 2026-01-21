package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.EmartCard;

public interface EmartCardRepository extends JpaRepository<EmartCard, Integer> {
}
