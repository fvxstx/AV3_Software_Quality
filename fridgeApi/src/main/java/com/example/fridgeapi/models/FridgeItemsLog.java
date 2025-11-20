package com.example.fridgeapi.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fridge_items_log")
public class FridgeItemsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;

    private Long fridgeItemId;
    private String fridgeItemName;
    private String userName;
    private Long userId;
    private String description;

    public FridgeItemsLog() {}

    public FridgeItemsLog(FridgeItems fridgeItem, Users user, String description){
        this.createdAt = LocalDateTime.now();
        this.fridgeItemId = fridgeItem.getId();
        this.fridgeItemName = fridgeItem.getName();
        this.userName = user.getName();
        this.userId = user.getId();
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFridgeItemId() {
        return fridgeItemId;
    }

    public void setFridgeItemId(Long fridgeItemId) {
        this.fridgeItemId = fridgeItemId;
    }

    public String getFridgeItemName() {
        return fridgeItemName;
    }

    public void setFridgeItemName(String fridgeItemName) {
        this.fridgeItemName = fridgeItemName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
