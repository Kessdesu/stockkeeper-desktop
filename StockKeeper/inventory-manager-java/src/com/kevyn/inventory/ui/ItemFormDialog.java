package com.kevyn.inventory.ui;

import com.kevyn.inventory.model.Item;
import com.kevyn.inventory.util.Area;
import com.kevyn.inventory.util.Category;
import com.kevyn.inventory.util.Status;
import com.kevyn.inventory.util.Unit;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ItemFormDialog extends JDialog {
    private JTextField codeField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JComboBox<Category> categoryComboBox;
    private JComboBox<Area> areaComboBox;
    private JTextField locationField;
    private JTextField quantityField;
    private JComboBox<Unit> unitComboBox;
    private JComboBox<Status> statusComboBox;
    private JTextField notesField;

    private Item itemToEdit;
    private Consumer<Item> onSave;

    public ItemFormDialog(JFrame parent, Item itemToEdit) {
        this(parent);

        this.itemToEdit = itemToEdit;
        setTitle("Editar Item");

        codeField.setText(itemToEdit.getCode());
        nameField.setText(itemToEdit.getName());
        descriptionField.setText(itemToEdit.getDescription());
        categoryComboBox
                .setSelectedItem(Enum.valueOf(com.kevyn.inventory.util.Category.class, itemToEdit.getCategory()));
        areaComboBox.setSelectedItem(Enum.valueOf(com.kevyn.inventory.util.Area.class, itemToEdit.getArea()));
        locationField.setText(itemToEdit.getLocation());
        quantityField.setText(String.valueOf(itemToEdit.getQuantity()));
        unitComboBox.setSelectedItem(Enum.valueOf(com.kevyn.inventory.util.Unit.class, itemToEdit.getUnit()));
        statusComboBox.setSelectedItem(Enum.valueOf(com.kevyn.inventory.util.Status.class, itemToEdit.getStatus()));
        notesField.setText(itemToEdit.getNotes());
    }

    public ItemFormDialog(JFrame parent) {
        super(parent, "Novo Item", true);

        setSize(520, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        codeField = new JTextField();
        nameField = new JTextField();
        descriptionField = new JTextField();
        categoryComboBox = new JComboBox<>(Category.values());
        areaComboBox = new JComboBox<>(Area.values());
        locationField = new JTextField();
        quantityField = new JTextField();
        unitComboBox = new JComboBox<>(Unit.values());
        statusComboBox = new JComboBox<>(Status.values());
        notesField = new JTextField();

        formPanel.add(new JLabel("Código:"));
        formPanel.add(codeField);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoryComboBox);

        formPanel.add(new JLabel("Área:"));
        formPanel.add(areaComboBox);

        formPanel.add(new JLabel("Localização:"));
        formPanel.add(locationField);

        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Unidade:"));
        formPanel.add(unitComboBox);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusComboBox);

        formPanel.add(new JLabel("Observações:"));
        formPanel.add(notesField);

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(event -> saveItem());
        cancelButton.addActionListener(event -> dispose());
    }

    private void saveItem() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();

        if (code.isEmpty() || name.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos obrigatórios: Código, Nome e Localização.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "A quantidade precisa ser um número inteiro.",
                    "Erro de validação",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int itemId = itemToEdit == null ? 0 : itemToEdit.getId();

        Item item = new Item(
                itemId,
                code,
                name,
                descriptionField.getText(),
                categoryComboBox.getSelectedItem().toString(),
                areaComboBox.getSelectedItem().toString(),
                location,
                quantity,
                unitComboBox.getSelectedItem().toString(),
                statusComboBox.getSelectedItem().toString(),
                notesField.getText());

        if (onSave != null) {
            onSave.accept(item);
        }

        dispose();
    }

    public void setOnSave(Consumer<Item> onSave) {
        this.onSave = onSave;
    }
}