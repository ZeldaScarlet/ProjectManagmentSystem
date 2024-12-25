package org.example.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeListPage extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    // Buton hücresini render eden sınıf
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Detay");
            setFont(new Font("Tahoma", Font.PLAIN, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;  // Butonu hücrede göster
        }
    }

    // Buton tıklama işlevselliği ekleyen sınıf
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Butona tıklandığında yapılacak işlem (Çalışan Detayları)
                    int row = employeeTable.getSelectedRow();
                    String employeeName = (String) employeeTable.getValueAt(row, 1); // Çalışan adı alınıyor
                    JOptionPane.showMessageDialog(button, employeeName + " için detaylar görüntüleniyor!", "Çalışan Detayları", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    public EmployeeListPage() {
        setLayout(new BorderLayout());

        // Tablo modeli ve tablo oluşturuluyor
        String[] columnNames = {"ID", "Çalışan Adı", "Detay"};
        tableModel = new DefaultTableModel(columnNames, 0);

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getColumn("Detay").setCellRenderer(new ButtonRenderer());
        employeeTable.getColumn("Detay").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Örnek çalışan verilerini tabloya yükle
        loadExampleEmployees();

        // Tablo stil ayarları
        employeeTable.setRowHeight(30); // Satır yüksekliği
        employeeTable.setFont(new Font("Tahoma", Font.BOLD, 16)); // Yazı tipi ve boyutu
        employeeTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18)); // Başlık yazı tipi ve boyutu
        employeeTable.getTableHeader().setBackground(new Color(220, 220, 220)); // Başlık arka plan rengi
        add(scrollPane, BorderLayout.CENTER);

        // Yeni çalışan ekleme butonu
        JButton addEmployeeButton = new JButton("Yeni Çalışan Ekle");
        addEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        addEmployeeButton.setBackground(new Color(100, 149, 237));
        addEmployeeButton.setForeground(Color.WHITE);
        addEmployeeButton.setSize(50, 50);

        // Butona işlev ekle
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewEmployee(); // Yeni çalışan ekleme metodunu çağır
            }
        });

        // Panel düzeni
        add(scrollPane, BorderLayout.CENTER);
        add(addEmployeeButton, BorderLayout.SOUTH);
    }

    // Örnek çalışan verilerini tabloya yükleyen metod
    private void loadExampleEmployees() {
        // Tabloyu temizle
        tableModel.setRowCount(0);

        // Örnek çalışan verileri
        Object[][] exampleEmployees = {
                {1, "Ahmet Yılmaz"},
                {2, "Ayşe Demir"},
                {3, "Mehmet Kaya"}
        };

        // Örnek verileri tabloya ekle
        for (Object[] employee : exampleEmployees) {
            tableModel.addRow(employee);
        }
    }

    // Yeni bir çalışan ekleme işlemi (şu an için sadece gösterim)
    private void addNewEmployee() {
        // Çalışan detayları için giriş alanları oluşturuluyor
        JTextField userNameField = new JTextField(20);

        // Giriş alanlarını bir panelde düzenle
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("Kullanıcı Adı:"));
        inputPanel.add(userNameField);


        // Kullanıcıdan bilgi alma
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Yeni Çalışan Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String userName = userNameField.getText();

            // Yeni çalışan ekleme işlemi (şu anda sadece mesaj gösteriliyor)
            JOptionPane.showMessageDialog(this, "Çalışan başarıyla eklendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);

            // Tabloyu güncelle (yeni çalışan ekleniyor)
            tableModel.addRow(new Object[] {tableModel.getRowCount() + 1, userName});
        }
    }
}
