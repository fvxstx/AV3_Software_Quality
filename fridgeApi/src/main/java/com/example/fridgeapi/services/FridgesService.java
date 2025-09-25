package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgesService {
    @Autowired
    private final FridgeRepository fridgeRepository;

    public FridgesService(FridgeRepository fridgeRepository) {
        this.fridgeRepository = fridgeRepository;
    }
    public List<Fridges> findAll(){
        return fridgeRepository.findAll();
    }
    public Fridges saveFridge(Fridges fridges){
        return fridgeRepository.save(fridges);
    }
    public Fridges findById(Long id){
        return fridgeRepository.findById(id).get();
    }
    public void deleteFridge(Long id){
        fridgeRepository.deleteById(id);
    }
}
