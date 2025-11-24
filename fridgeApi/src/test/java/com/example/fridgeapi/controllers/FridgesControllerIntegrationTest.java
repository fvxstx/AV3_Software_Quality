package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.Fridges;
import com.example.fridgeapi.repositories.FridgesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FridgesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FridgesRepository fridgesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // limpa o banco antes de cada teste
        fridgesRepository.deleteAll();
    }

    @Test
    void shouldCreateANewFridgeAndReturnJson() throws Exception {
        Fridges fridge = new Fridges(true, "5°C");

        mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.temperature").value("5°C"));
    }

    @Test
    void shouldListAllFridges() throws Exception {
        // adiciona alguns dados no banco
        fridgesRepository.saveAll(List.of(
                new Fridges(true, "4°C"),
                new Fridges(false, "10°C")
        ));

        mockMvc.perform(get("/fridges"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnAFridgeById() throws Exception {
        Fridges fridge = fridgesRepository.save(new Fridges(true, "3°C"));

        mockMvc.perform(get("/fridges/" + fridge.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value("3°C"))
                .andExpect(jsonPath("$.on").value(true));
    }

    @Test
    void shouldUpdateAFridge() throws Exception {
        Fridges fridge = fridgesRepository.save(new Fridges(true, "5°C"));
        fridge.setTemperature("2°C");

        mockMvc.perform(put("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void shouldDeleteAFridge() throws Exception {
        Fridges fridge = fridgesRepository.save(new Fridges(true, "8°C"));

        mockMvc.perform(delete("/fridges/" + fridge.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }
}
