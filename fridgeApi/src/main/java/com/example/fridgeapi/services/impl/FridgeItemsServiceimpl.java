package com.example.fridgeapi.services.impl;

import org.springframework.stereotype.Service;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.repositories.FridgeItemsRepository;
import com.example.fridgeapi.services.FridgeItemsService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FridgeItemsServiceimpl implements FridgeItemsService {
    FridgeItemsRepository fridgeItemsRepository;

    public FridgeItemsServiceimpl(FridgeItemsRepository fridgeItemsRepository) {
        this.fridgeItemsRepository = fridgeItemsRepository;
    }

    @Override
    public FridgeItems getFridgeItem(Long fridgeItemId) {
        return fridgeItemsRepository.findById(fridgeItemId).get();
    }

    @Override
    public String createFridgeItem(FridgeItems fridgeItems) {
        fridgeItems.setCreatedAt(LocalDateTime.now());
        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String updateFridgeItem(FridgeItems fridgeItems) {
        fridgeItems.setCreatedAt(LocalDateTime.now());
        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String deleteFridgeItem(Long fridgeItemId) {
        fridgeItemsRepository.deleteById(fridgeItemId);
        return "Success";
    }

    @Override
    public List<FridgeItems> getAllFridgeItems() {
        return fridgeItemsRepository.findAll();
    }
}