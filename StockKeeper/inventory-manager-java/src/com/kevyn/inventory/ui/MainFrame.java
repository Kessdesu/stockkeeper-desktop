package com.kevyn.inventory.ui;

import com.kevyn.inventory.util.Category;
import com.kevyn.inventory.util.Status;

import com.kevyn.inventory.model.Item;
import com.kevyn.inventory.service.ItemService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private final ItemService itemService;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private JTextField searchField;

    private JComboBox<String> categoryFilterComboBox;
    private JComboBox<String> statusFilterComboBox;

    public MainFrame() {
        itemService = new ItemService();

        setTitle("StockKeeper Desktop");
        setSize(1050, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(17, 24, 39));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("Controle de Estoque");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton newButton = new JButton("+ Novo Item");
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

        JButton editButton = new JButton("Editar");
        editButton.setFocusPainted(false);
        editButton.setBackground(new Color(22, 163, 74));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JButton detailsButton = new JButton("Detalhes");
        detailsButton.setFocusPainted(false);
        detailsButton.setBackground(new Color(79, 70, 229));
        detailsButton.setForeground(Color.WHITE);
        detailsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailsButton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(detailsButton);
        buttonPanel.add(editButton);
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

        JLabel categoryLabel = new JLabel("Categoria:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        categoryFilterComboBox = new JComboBox<>();
        categoryFilterComboBox.addItem("TODAS");

        for (Category category : Category.values()) {
            categoryFilterComboBox.addItem(category.toString());
        }

        statusFilterComboBox = new JComboBox<>();
        statusFilterComboBox.addItem("TODOS");

        for (Status status : Status.values()) {
            statusFilterComboBox.addItem(status.toString());
        }

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setOpaque(false);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(categoryLabel);
        searchPanel.add(categoryFilterComboBox);
        searchPanel.add(statusLabel);
        searchPanel.add(statusFilterComboBox);

        String[] columns = {
                "ID", "Código", "Nome", "Categoria", "Área", "Localização", "Qtd", "Unidade", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

        newButton.addActionListener(event -> openItemForm());
        editButton.addActionListener(event -> editSelectedItem());
        deleteButton.addActionListener(event -> deleteSelectedItem());
        searchField.addActionListener(event -> filterTable());
        detailsButton.addActionListener(event -> showSelectedItemDetails());
        categoryFilterComboBox.addActionListener(event -> filterTable());
        statusFilterComboBox.addActionListener(event -> filterTable());
    }

    private void openItemForm() {
        ItemFormDialog dialog = new ItemFormDialog(this);

        dialog.setOnSave(item -> {
            itemService.addItem(
                    item.getCode(),
                    item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getArea(),
                    item.getLocation(),
                    item.getQuantity(),
                    item.getUnit(),
                    item.getStatus(),
                    item.getNotes());

            filterTable();
        });

        dialog.setVisible(true);
    }

    private void deleteSelectedItem() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item para excluir.",
                    "Nenhum item selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este item?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);

        boolean deleted = itemService.deleteById(itemId);

        if (deleted) {
            filterTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Item excluído com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Item item : itemService.findAll()) {
            tableModel.addRow(new Object[] {
                    item.getId(),
                    item.getCode(),
                    item.getName(),
                    item.getCategory(),
                    item.getArea(),
                    item.getLocation(),
                    item.getQuantity(),
                    item.getUnit(),
                    item.getStatus()
            });
        }
    }

    private void editSelectedItem() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item para editar.",
                    "Nenhum item selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        Item item = itemService.findById(itemId);

        if (item == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Item não encontrado.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ItemFormDialog dialog = new ItemFormDialog(this, item);

        dialog.setOnSave(updatedItem -> {
            itemService.updateItem(updatedItem);
            filterTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Item atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        dialog.setVisible(true);
    }

    private void showSelectedItemDetails() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item para visualizar os detalhes.",
                    "Nenhum item selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        Item item = itemService.findById(itemId);

        if (item == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Item não encontrado.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String details = """
                ID: %d
                Código: %s
                Nome: %s
                Descrição: %s
                Categoria: %s
                Área: %s
                Localização: %s
                Quantidade: %d
                Unidade: %s
                Status: %s
                Observações: %s
                """.formatted(
                item.getId(),
                item.getCode(),
                item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getArea(),
                item.getLocation(),
                item.getQuantity(),
                item.getUnit(),
                item.getStatus(),
                item.getNotes());

        JOptionPane.showMessageDialog(
                this,
                details,
                "Detalhes do Item",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase().trim();
        String selectedCategory = categoryFilterComboBox.getSelectedItem().toString();
        String selectedStatus = statusFilterComboBox.getSelectedItem().toString();

        tableModel.setRowCount(0);

        for (Item item : itemService.findAll()) {
            boolean matchesSearch = item.getCode().toLowerCase().contains(searchText) ||
                    item.getName().toLowerCase().contains(searchText) ||
                    item.getDescription().toLowerCase().contains(searchText) ||
                    item.getCategory().toLowerCase().contains(searchText) ||
                    item.getArea().toLowerCase().contains(searchText) ||
                    item.getLocation().toLowerCase().contains(searchText) ||
                    item.getUnit().toLowerCase().contains(searchText) ||
                    item.getStatus().toLowerCase().contains(searchText);

            boolean matchesCategory = selectedCategory.equals("TODAS") ||
                    item.getCategory().equals(selectedCategory);

            boolean matchesStatus = selectedStatus.equals("TODOS") ||
                    item.getStatus().equals(selectedStatus);

            if (matchesSearch && matchesCategory && matchesStatus) {
                tableModel.addRow(new Object[] {
                        item.getId(),
                        item.getCode(),
                        item.getName(),
                        item.getCategory(),
                        item.getArea(),
                        item.getLocation(),
                        item.getQuantity(),
                        item.getUnit(),
                        item.getStatus()
                });
            }
        }
    }
}