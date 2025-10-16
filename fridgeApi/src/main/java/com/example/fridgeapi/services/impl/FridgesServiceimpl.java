package com.example.fridgeapi.services.impl;
import java.time.LocalDateTime;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import com.example.fridgeapi.models.Users;
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
    public String createFridge(Fridges fridge) {
        fridge.setCreatedAt(LocalDateTime.now());
        fridgesRepository.save(fridge);
        return "Success";
    }

    @Override
    public String updateFridge(Fridges fridges) {

        Fridges existingFridge = fridgesRepository.findById(fridges.getId()).get();
        LocalDateTime originalCreationDate = existingFridge.getCreatedAt();
        fridges.setCreatedAt(originalCreationDate);

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
