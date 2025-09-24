package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.services.FridgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridges")
public class FridgesController {
    private final FridgesService fridgesService;

    public FridgesController(FridgesService fridgesService) {
        this.fridgesService = fridgesService;
    }
    @GetMapping
    public List<Fridges> getFridges() {
        return fridgesService.findAll();
    }
    @GetMapping("/{id}")
    public Fridges getFridgesById(@PathVariable Long id){
        return fridgesService.findById(id);
    }
    @PostMapping
    public  Fridges addFridge(@RequestBody Fridges fridge){
        return fridgesService.saveFridge(fridge);
    }
    @DeleteMapping
    public void deleteFridgeById(@PathVariable Long id){
        fridgesService.deleteFridge(id);
    }
}
