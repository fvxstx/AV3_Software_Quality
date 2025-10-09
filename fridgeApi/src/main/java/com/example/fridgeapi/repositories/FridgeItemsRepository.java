package com.example.fridgeapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fridgeapi.models.FridgeItems;

public interface FridgeItemsRepository extends JpaRepository<FridgeItems, Long> {
    List<FridgeItems> findByFridgeId(Long fridgeId);
    
}
