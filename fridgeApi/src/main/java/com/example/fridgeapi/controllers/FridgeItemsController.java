package com.example.fridgeapi.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.services.FridgeItemsService;

@RestController
@RequestMapping("/fridge-items")
public class FridgeItemsController {
    FridgeItemsService FridgeItemsService;

    public FridgeItemsController(FridgeItemsService FridgeItemsService) {
        this.FridgeItemsService = FridgeItemsService;
    }

    @GetMapping("{fridgeItemId}")
    public FridgeItems getFridgeItemDetails(@PathVariable("fridgeItemId") Long fridgeItemId){
        return FridgeItemsService.getFridgeItem(fridgeItemId);
    }

    @GetMapping()
    public List<FridgeItems> getAllFridgeItemDetails(){             
        return FridgeItemsService.getAllFridgeItems();
    }

    @PostMapping
    public String createFridgeItemDetails(@RequestBody FridgeItems fridgeItems){
        return FridgeItemsService.createFridgeItem(fridgeItems);
    }

    @PutMapping
    public String updateFridgeItemDetails(@RequestBody FridgeItems fridgeItems, @RequestHeader("X-Token") String token){
        return FridgeItemsService.updateFridgeItem(fridgeItems, token);
    }

    @DeleteMapping("{fridgeItemId}")
    public String deleteFridgeItemDetails(@PathVariable("fridgeItemId") Long fridgeItemId , @RequestHeader("X-Token") String token){
        return FridgeItemsService.deleteFridgeItem(fridgeItemId, token);
    }
}
