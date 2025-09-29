package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgeRepository;
import com.example.fridgeapi.services.FridgesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgesServiceImpl implements FridgesService {

    FridgeRepository fridgeRepository;

    public FridgesServiceImpl(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }

    @Override
    public String createFridge(Fridges fridge) {
        if (fridge.getItems() != null) {
            for (FridgeItems item : fridge.getItems()) {
                item.setFridge(fridge);
            }
        }
        fridgeRepository.save(fridge);
        return "Success";
    }

    @Override
    public String updateFridge(Fridges fridge) {
        if (fridge.getItems() != null) {
            for (FridgeItems item : fridge.getItems()) {
                item.setFridge(fridge);
            }
        }
        fridgeRepository.save(fridge);
        return "Success";
    }

    @Override
    public String deleteFridge(Long id) {
        if (!fridgeRepository.existsById(id)) {
            return "Fridge not found with id " + id;
        }
        fridgeRepository.deleteById(id);
        return "Success";
    }

    @Override
    public Fridges getFridge(Long id) {
        return fridgeRepository.findById(id).get();
    }

    @Override
    public List<Fridges> getAllFridges() {
        return fridgeRepository.findAll();
    }
}
