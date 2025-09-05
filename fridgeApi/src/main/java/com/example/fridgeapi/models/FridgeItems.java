package com.example.fridgeapi.models;

import java.time.LocalDateTime;

public class FridgeItems {
    private Long id;
    private String name;
    private LocalDateTime validDate;
    private Long fridgeId;
    private boolean isAvailableForChildren;
    private int quantity;
    private ItemType itemType;

    public FridgeItems(Long id, String name, LocalDateTime validDate, Long fridgeId, boolean isAvailableForChildren, int quantity, ItemType itemType) {
        this.setId(id);
        this.setName(name);
        this.setValidDate(validDate);
        this.setFridgeId(fridgeId);
        this.setAvailableForChildren(isAvailableForChildren);
        this.setQuantity(quantity);
        this.setItemType(itemType);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(Long fridgeId) {
        this.fridgeId = fridgeId;
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
}
