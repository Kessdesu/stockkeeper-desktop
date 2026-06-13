package com.kevyn.inventory.ui;

import com.kevyn.inventory.model.Equipment;
import com.kevyn.inventory.util.Status;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class EquipmentFormDialog extends JDialog {
    private JTextField tagField;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField companyField;
    private JTextField locationField;
    private JComboBox<Status> statusComboBox;

    private Consumer<Equipment> onSave;

    public EquipmentFormDialog(JFrame parent) {
        super(parent, "Novo Equipamento", true);

        setSize(420, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        tagField = new JTextField();
        nameField = new JTextField();
        categoryField = new JTextField();
        companyField = new JTextField();
        locationField = new JTextField();
        statusComboBox = new JComboBox<>(Status.values());

        formPanel.add(new JLabel("Tag:"));
        formPanel.add(tagField);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Empresa:"));
        formPanel.add(companyField);

        formPanel.add(new JLabel("Localização:"));
        formPanel.add(locationField);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusComboBox);

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(event -> saveEquipment());
        cancelButton.addActionListener(event -> dispose());
    }

    private void saveEquipment() {
        Equipment equipment = new Equipment(
                0,
                tagField.getText(),
                nameField.getText(),
                categoryField.getText(),
                companyField.getText(),
                locationField.getText(),
                statusComboBox.getSelectedItem().toString()
        );

        if (onSave != null) {
            onSave.accept(equipment);
        }

        dispose();
    }

    public void setOnSave(Consumer<Equipment> onSave) {
        this.onSave = onSave;
    }
}