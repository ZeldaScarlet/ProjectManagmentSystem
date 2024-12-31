package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import org.example.controller.GorevController;
import org.example.model.Calisan;
import org.example.model.Gorev;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

public class TasksPagePanel extends JPanel {
    private MainPage mainPage;
    private JTable tasksTable;
    private DefaultTableModel tableModel;
    private String projectName; // Proje adı
    private int projectId;
    private GorevController taskController;


    public TasksPagePanel(MainPage mainPage) {
        this.mainPage = mainPage;
        taskController = new GorevController(this);
        setLayout(new BorderLayout());

        // Başlık olarak proje adını ekleme
        JLabel projectLabel = new JLabel("Proje Adı: " + (projectName != null ? projectName : ""), JLabel.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(projectLabel, BorderLayout.NORTH);

        // Tabloyu oluşturma
        String[] columnNames = {"Görev ID", "Görev Adı", "Başlangıç Tarihi", "Bitiş Tarihi", "Adam Gün Sayısı", "Erteleme Miktarı", "Durum", "Çalışan"};
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tüm hücreleri düzenlenemez yap
            }
        };

        tasksTable = new JTable(tableModel);
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(tasksTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(tableScrollPane, BorderLayout.CENTER);

        // Düğme paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // "Görev Ekle" düğmesi
        JButton addTaskButton = new JButton("Görev Ekle");
        addTaskButton.addActionListener(e -> openAddTaskDialog());
        buttonPanel.add(addTaskButton);

        // "Görev Sil" düğmesi
        JButton deleteTaskButton = new JButton("Görev Sil");
        deleteTaskButton.addActionListener(e -> deleteSelectedTask());
        buttonPanel.add(deleteTaskButton);

        // "Ana Sayfa" düğmesi
        JButton backToMainPageButton = new JButton("Ana Sayfa");
        backToMainPageButton.addActionListener(e -> mainPage.getCardLayout().show(mainPage.getCards(), "MainPage"));
        buttonPanel.add(backToMainPageButton);

        // Düğme panelini ekle
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        Component[] components = getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            JLabel projectLabel = (JLabel) components[0];
            projectLabel.setText("Proje Adı: " + projectName);
        }
    }


    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }



    public void loadTasksForProject(int projectId) {
        try {
            List<Gorev[]> tasks = taskController.getTasksByProjectId(projectId);

            // Mevcut tabloyu temizle
            tableModel.setRowCount(0);

            // Görevleri tabloya ekle
            for (Gorev[] taskArray : tasks) {
                for (Gorev task : taskArray) {
                    tableModel.addRow(new Object[]{
                            task.getGorevId(),
                            task.getGorevAdi(),
                            task.getBaslamaTarihi(),
                            task.getBitisTarihi() != null ? task.getBitisTarihi() : "Henüz tamamlanmadı",
                            task.getAdamGunSayisi(),
                            task.getErtelemeMiktari() + " gün",
                            task.getDurum(),
                            task.getAtanancalisan() != null ? task.getAtanancalisan().getCalisanAdiSoyadi() : "Atanmamış"
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Görevler yüklenirken bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Görev ekleme diyalog penceresi
    private void openAddTaskDialog() {
        JDialog dialog = new JDialog((Frame) null, "Yeni Görev Ekle", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10 )); // 7 satır, 2 sütun düzeni

        // Görev Adı
        dialog.add(new JLabel("Görev Adı:"));
        JTextField taskNameField = new JTextField();
        dialog.add(taskNameField);

        // Başlangıç Tarihi
        dialog.add(new JLabel("Başlangıç Tarihi:"));
        JDatePickerImpl startDatePicker = createDatePicker();
        dialog.add(startDatePicker);

        // Bitiş Tarihi
        dialog.add(new JLabel("Bitiş Tarihi:"));
        JDatePickerImpl endDatePicker = createDatePicker();
        dialog.add(endDatePicker);

        // Adam Gün Sayısı
        dialog.add(new JLabel("Adam Gün Sayısı:"));
        JTextField manDaysField = new JTextField();
        dialog.add(manDaysField);

        // Çalışan Seçimi (ComboBox)
        dialog.add(new JLabel("Çalışan Seçin:"));
        JComboBox<Calisan> employeeComboBox = new JComboBox<>();

        for(Calisan calisan : taskController.listAllCalisan()){
            employeeComboBox.addItem(calisan);
        }

        dialog.add(employeeComboBox);

        // Kaydet ve Kapat butonları
        JButton saveButton = new JButton("Kaydet");
        saveButton.addActionListener(e -> {
            String taskName = taskNameField.getText();
            String startDate = getDateFromDatePicker(startDatePicker);
            String endDate = getDateFromDatePicker(endDatePicker);
            int manDays =  Integer.parseInt(manDaysField.getText());

            Calisan selectedEmployee = (Calisan) employeeComboBox.getSelectedItem();

            taskController.gorevEkle(taskName, startDate,endDate,manDays, this.projectId, selectedEmployee);


            Object[] newRow = {
                    tableModel.getRowCount() + 1,
                    taskName,
                    startDate,
                    endDate,
                    manDays,
                    "0 gün",
                    "Devam Ediyor",
                    selectedEmployee.toString()
            };
            tableModel.addRow(newRow);
            dialog.dispose();
        });
        dialog.add(saveButton);

        JButton closeButton = new JButton("Kapat");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties i18nStrings = new Properties();
        i18nStrings.put("text.today", "Bugün");
        i18nStrings.put("text.month", "Ay");
        i18nStrings.put("text.year", "Yıl");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, i18nStrings);
        JFormattedTextField.AbstractFormatter formatter = new DateComponentFormatter();
        return new JDatePickerImpl(datePanel, formatter);
    }

    private String getDateFromDatePicker(JDatePickerImpl datePicker) {
        Object selectedDate = datePicker.getModel().getValue();
        if (selectedDate != null) {
            LocalDate localDate = ((java.util.Date) selectedDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.toString();  // YYYY-MM-DD formatında döndürür
        }
        return "";
    }


    private void deleteSelectedTask() {
        int selectedRow = tasksTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Seçilen görev silindi.");
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir görev seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }
}
