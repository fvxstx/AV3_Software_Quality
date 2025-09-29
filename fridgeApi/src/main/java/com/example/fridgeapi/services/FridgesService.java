package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Fridges;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface FridgesService {
    String createFridge(Fridges fridge);
    String updateFridge(Fridges fridge);
    String deleteFridge(Long id);
    Fridges getFridge(Long id);
    List<Fridges> getAllFridges();
}
