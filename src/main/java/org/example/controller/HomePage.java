package org.example.controller;

import javax.swing.*;
import java.awt.*;

class HomePage extends JPanel {
    HomePage(ProjectManagementApp app) {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));

        JLabel title = new JLabel("Proje Yönetim Sistemi", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(54, 69, 79));
        this.add(title);

        JButton projectsButton = new JButton("Projeler Listesi");
        projectsButton.addActionListener(e -> app.showPage("ProjectListPage"));
        styleButton(projectsButton, new Color(75, 160, 230));
        this.add(projectsButton);


        JButton employeesButton = new JButton("Çalışanlar Listesi");
        employeesButton.addActionListener(e -> app.showPage("EmployeeListPage"));
        styleButton(employeesButton, new Color(102, 187, 106));
        this.add(employeesButton);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(projectsButton);
        buttonPanel.add(employeesButton);

        this.add(title, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
