package com.example.fridgeapi.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgesRepository;
import com.example.fridgeapi.services.FridgesService;

@Service
public class FridgesServiceimpl implements FridgesService {
    FridgesRepository fridgesRepository;

    public FridgesServiceimpl(FridgesRepository fridgesRepository) {
        this.fridgesRepository = fridgesRepository;
    }

    @Override
    public Fridges getFridge(Long fridgeID) {
        return fridgesRepository.findById(fridgeID).get();
    }

    @Override
    public String createFridge(Fridges fridges) {
        fridges.setCreatedAt(LocalDateTime.now());
        fridgesRepository.save(fridges);
        return "Success";
    }

    @Override
    public String updateFridge(Fridges fridges) {
        fridgesRepository.save(fridges);
        return "Success";
    }

    @Override
    public String deleteFridge(Long fridgeId) {
        fridgesRepository.deleteById(fridgeId);
        return "Success";
    }

    @Override
    public List<Fridges> getAllFridges() {
        return fridgesRepository.findAll();
    }
}
