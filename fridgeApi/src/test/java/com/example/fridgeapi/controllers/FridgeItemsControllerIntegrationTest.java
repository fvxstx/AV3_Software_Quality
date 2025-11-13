package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.ItemType;
import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.models.Fridges;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FridgeItemsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long createdFridgeId;

    @BeforeEach
    void setup() throws Exception {
        // Cria uma geladeira antes de testar os items
        Fridges fridge = new Fridges();
        fridge.setOn(true);
        fridge.setTemperature("5");

        mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // Recupera o ID gerado
        String response = mockMvc.perform(get("/fridges"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Fridges> fridges = objectMapper.readValue(
                response,
                new com.fasterxml.jackson.core.type.TypeReference<List<Fridges>>() {}
        );
        createdFridgeId = fridges.get(0).getId();
    }

    private FridgeItems createTestItem(String name, int quantity) {
        FridgeItems item = new FridgeItems();
        item.setName(name);
        item.setQuantity(quantity);
        item.setItemType(ItemType.Drinks);
        item.setAvailableForChildren(true);
        item.setValidDate(LocalDateTime.now().plusDays(5));

        // referencia a geladeira existente
        Fridges fridge = new Fridges();
        fridge.setId(createdFridgeId);
        item.setFridge(fridge);
        return item;
    }

    private Long createItemAndReturnId() throws Exception {
        FridgeItems item = createTestItem("Leite Integral", 2);

        mockMvc.perform(post("/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        String response = mockMvc.perform(get("/fridge-items"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<FridgeItems> items = objectMapper.readValue(
                response,
                new com.fasterxml.jackson.core.type.TypeReference<List<FridgeItems>>() {}
        );
        return items.get(0).getId();
    }

    @Test
    void createAndGetAllItems_ShouldWork() throws Exception {
        FridgeItems item = createTestItem("Agua", 3);

        mockMvc.perform(post("/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        mockMvc.perform(get("/fridge-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Agua"))
                .andExpect(jsonPath("$[0].quantity").value(3))
                .andExpect(jsonPath("$[0].fridge.id").value(createdFridgeId));
    }

    @Test
    void updateItem_ShouldChangeQuantity() throws Exception {

        Long itemId = createItemAndReturnId();

        FridgeItems updatedItem = createTestItem("Leite Integral", 10);
        updatedItem.setId(itemId);

        mockMvc.perform(put("/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        mockMvc.perform(get("/fridge-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));

    }

    @Test
    void deleteItem_ShouldRemoveFromDatabase() throws Exception {

        Long itemId = createItemAndReturnId();

        mockMvc.perform(delete("/fridge-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        mockMvc.perform(get("/fridge-items/{id}", itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNonExistentItem_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/fridge-items/{id}", 999))
                .andExpect(status().isNotFound());
    }


}


