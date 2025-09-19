package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Fridges;

import java.util.List;

public interface FridgesService {
    List<Fridges> findAllFridges();
    Fridges findFridgesById(Long id);
    Fridges saveFridges(Fridges fridges);
    void deleteFridgesById(Long id);
}
