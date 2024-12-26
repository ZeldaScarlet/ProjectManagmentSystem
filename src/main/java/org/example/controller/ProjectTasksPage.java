// ProjectTasksPage.java: Görevler sayfasını temsil eden sınıf
package org.example.controller;

import org.example.dao.GorevDAO;
import org.example.model.Gorev;
import org.example.service.GorevServisi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ProjectTasksPage extends JPanel {
    private JTable tasksTable;
    private DefaultTableModel tableModel;
    private int projectId;
    private Runnable onBack;

    public ProjectTasksPage(int projectId) {
        this.projectId = projectId;


        setLayout(new BorderLayout());

        // Görevleri listelemek için tablo oluştur
        String[] columnNames = {"Görev Adı", "Başlangıç Tarihi", "Bitiş Tarihi", "Adam Gün Sayısı"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tasksTable = new JTable(tableModel);
        tasksTable.setRowHeight(30);
        tasksTable.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tasksTable);
        add(scrollPane, BorderLayout.CENTER);

        // Görevleri yükle
        loadTasks();

        // Alt panel (Görev Ekle ve Geri butonları)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton addTaskButton = new JButton("Görev Ekle");
        addTaskButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddTaskDialog();
            }
        });

        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBack.run();
            }.
        });

        bottomPanel.add(addTaskButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadTasks() {
        tableModel.setRowCount(0); // Mevcut tabloyu temizle

        GorevServisi gorevServisi = new GorevServisi();
        List<Gorev> tasks = gorevServisi.getTasksByProjectId(projectId);
        for (Gorev task : tasks) {
            Object[] rowData = {
                    task.getGorevAdi(),
                    task.getBaslamaTarihi(),
                    task.getBitisTarihi(),
                    task.getAdamGunSayisi()
            };
            tableModel.addRow(rowData);
        }
    }

    private void openAddTaskDialog() {
        // Görev ekleme formu
        JTextField taskNameField = new JTextField(20);
        JTextField startDateField = new JTextField(20);
        JTextField endDateField = new JTextField(20);
        JTextField manDayField = new JTextField(20);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Görev Adı:"));
        inputPanel.add(taskNameField);
        inputPanel.add(new JLabel("Başlangıç Tarihi (YYYY-MM-DD):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("Bitiş Tarihi (YYYY-MM-DD):"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel("Adam Gün Sayısı:"));
        inputPanel.add(manDayField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Yeni Görev Ekle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String taskName = taskNameField.getText();
                LocalDate startDate = LocalDate.parse(startDateField.getText());
                LocalDate endDate = LocalDate.parse(endDateField.getText());
                int manDay = Integer.parseInt(manDayField.getText());
                int calisanId = 0;
                int ertelemeMiktari = 0;
                String durum = "Tamamlanacak";
                Gorev newTask = new Gorev(taskName, startDate, endDate, ertelemeMiktari, manDay, durum, projectId, calisanId);
                GorevServisi gorevServisi = new GorevServisi();
                gorevServisi.gorevEkle(newTask);

                loadTasks(); // Yeni görevleri yükle

                JOptionPane.showMessageDialog(this, "Görev başarıyla eklendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Adam gün sayısı geçerli bir sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
