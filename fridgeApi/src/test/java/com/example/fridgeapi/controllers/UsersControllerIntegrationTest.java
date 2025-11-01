package com.example.fridgeapi.controllers;

import com.example.fridgeapi.dtos.LoginDto;
import com.example.fridgeapi.models.Users;
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

import java.math.BigInteger;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class UsersControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Users createTestUser(String email, String password) {
        Users user = new Users();
        user.setEmail(email);
        user.setName(email.split("@")[0]);
        user.setPassword(password);
        return user;
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createUserDetailsAndGetUser_ShouldWorkTogether() throws Exception {
        // 1. Arrange 
        Users newUser = createTestUser("fulltest@example.com", "securepass");

        // 2. Act (POST)
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // 3. Act (GET)
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("fulltest@example.com"));
    }

    @Test
    void loginUser_ShouldReturnToken_OnSuccessfulPersistenceAndLogin() throws Exception {
        // 1. Arrange
        Users userToLogin = createTestUser("loginuser@test.com", "loginpass");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToLogin)))
                .andExpect(status().isOk());

        // 2. Act
        LoginDto loginDto = new LoginDto("loginuser@test.com", "loginpass", null);

        // 3. Assert
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("token valid 123123123124123"));
    }

    @Test
    void deleteUserDetails_ShouldRemoveUser() throws Exception {
        // 1. Arrange
        Users userToDelete = createTestUser("todelete@test.com", "deletepass");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToDelete)))
                .andExpect(status().isOk());

        BigInteger userIdToDelete = BigInteger.ONE;

        // 2. Act
        mockMvc.perform(delete("/users/{userId}", userIdToDelete))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        // 3. Assert
        mockMvc.perform(get("/users/{userId}", userIdToDelete))
                .andExpect(status().isNotFound());
    }
}