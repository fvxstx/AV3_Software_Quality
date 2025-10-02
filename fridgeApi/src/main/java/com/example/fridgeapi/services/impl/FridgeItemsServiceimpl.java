package com.example.fridgeapi.services.impl;

import org.springframework.stereotype.Service;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.repositories.FridgeItemsRepository;
import com.example.fridgeapi.repositories.FridgesRepository;
import com.example.fridgeapi.services.FridgeItemsService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FridgeItemsServiceimpl implements FridgeItemsService {
    FridgeItemsRepository fridgeItemsRepository;
    FridgesRepository fridgesRepository;

    public FridgeItemsServiceimpl(FridgeItemsRepository fridgeItemsRepository, FridgesRepository fridgesRepository) {
        this.fridgeItemsRepository = fridgeItemsRepository;
        this.fridgesRepository = fridgesRepository;
    }

    @Override
    public FridgeItems getFridgeItem(Long fridgeItemId) {
        return fridgeItemsRepository.findById(fridgeItemId).orElse(null);
    }

    @Override
    public String createFridgeItem(FridgeItems fridgeItems) {

        Long fridgeId = fridgeItems.getFridgeId(); 

        if (fridgeId == null) {
            return "fridgeId is required";
        }

        boolean fridgeExists = fridgesRepository.existsById(fridgeId);

        if (!fridgeExists) {
            return "fridge not found";
        }

        fridgeItems.setCreatedAt(LocalDateTime.now());
        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String updateFridgeItem(FridgeItems fridgeItems) {
        fridgeItems.setUpdatedAt(LocalDateTime.now());
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