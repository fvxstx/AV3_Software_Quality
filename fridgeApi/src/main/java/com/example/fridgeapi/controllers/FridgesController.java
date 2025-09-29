package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.services.FridgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fridges")
public class FridgesController {

    @Autowired
    private FridgesService fridgesService;

    public FridgesController(FridgesService fridgesService) {
        this.fridgesService = fridgesService;
    }

    @GetMapping("/{id}")
    public Fridges getFridge(@PathVariable Long id) {
        return fridgesService.getFridge(id);
    }

    @GetMapping
    public List<Fridges> getAllFridges() {
        return fridgesService.getAllFridges();
    }

    @PostMapping
    public String createFridge(@RequestBody Fridges fridge) {
        return fridgesService.createFridge(fridge);
    }

    @PutMapping
    public String updateFridge(@RequestBody Fridges fridge) {
        return fridgesService.updateFridge(fridge);
    }

    @DeleteMapping("/{id}")
    public String deleteFridge(@PathVariable Long id) {
        return fridgesService.deleteFridge(id);
    }
}
