package com.example.fridgeapi.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_fridge_items")
public class FridgeItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime validDate;
    private boolean isAvailableForChildren;
    private int quantity;
    private ItemType itemType;
    
    @ManyToOne
    @JoinColumn(name = "fridge_Id")
    private Fridges fridge;

    public FridgeItems(){
        
    }

    public FridgeItems(Long id, String name, LocalDateTime validDate, Long fridgeId, boolean isAvailableForChildren, int quantity, ItemType itemType) {
        this.setName(name);
        this.setValidDate(validDate);
        this.setAvailableForChildren(isAvailableForChildren);
        this.setQuantity(quantity);
        this.setItemType(itemType);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDateTime validDate) {
        this.validDate = validDate;
    }

    public boolean isAvailableForChildren() {
        return isAvailableForChildren;
    }

    public void setAvailableForChildren(boolean availableForChildren) {
        isAvailableForChildren = availableForChildren;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Fridges getFridge() {
        return fridge;
    }

    public void setFridge(Fridges fridge) {
        this.fridge = fridge;
    }
}
