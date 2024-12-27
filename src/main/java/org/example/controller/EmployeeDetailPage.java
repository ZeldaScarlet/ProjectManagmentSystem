package org.example.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeDetailPage extends JPanel {
    private JTable projectTable;
    private DefaultTableModel tableModel;

    public EmployeeDetailPage(JPanel previousFrame, String employeeName, List<Object[]> employeeProjects) {
        setLayout(new BorderLayout());

        // Geri butonu ekle
        BackButton backButton = new BackButton(this, previousFrame);
        add(backButton, BorderLayout.NORTH);

        // Üstte çalışanın adını göster
        JLabel employeeNameLabel = new JLabel("Çalışan: " + employeeName, JLabel.CENTER);
        employeeNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        employeeNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(employeeNameLabel, BorderLayout.NORTH);

        // Proje tablosunu oluştur
        String[] columnNames = {"Proje Adı", "Başlangıç Tarihi", "Bitiş Tarihi", "Durum"};
        tableModel = new DefaultTableModel(columnNames, 0);

        projectTable = new JTable(tableModel);
        projectTable.setRowHeight(30);
        projectTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(projectTable);

        add(scrollPane, BorderLayout.CENTER);

        // Alt kısma toplam istatistikleri göster
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JLabel completedLabel = new JLabel("Tamamlanan Proje Sayısı: 0");
        JLabel incompleteLabel = new JLabel("Tamamlanmayan Proje Sayısı: 0");
        completedLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        incompleteLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        statsPanel.add(completedLabel);
        statsPanel.add(incompleteLabel);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statsPanel, BorderLayout.SOUTH);

        // Verileri tabloya yükle
        loadEmployeeProjects(employeeProjects, completedLabel, incompleteLabel);
    }

    private void loadEmployeeProjects(List<Object[]> employeeProjects, JLabel completedLabel, JLabel incompleteLabel) {
        int completedCount = 0;
        int incompleteCount = 0;

        // Tabloyu temizle
        tableModel.setRowCount(0);

        // Proje verilerini tabloya ekle
        for (Object[] project : employeeProjects) {
            tableModel.addRow(project);

            // Proje durumu istatistikleri hesapla
            String status = (String) project[3];
            if ("Tamamlandı".equalsIgnoreCase(status)) {
                completedCount++;
            } else {
                incompleteCount++;
            }
        }

        // İstatistik etiketlerini güncelle
        completedLabel.setText("Tamamlanan Proje Sayısı: " + completedCount);
        incompleteLabel.setText("Tamamlanmayan Proje Sayısı: " + incompleteCount);
    }
}

