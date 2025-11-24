package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.ItemType;
import com.example.fridgeapi.models.UserType;
import com.example.fridgeapi.dtos.LoginResponse;
import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.models.Users;
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
    private String createdUserToken;

    @BeforeEach
    void setup() throws Exception {

        // Cria uma geladeira antes dos testes começarem
        Fridges fridge = new Fridges();
        fridge.setOn(true);
        fridge.setTemperature("5°C");

        String fridgeResponse = mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //pegando o id
        Fridges createdFridge = objectMapper.readValue(fridgeResponse, Fridges.class);
        createdFridgeId = createdFridge.getId();


        // Criar as caracteristicas do usuario antes de testar os items
        Users user = new Users();
        user.setEmail("fausto@teste.com");
        user.setName("Fausto");
        user.setPassword("password");
        user.setType(UserType.Parent);

        // Criando o usuario em si
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        // Logando o usuario
        String loginResponse = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Pegando o token
        LoginResponse login = objectMapper.readValue(loginResponse, LoginResponse.class);
        createdUserToken = login.token();


    }

    private FridgeItems createTestItem(String name, int quantity) {

        FridgeItems item = new FridgeItems();
        item.setName(name);
        item.setQuantity(quantity);
        item.setItemType(ItemType.Drinks);
        item.setAvailableForChildren(false);
        item.setValidDate(LocalDateTime.now().plusDays(5));

        Fridges fridge = new Fridges();
        fridge.setId(createdFridgeId);
        item.setFridge(fridge);

        return item;
    }

    private Long createItem() throws Exception {
        FridgeItems item = createTestItem("Energetico", 1);

        String response = mockMvc.perform(post("/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, FridgeItems.class).getId();
    }

    @Test
    void createAndGetAllItems_ShouldWork() throws Exception {
        FridgeItems item = createTestItem("Nescau", 3);

        mockMvc.perform(post("/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Nescau"));

        mockMvc.perform(get("/fridge-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Nescau"))
                .andExpect(jsonPath("$[0].quantity").value(3))
                .andExpect(jsonPath("$[0].fridge.id").value(createdFridgeId));
    }

    @Test
    void updateItem_ShouldChangeQuantityAndName() throws Exception {

        Long itemId = createItem();

        FridgeItems updatedItem = createTestItem("Energetico Com Whisky", 4);
        updatedItem.setId(itemId); // ID no corpo da requisição

        mockMvc.perform(put("/fridge-items")
                        .header("token", createdUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        mockMvc.perform(get("/fridge-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(4))
                .andExpect(jsonPath("$.name").value("Energetico Com Whisky"));

    }

    @Test
    void deleteItem_ShouldRemoveFromDatabase() throws Exception {

        Long itemId = createItem();

        mockMvc.perform(delete("/fridge-items/{id}", itemId)
                        .header("token", createdUserToken))
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


