package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.EmartCard;

public interface EmartCardRepository extends JpaRepository<EmartCard, Integer> {

    Optional<EmartCard> findByUserId(int userId);

    boolean existsByUserId(int userId);
}