package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Fridges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgesRepository extends JpaRepository<Fridges, Long> {
    void save(Long fridgeId);
}

