package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Fridges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridges, Long> {
}
