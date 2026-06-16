package com.kevyn.inventory.service;

import com.kevyn.inventory.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final List<Item> items = new ArrayList<>();
    private int nextId = 1;

    public void addItem(
            String code,
            String name,
            String description,
            String category,
            String area,
            String location,
            int quantity,
            String unit,
            String status,
            String notes) {
        Item item = new Item(
                nextId++,
                code,
                name,
                description,
                category,
                area,
                location,
                quantity,
                unit,
                status,
                notes);

        items.add(item);
    }

    public List<Item> findAll() {
        return items;
    }

    public Item findById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    public boolean updateItem(Item updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);

            if (currentItem.getId() == updatedItem.getId()) {
                items.set(i, updatedItem);
                return true;
            }
        }

        return false;
    }

    public int getTotalItems() {
        return items.size();
    }

    public int countByStatus(String status) {
        int count = 0;

        for (Item item : items) {
            if (item.getStatus().equals(status)) {
                count++;
            }
        }

        return count;
    }

    public boolean deleteById(int id) {
        return items.removeIf(item -> item.getId() == id);
    }
}