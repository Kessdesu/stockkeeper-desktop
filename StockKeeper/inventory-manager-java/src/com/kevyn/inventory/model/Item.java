package com.kevyn.inventory.model;

public class Item {
    private int id;
    private String code;
    private String name;
    private String description;
    private String category;
    private String area;
    private String location;
    private int quantity;
    private String unit;
    private String status;
    private String notes;

    public Item(
            int id,
            String code,
            String name,
            String description,
            String category,
            String area,
            String location,
            int quantity,
            String unit,
            String status,
            String notes
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.category = category;
        this.area = area;
        this.location = location;
        this.quantity = quantity;
        this.unit = unit;
        this.status = status;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getLocation() {
        return location;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}