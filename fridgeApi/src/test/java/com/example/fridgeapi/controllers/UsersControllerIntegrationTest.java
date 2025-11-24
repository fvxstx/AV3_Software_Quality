package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.UserType;
import com.example.fridgeapi.dtos.LoginDto;
import com.example.fridgeapi.models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
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
        user.setType(UserType.Parent);
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
            .andExpect(status().isOk());

        // 3. Assert
        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].email").value("fulltest@example.com"))
            .andExpect(jsonPath("$[0].password").value("securepass"));
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
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.token", not(emptyString())));
    }

    @Test
    void deleteUserDetails_ShouldRemoveUser() throws Exception {
        // 1. Arrange
        Users userToDelete = createTestUser("todelete@test.com", "deletepass");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToDelete)))
                .andExpect(status().isOk())
                .andReturn();


        Users created = objectMapper.readValue(result.getResponse().getContentAsString(), Users.class);

        // 2. Act
        mockMvc.perform(delete("/users/{userId}", created.getId()))
                .andExpect(status().isOk());
        // 3. Assert
        mockMvc.perform(get("/users/{userId}", created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserDetails_ShouldUpdateUser() throws Exception {
        Users originalUser = createTestUser("updateuser@test.com", "originalpass");

        MvcResult postResult = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(originalUser)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = postResult.getResponse().getContentAsString();
        Users createdUser = objectMapper.readValue(jsonResponse, Users.class);

        Long userIdToUpdate = createdUser.getId();
        Users updatedUserData = new Users();
        updatedUserData.setName("New Updated Name");
        updatedUserData.setEmail("updateuser@test.com");
        updatedUserData.setPassword("newSecurePass123");

        // 2. Act:
        mockMvc.perform(put("/users/{userId}", userIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserData)))
                // 3. Assert (Response)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userIdToUpdate))
                .andExpect(jsonPath("$.name").value("New Updated Name"))
                .andExpect(jsonPath("$.email").value("updateuser@test.com"));

        // 3. Assert (Verification)
        mockMvc.perform(get("/users/{userId}", userIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Updated Name"))
                .andExpect(jsonPath("$.email").value("updateuser@test.com"));
    }
}