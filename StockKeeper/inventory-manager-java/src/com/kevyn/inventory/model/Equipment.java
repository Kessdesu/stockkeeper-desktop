package com.kevyn.inventory.model;

public class Equipment {
    private int id;
    private String tag;
    private String name;
    private String category;
    private String company;
    private String location;
    private String status;

    public Equipment(int id, String tag, String name, String category, String company, String location, String status) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.category = category;
        this.company = company;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}