package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.Fridges;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")

class FridgesControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Fridges createTestFridge(String temperature) {
        Fridges fridge = new Fridges();
        fridge.setOn(true);
        fridge.setTemperature(temperature);
        return fridge;
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createAndGetAllFridges_ShouldWork() throws Exception {

        // 1. ARRANGE
        Fridges fridge1 = createTestFridge("5");

        // 2. ACT (Ação - POST)
        mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridge1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // 3. Act (GET /ALL)
        mockMvc.perform(get("/fridges")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].temperature").value("5"));
    }
    @Test
    void updateFridgeDetails_ShouldModifyTemperature() throws Exception {

        // 1. Cria a geladeira inicial
        Fridges originalFridge = createTestFridge("5");
        mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(originalFridge)))
                .andExpect(status().isOk());

        // 2. Faz um GET /fridges para descobrir o ID gerado automaticamente
        String listResponse = mockMvc.perform(get("/fridges")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 3. Converte o JSON de resposta em uma lista de objetos Fridges
        List<Fridges> fridges = objectMapper.readValue(
                listResponse,
                new com.fasterxml.jackson.core.type.TypeReference<List<Fridges>>() {}
        );

        // 4. Pega o ID do primeiro registro da lista (único até agora)
        Long generatedId = fridges.get(0).getId();

        // 5. Cria o objeto atualizado com o mesmo ID
        Fridges updatedFridge = createTestFridge("2");
        updatedFridge.setId(generatedId);

        // 6. PUT /fridges com o novo valor de temperatura
        mockMvc.perform(put("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFridge)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // 7. GET /fridges/{id} para confirmar que foi atualizado
        mockMvc.perform(get("/fridges/{id}", generatedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value("2"));
    }
    @Test
    void deleteFridgeDetails_ShouldRemoveFridge() throws Exception {
        // 1. ARRANGE (Criação Inicial)
        Fridges fridgeToDelete = createTestFridge("15");
        mockMvc.perform(post("/fridges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fridgeToDelete)))
                .andExpect(status().isOk());

        String listResponse = mockMvc.perform(get("/fridges")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 3. Converte o JSON de resposta em uma lista de objetos Fridges
        List<Fridges> fridges = objectMapper.readValue(
                listResponse,
                new com.fasterxml.jackson.core.type.TypeReference<List<Fridges>>() {}
        );

        // 4. Pega o ID do primeiro registro da lista (único até agora)
        Long generatedId = fridges.get(0).getId();

        // 2. ACT (Ação - DELETE)
        mockMvc.perform(delete("/fridges/{fridgeId}", generatedId))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // 3. ASSERT (Verificação de Remoção)
        // Tenta fazer um GET no mesmo ID
        mockMvc.perform(get("/fridges/{fridgeId}", generatedId))
                // Espera que o servidor retorne 404 NOT FOUND, confirmando a exclusão
                .andExpect(status().isNotFound());
    }

    @Test
    void getFridgeDetails_ShouldReturnNotFound() throws Exception {
        // Testa se tentar buscar um ID que nunca foi criado retorna 404.
        final Long NON_EXISTENT_ID = 99L;
        mockMvc.perform(get("/fridges/{fridgeId}", NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }
}
