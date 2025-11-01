package com.example.fridgeapi.models;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_fridges")
public class Fridges implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOn;
    private String temperature;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<FridgeItems> fridgeItems;

    public Fridges(boolean isOn, String temperature) {
        this.isOn = isOn;
        this.temperature = temperature;
        this.createdAt = LocalDateTime.now();
    }

    public Fridges() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
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