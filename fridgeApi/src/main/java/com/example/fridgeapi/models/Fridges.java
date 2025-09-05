package com.example.fridgeapi.models;

public class Fridges {
    private Long id;
    private boolean isOn;
    private String temperature;

    public Fridges(Long id, boolean isOn, String temperature) {
        this.id = id;
        this.isOn = isOn;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
