package com.example.fridgeapi.services;

import java.math.BigInteger;
import java.util.List;

import com.example.fridgeapi.models.Fridges;

public interface FridgesService {
    public Fridges getFridge(BigInteger fridgeID);
    public String createFridge(Fridges fridges);
    public String updateFridge(Fridges fridges);
    public String deleteFridge(BigInteger fridgeId);
    public List<Fridges> getAllFridges();
}
