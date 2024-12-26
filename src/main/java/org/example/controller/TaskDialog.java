package org.example.controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TaskDialog {
    private JTable parentTable;

    public TaskDialog(JTable parentTable) {
        this.parentTable = parentTable;
    }

    public void showTaskDialog(int projectId) {
        JTextField taskNameField = new JTextField(20);
        JTextField startDateField = new JTextField(20);
        JTextField endDateField = new JTextField(20);
        JTextField manDayField = new JTextField(20);

        // Çalışan seçimi için JComboBox
        JComboBox<String> employeeComboBox = new JComboBox<>();
        loadEmployeesIntoComboBox(employeeComboBox);

        // Giriş alanlarını düzenleyen bir panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Görev Adı:"));
        inputPanel.add(taskNameField);
        inputPanel.add(new JLabel("Başlangıç Tarihi (YYYY-MM-DD):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("Bitiş Tarihi (YYYY-MM-DD):"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel("Adam-Gün Sayısı:"));
        inputPanel.add(manDayField);
        inputPanel.add(new JLabel("Çalışan Seçin:"));
        inputPanel.add(employeeComboBox);

        int result = JOptionPane.showConfirmDialog(parentTable, inputPanel, "Yeni Görev Ekle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String taskName = taskNameField.getText();
                LocalDate startDate = LocalDate.parse(startDateField.getText());
                LocalDate endDate = LocalDate.parse(endDateField.getText());
                int manDay = Integer.parseInt(manDayField.getText());
                String selectedEmployee = (String) employeeComboBox.getSelectedItem();

                // Görevi veritabanına kaydet
                saveTaskToDatabase(projectId, taskName, startDate, endDate, manDay, selectedEmployee);

                JOptionPane.showMessageDialog(parentTable, "Görev başarıyla eklendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentTable, "Geçerli bilgiler giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Çalışanları JComboBox'a yükler
    private void loadEmployeesIntoComboBox(JComboBox<String> comboBox) {
        List<String> employees = getEmployeesFromDatabase();
        for (String employee : employees) {
            comboBox.addItem(employee);
        }
    }

    // Veritabanından çalışanları alır (örnek bir metot)
    private List<String> getEmployeesFromDatabase() {
        // Burada çalışanları veritabanından çekmeniz gerekecek.
        // Örnek olarak sabit değerler dönüyoruz.
        return List.of("Ali", "Ayşe", "Mehmet", "Fatma");
    }

    // Görevi veritabanına kaydeder (örnek bir metot)
    private void saveTaskToDatabase(int projectId, String taskName, LocalDate startDate, LocalDate endDate, int manDay, String employee) {
        // Burada görevi veritabanına kaydetmek için ilgili DAO/Servis çağrılmalı.
        System.out.println("Görev Kaydedildi: " + taskName + " - " + employee);
    }
}

