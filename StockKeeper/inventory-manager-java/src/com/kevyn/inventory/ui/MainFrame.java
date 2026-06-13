package com.kevyn.inventory.ui;

import com.kevyn.inventory.model.Equipment;
import com.kevyn.inventory.service.EquipmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private final EquipmentService equipmentService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private JTextField searchField;

    private void deleteSelectedEquipment() {
    int selectedRow = table.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Selecione um equipamento para excluir.",
                "Nenhum equipamento selecionado",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir este equipamento?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    int equipmentId = (int) tableModel.getValueAt(selectedRow, 0);

    boolean deleted = equipmentService.deleteById(equipmentId);

    if (deleted) {
        filterTable();

        JOptionPane.showMessageDialog(
                this,
                "Equipamento excluído com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

    public MainFrame() {
        equipmentService = new EquipmentService();

        setTitle("StockKeeper Desktop");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(17, 24, 39));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("Controle de Estoque");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton newButton = new JButton("+ Novo Equipamento");
        newButton.setFocusPainted(false);
        newButton.setBackground(new Color(37, 99, 235));
        newButton.setForeground(Color.WHITE);
        newButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newButton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JButton deleteButton = new JButton("Excluir");
        deleteButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(220, 38, 38));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);
        buttonPanel.add(newButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(260, 34));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setOpaque(false);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        String[] columns = {
                "ID", "Tag", "Nome", "Categoria", "Empresa", "Localização", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(37, 99, 235));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(55, 65, 81));
        table.setBackground(new Color(31, 41, 55));
        table.setForeground(Color.WHITE);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(15, 23, 42));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(31, 41, 55));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        newButton.addActionListener(event -> openEquipmentForm());
        deleteButton.addActionListener(event -> deleteSelectedEquipment());
        searchField.addActionListener(event -> filterTable());
        
    }

private void openEquipmentForm() {
    EquipmentFormDialog dialog = new EquipmentFormDialog(this);

    dialog.setOnSave(equipment -> {
        equipmentService.addEquipment(
                equipment.getTag(),
                equipment.getName(),
                equipment.getCategory(),
                equipment.getCompany(),
                equipment.getLocation(),
                equipment.getStatus()
        );

        filterTable();
    });

    dialog.setVisible(true);
}

private void refreshTable() {
    tableModel.setRowCount(0);

    for (Equipment equipment : equipmentService.findAll()) {
        tableModel.addRow(new Object[]{
                equipment.getId(),
                equipment.getTag(),
                equipment.getName(),
                equipment.getCategory(),
                equipment.getCompany(),
                equipment.getLocation(),
                equipment.getStatus()
        });
    }
}

private void filterTable() {
    String searchText = searchField.getText().toLowerCase().trim();

    tableModel.setRowCount(0);

    for (Equipment equipment : equipmentService.findAll()) {
        boolean matchesSearch =
                equipment.getTag().toLowerCase().contains(searchText) ||
                equipment.getName().toLowerCase().contains(searchText) ||
                equipment.getCategory().toLowerCase().contains(searchText) ||
                equipment.getCompany().toLowerCase().contains(searchText) ||
                equipment.getLocation().toLowerCase().contains(searchText) ||
                equipment.getStatus().toLowerCase().contains(searchText);

        if (matchesSearch) {
            tableModel.addRow(new Object[]{
                    equipment.getId(),
                    equipment.getTag(),
                    equipment.getName(),
                    equipment.getCategory(),
                    equipment.getCompany(),
                    equipment.getLocation(),
                    equipment.getStatus()
            });
        }
    }
}}