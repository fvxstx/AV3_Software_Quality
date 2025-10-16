package com.example.fridgeapi.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.repositories.FridgeItemsRepository;
import com.example.fridgeapi.services.FridgeItemsService;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FridgeItemsServiceimpl implements FridgeItemsService {
    FridgeItemsRepository fridgeItemsRepository;

    public FridgeItemsServiceimpl(FridgeItemsRepository fridgeItemsRepository) {
        this.fridgeItemsRepository = fridgeItemsRepository;
    }

    @Override
    public FridgeItems getFridgeItem(Long fridgeItemId) {
        return fridgeItemsRepository.findById(fridgeItemId).get();
    }

    @Override
    public String createFridgeItem(FridgeItems fridgeItems) {

        if (fridgeItems.getFridge() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, // Código HTTP 400
                    "A propriedade 'fridge' deve ser incluída na requisição para associar o item."
            );
        }

        if (fridgeItems.getFridge().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, // Código HTTP 400
                    "O ID da Geladeira (fridge.id) é obrigatório para criar um item."
            );
        }

        fridgeItems.setCreatedAt(LocalDateTime.now());
        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String updateFridgeItem(FridgeItems fridgeItems) {

        FridgeItems existingItem = fridgeItemsRepository.findById(fridgeItems.getId()).get();
        LocalDateTime originalCreationDate = existingItem.getCreatedAt();
        fridgeItems.setCreatedAt(originalCreationDate);

        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String deleteFridgeItem(Long fridgeItemId) {
        fridgeItemsRepository.deleteById(fridgeItemId);
        return "Success";
    }

    @Override
    public List<FridgeItems> getAllFridgeItems() {
        return fridgeItemsRepository.findAll();
    }
}