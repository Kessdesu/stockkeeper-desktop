package com.kevyn.inventory.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("StockKeeper");
        setSize(820, 520);
        ImageIcon appIcon = new ImageIcon("assets/logo.png");
        setIconImage(appIcon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(17, 24, 39));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        ImageIcon logoIcon = new ImageIcon("assets/logo.png");

        Image logoImage = logoIcon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage), SwingConstants.CENTER);

        JLabel titleLabel = new JLabel("StockKeeper", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));

        JLabel subtitleLabel = new JLabel(
                "Controle inteligente de itens, produtos e ativos",
                SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(203, 213, 225));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JButton enterButton = new JButton("Entrar");
        enterButton.setFocusPainted(false);
        enterButton.setBackground(new Color(37, 99, 235));
        enterButton.setForeground(Color.WHITE);
        enterButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        enterButton.setBorder(BorderFactory.createEmptyBorder(12, 32, 12, 32));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 24));
        centerPanel.setOpaque(false);
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        textPanel.setOpaque(false);
        textPanel.add(logoLabel);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        JPanel featuresPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        featuresPanel.setOpaque(false);

        featuresPanel.add(createFeatureCard(
                "Cadastro multiárea",
                "Controle itens de diferentes setores, como indústria, TI, manutenção e almoxarifado."));

        featuresPanel.add(createFeatureCard(
                "Busca e filtros",
                "Localize rapidamente itens por código, nome, categoria, área ou status."));

        featuresPanel.add(createFeatureCard(
                "Controle de status",
                "Acompanhe itens disponíveis, em uso, reservados, em manutenção ou baixados."));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(enterButton);

        centerPanel.add(textPanel, BorderLayout.NORTH);
        centerPanel.add(featuresPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        enterButton.addActionListener(event -> openMainFrame());
    }

    private JPanel createFeatureCard(String title, String description) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(31, 41, 55));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        descriptionLabel.setForeground(new Color(203, 213, 225));
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descriptionLabel, BorderLayout.CENTER);

        return card;
    }

    private void openMainFrame() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        dispose();
    }
}