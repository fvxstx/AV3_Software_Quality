package com.example.fridgeapi.models;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "fridges")
public class Fridges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isOn;
    private String temperature;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "fridges", cascade = CascadeType.ALL)
    private List<FridgeItems> fridgeItems;
    
    public Fridges() {
    }

    public Fridges(boolean isOn, String temperature) {
        this.isOn = isOn;
        this.temperature = temperature;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}