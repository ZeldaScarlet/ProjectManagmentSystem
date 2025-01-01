package org.example.view;

import org.example.controller.ProjeController;
import org.example.controller.GorevController;
import org.example.model.Proje;
import org.example.model.Gorev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeDetailPage extends JPanel {

    private JTable projectTable;
    private JTable taskTable;
    private DefaultTableModel projectTableModel;
    private DefaultTableModel taskTableModel;
    private JLabel completedProjectsLabel;
    private JLabel completedTasksLabel;
    private MainPage mainPage;
    private int employeeId;
    private ProjeController projeController;
    private GorevController gorevController = new GorevController(mainPage);

    public EmployeeDetailPage(MainPage mainPage, String employeeName, int employeeId) {
        this.mainPage = mainPage;
        this.employeeId = employeeId;

        setLayout(new BorderLayout(10, 10));

        // Üst kısım: Çalışan adı
        JLabel employeeNameLabel = new JLabel("Çalışan: " + employeeName, JLabel.CENTER);
        employeeNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        employeeNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(employeeNameLabel, BorderLayout.NORTH);

        // Orta kısım: Projeler ve görevler tablosu
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);

        // Projeler tablosu

        // Görevler tablosu
        taskTableModel = new DefaultTableModel(new String[]{"Görev ID", "Görev Adı", "Durum"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable = new JTable(taskTableModel);
        JScrollPane taskScrollPane = new JScrollPane(taskTable);
        taskScrollPane.setBorder(BorderFactory.createTitledBorder("Yer Aldığı Görevler"));
        splitPane.setBottomComponent(taskScrollPane);

        add(splitPane, BorderLayout.CENTER);

        // Alt kısım: İstatistikler ve Geri Düğmesi
        JPanel combinedPanel = new JPanel(new BorderLayout());

        // İstatistik paneli
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        completedProjectsLabel = new JLabel("Tamamlanan Görev Sayısı: 0");
        completedTasksLabel = new JLabel("Tamamlanmayan Görev Sayısı: 0");
        completedProjectsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        completedTasksLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        statsPanel.add(completedProjectsLabel);
        statsPanel.add(completedTasksLabel);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Geri düğmesi paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> mainPage.getCardLayout().show(mainPage.getCards(), "EmployeesPage"));
        buttonPanel.add(backButton);

        // İstatistik panelini ve düğme panelini üst panele ekle
        combinedPanel.add(statsPanel, BorderLayout.CENTER);
        combinedPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Üst paneli ana düzenin güneyine ekle
        add(combinedPanel, BorderLayout.SOUTH);

        // Çalışanın projelerini yükle
        loadEmployeeDetails(employeeId);
    }


    // Seçilen projeye ait görevleri yükle
    // Çalışana ait projeleri ve görevleri yükleme
    private void loadEmployeeDetails(int employeeId) {
        // Çalışanın görevlerini yükleyin
        List<Gorev> tasks = gorevController.getTasksByEmployeeId(employeeId);
        taskTableModel.setRowCount(0); // Mevcut görevleri temizle
        for (Gorev task : tasks) {
            taskTableModel.addRow(new Object[]{
                    task.getGorevId(),
                    task.getGorevAdi(),
                    task.getDurum()
            });
        }

        // Tamamlanan görev sayısını güncelleyin
        updateCompletedTasksCount(tasks);

        // İstatistikleri güncelleyin
        updateStatistics(tasks);
    }
    private void updateCompletedTasksCount(List<Gorev> tasks) {
        int completedTasksCount = 0;

        // Görevlerin her birini kontrol et
        for (Gorev task : tasks) {
            if ("Tamamlandı".equals(task.getDurum())) {
                completedTasksCount++;
            }
        }

        // Tamamlanan görev sayısını ekranda göster
        completedTasksLabel.setText("Tamamlanan Görev Sayısı: " + completedTasksCount);
    }

    private void updateStatistics(List<Gorev> tasks) {
        int completedTasks = 0;
        int pendingTasks = 0;

        for (Gorev task : tasks) {
            if ("tamamlandı".equals(task.getDurum())) {
                completedTasks++;
            } else {
                pendingTasks++;
            }
        }

        completedTasksLabel.setText("Tamamlanmayan Görev Sayısı: " + pendingTasks);
        completedProjectsLabel.setText("Tamamlanan Görev Sayısı: " + completedTasks);
    }
}

