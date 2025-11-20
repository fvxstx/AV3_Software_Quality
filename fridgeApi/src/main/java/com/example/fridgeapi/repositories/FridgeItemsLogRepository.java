package com.example.fridgeapi.repositories;
import com.example.fridgeapi.models.FridgeItemsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeItemsLogRepository extends JpaRepository<FridgeItemsLog, Long> {
}
