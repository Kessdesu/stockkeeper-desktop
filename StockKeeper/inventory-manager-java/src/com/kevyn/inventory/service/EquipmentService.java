package com.kevyn.inventory.service;

import com.kevyn.inventory.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService {
    private final List<Equipment> equipments = new ArrayList<>();
    private int nextId = 1;

    public void addEquipment(String tag, String name, String category, String company, String location, String status) {
        Equipment equipment = new Equipment(
                nextId++,
                tag,
                name,
                category,
                company,
                location,
                status
        );

        equipments.add(equipment);
    }

    public List<Equipment> findAll() {
        return equipments;

    }

    public boolean deleteById(int id) {
    return equipments.removeIf(equipment -> equipment.getId() == id);
        }
      }
    