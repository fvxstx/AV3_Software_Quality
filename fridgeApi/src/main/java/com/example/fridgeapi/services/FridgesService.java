package com.example.fridgeapi.services;


import java.util.List;

import com.example.fridgeapi.models.Fridges;

public interface FridgesService {
    public Fridges getFridge(Long fridgeID);
    public String createFridge(Fridges fridges);
    public String updateFridge(Fridges fridges);
    public String deleteFridge(Long fridgeId);
    public List<Fridges> getAllFridges();
}
