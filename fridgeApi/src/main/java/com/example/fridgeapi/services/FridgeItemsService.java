package com.example.fridgeapi.services;

import java.util.List;

import com.example.fridgeapi.models.FridgeItems;

public interface FridgeItemsService {
    public FridgeItems getFridgeItem(Long fridgeItemId);
    public String createFridgeItem(FridgeItems fridgeItems);
    public String updateFridgeItem(FridgeItems fridgeItems);
    public String deleteFridgeItem(Long fridgeItemId);
    public List<FridgeItems> getAllFridgeItems();
}
