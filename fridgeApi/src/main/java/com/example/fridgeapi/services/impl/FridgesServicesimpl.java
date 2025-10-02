package com.example.fridgeapi.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgesRepository;
import com.example.fridgeapi.services.FridgesService;

@Service
public class FridgesServicesimpl implements FridgesService {
    FridgesRepository fridgesRepository;

    public FridgesServicesimpl(FridgesRepository fridgesRepository) {
        this.fridgesRepository = fridgesRepository;
    }

    @Override
    public Fridges getFridge(BigInteger fridgeID) {
        return fridgesRepository.findById(fridgeID).get();
    }

    @Override
    public String createFridge(Fridges fridges) {
        fridges.setCreatedAt(LocalDateTime.now());
        fridgesRepository.save(fridges);
        return "Success";
    }

    /*@Override
    public String updateFridge(BigInteger fridgeId, Fridges fridges) {
        fridgesRepository.save(fridges);
        return "Success";
    }*/

    @Override
    public String updateFridge(BigInteger fridgeId, Fridges fridges) {
        Fridges existing = fridgesRepository.findById(fridgeId).orElse(null);
        if (existing == null) {
            return "Fridge not found";
        }
        existing.setOn(fridges.isOn());
        existing.setTemperature(fridges.getTemperature());
        
        if (fridges.getRoomLocation() != null) {
        existing.setRoomLocation(fridges.getRoomLocation());
    }
        existing.setUpdatedAt(LocalDateTime.now());
        fridgesRepository.save(existing);
        return "Success";
    }

    @Override
    public String deleteFridge(BigInteger fridgeId) {
        fridgesRepository.deleteById(fridgeId);
        return "Success";
    }

    @Override
    public List<Fridges> getAllFridges() {
        return fridgesRepository.findAll();
    }
}
