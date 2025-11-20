package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.FridgeItemsLog;
import com.example.fridgeapi.repositories.FridgeItemsLogRepository;
import com.example.fridgeapi.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.repositories.FridgeItemsRepository;
import com.example.fridgeapi.services.FridgeItemsService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FridgeItemsServiceimpl implements FridgeItemsService {
    FridgeItemsRepository fridgeItemsRepository;
    UsersRepository usersRepository;
    FridgeItemsLogRepository fridgeItemsLogRepository;

    public FridgeItemsServiceimpl(FridgeItemsRepository fridgeItemsRepository,  UsersRepository usersRepository,
                                  FridgeItemsLogRepository fridgeItemsLogRepository) {
        this.fridgeItemsRepository = fridgeItemsRepository;
        this.usersRepository = usersRepository;
        this.fridgeItemsLogRepository = fridgeItemsLogRepository;
    }

    @Override
    public FridgeItems getFridgeItem(Long fridgeItemId) {
        return fridgeItemsRepository.findById(fridgeItemId).get();
    }

    @Override
    public String createFridgeItem(FridgeItems fridgeItems) {

        if (fridgeItems.getFridge() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }
        
        fridgeItems.setCreatedAt(LocalDateTime.now());
        fridgeItemsRepository.save(fridgeItems);
        return "Success";    }

    @Override
    public String updateFridgeItem(FridgeItems fridgeItems, String token) {

        var user =  usersRepository.findByToken(token);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        FridgeItems existingItem = fridgeItemsRepository.findById(fridgeItems.getId()).get();
        LocalDateTime originalCreationDate = existingItem.getCreatedAt();
        fridgeItems.setCreatedAt(originalCreationDate);

        FridgeItemsLog log = new FridgeItemsLog(existingItem, user.get(), "Item atualizado");
        fridgeItemsLogRepository.save(log);

        fridgeItemsRepository.save(fridgeItems);
        return "Success";
    }

    @Override
    public String deleteFridgeItem(Long fridgeItemId, String token) {
        var user =  usersRepository.findByToken(token);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        FridgeItems existingItem = fridgeItemsRepository.findById(fridgeItemId).get();
        FridgeItemsLog log = new FridgeItemsLog(existingItem, user.get(), "Item removido");
        fridgeItemsLogRepository.save(log);

        if(existingItem.getQuantity() > 1){
            existingItem.setQuantity(existingItem.getQuantity() - 1);
            fridgeItemsRepository.save(existingItem);
            return "Success";
        }

        fridgeItemsRepository.deleteById(fridgeItemId);
        return "Success";
    }

    @Override
    public List<FridgeItems> getAllFridgeItems() {
        return fridgeItemsRepository.findAll();
    }
}