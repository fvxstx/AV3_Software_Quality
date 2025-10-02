package com.example.fridgeapi.controllers;

import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.services.FridgesService;

@RestController
@RequestMapping("/fridges")
public class FridgesController {
    FridgesService fridgesService;

    public FridgesController(FridgesService fridgesService) {
        this.fridgesService = fridgesService;
    }

    @GetMapping("{fridgeId}")
    public Fridges getFridgeDetails(@PathVariable("fridgeId") BigInteger fridgeId){
        return fridgesService.getFridge(fridgeId);
    }

    @GetMapping()
    public List<Fridges> getAllFridgeDetails(){
        return fridgesService.getAllFridges();
    }
    
    @PostMapping
    public String createFridgeDetails(@RequestBody Fridges fridges){
        return fridgesService.createFridge(fridges);
    }

    @PutMapping("{fridgeId}")
    public String updateFridgeDetails(@PathVariable("fridgeId") BigInteger fridgeId, @RequestBody Fridges fridges) {
        return fridgesService.updateFridge(fridgeId, fridges);
        //(@RequestBody Fridges fridges)
    }
    
    @DeleteMapping("{fridgeId}")
    public String deleteFridgeDetails(@PathVariable("fridgeId") BigInteger fridgeId ){
        return fridgesService.deleteFridge(fridgeId);
    }
}