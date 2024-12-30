package org.example.view;

import org.example.controller.CalisanController;
import org.example.model.Calisan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeesPagePanel extends JPanel {

    private MainPage mainPage;
    private JTable employeesTable;
    private JButton backButton;
    private JButton viewDetailsButton;
    private JButton addEmployeeButton;
    private JButton deleteEmployeeButton;
    private CalisanController controller;

    public EmployeesPagePanel(MainPage mainPage) {
        this.mainPage = mainPage;

        setLayout(new BorderLayout(10, 10));

        String[] columnNames = {"Çalışan ID", "Çalışan Adı"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeesTable = new JTable(model);
        employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(employeesTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addEmployeeButton = new JButton("Çalışan Ekle");
        addEmployeeButton.addActionListener(e -> addEmployee(model));
        bottomPanel.add(addEmployeeButton);

        deleteEmployeeButton = new JButton("Çalışan Sil");
        deleteEmployeeButton.setEnabled(false);
        deleteEmployeeButton.addActionListener(e -> deleteEmployee(model));
        bottomPanel.add(deleteEmployeeButton);

        viewDetailsButton = new JButton("Detay Gör");
        viewDetailsButton.setEnabled(false);
        viewDetailsButton.addActionListener(e -> openEmployeeDetailPage());
        bottomPanel.add(viewDetailsButton);

        backButton = new JButton("Ana Sayfa");
        backButton.addActionListener(e -> mainPage.getCardLayout().show(mainPage.getCards(), "MainPage"));
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        employeesTable.getSelectionModel().addListSelectionListener(e -> {
            if (employeesTable.getSelectedRow() != -1) {
                viewDetailsButton.setEnabled(true);
                deleteEmployeeButton.setEnabled(true);
            } else {
                viewDetailsButton.setEnabled(false);
                deleteEmployeeButton.setEnabled(false);
            }
        });

        controller = new CalisanController(this);

        // Çalışanları listeleme
        controller.listAllEmployees();
    }

    // Çalışanları listeleyen metod
    public void updateEmployeeTable(List<Calisan> employees) {
        DefaultTableModel model = (DefaultTableModel) employeesTable.getModel();
        model.setRowCount(0); // Mevcut verileri temizle

        for (Calisan calisan : employees) {
            model.addRow(new Object[]{calisan.getCalisanId(), calisan.getCalisanAdiSoyadi()});
        }
    }

    // Çalışan ekleme metodu
    private void addEmployee(DefaultTableModel model) {
        String calisanAdi = JOptionPane.showInputDialog(this, "Çalışan Adını Giriniz:", "Çalışan Ekle", JOptionPane.PLAIN_MESSAGE);

        if (calisanAdi != null && !calisanAdi.trim().isEmpty()) {
            controller.addEmployee(calisanAdi);
        } else if (calisanAdi != null) {
            JOptionPane.showMessageDialog(this, "Çalışan adı boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Çalışan silme metodu
    private void deleteEmployee(DefaultTableModel model) {
        int selectedRow = employeesTable.getSelectedRow();
        if (selectedRow != -1) {
            int employeeId = (int) employeesTable.getValueAt(selectedRow, 0);
            controller.deleteEmployee(employeeId);
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir çalışan seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Çalışan detaylarını açan metot
    public void openEmployeeDetailPage() {
        int selectedRow = employeesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir çalışan seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) employeesTable.getValueAt(selectedRow, 0);
        controller.getEmployeeById(employeeId);
        String employeeName = (String) employeesTable.getValueAt(selectedRow, 1);


        EmployeeDetailPage detailPage = new EmployeeDetailPage(mainPage, employeeName, employeeId);
        mainPage.getCardLayout().show(mainPage.getCards(), "EmployeeDetailPage");
    }
}
