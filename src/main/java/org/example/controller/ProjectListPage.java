package org.example.controller;

import org.example.model.Proje;
import org.example.service.ProjeServisi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ProjectListPage extends JPanel{
    private JTable projectTable;
    private DefaultTableModel tableModel;


    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Görev Ekle");
            setFont(new Font("Tahoma", Font.PLAIN, 14));
        }


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private JButton button;
        private int row;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setFont(new Font("Tahoma", Font.PLAIN, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int projectId = (int)projectTable.getValueAt(row, 0);
                    ProjectTasksPage projectTasksPage = new ProjectTasksPage(projectId);



                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            this.row = row;
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                isPushed = false;
                return label;
            }
            return null;
        }
    }

    public ProjectListPage(JPanel previousFrame) {
        setLayout(new BorderLayout());

        // Tabloyu başlatırken "Görev Ekle" sütunu ekliyoruz
        String[] columnNames = {"id", "Proje Adı", "Başlangıç Tarihi", "Bitiş Tarihi", "Gecikme Miktarı (Gün)", "Görev Ekle"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectTable = new JTable(tableModel);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // "Görev Ekle" butonunu ekle
        projectTable.getColumn("Görev Ekle").setCellRenderer(new ButtonRenderer());
        projectTable.getColumn("Görev Ekle").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(projectTable);
        projectTable.setRowHeight(30);
        projectTable.setFont(new Font("Tahoma", Font.BOLD, 16));
        projectTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
        projectTable.getTableHeader().setBackground(new Color(220, 220, 220));
        add(scrollPane, BorderLayout.CENTER);

        BackButton backButton = new BackButton(this, previousFrame);
        add(backButton, BorderLayout.NORTH);

        // Projeleri yükle
        loadProjects();

        // Yeni proje ekleme butonu
        JButton addProjectButton = new JButton("Yeni Proje Ekle");
        addProjectButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        addProjectButton.setBackground(new Color(100, 149, 237));
        addProjectButton.setForeground(Color.WHITE);
        addProjectButton.setSize(50, 50);

        addProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewProject();
            }
        });

        add(addProjectButton, BorderLayout.SOUTH);
    }

    // Projeleri veritabanından alıp tabloya ekleyen metod
    private void loadProjects() {
        tableModel.setRowCount(0); // Tabloyu temizle

        // ProjectService sınıfını kullanarak projeleri al
        ProjeServisi projeServisi = new ProjeServisi();
        List<Proje> projects = projeServisi.getAllProjects();
        for (Proje project : projects) {
            Object[] rowData = {
                    project.getProjeId(),
                    project.getProjeAdi(),
                    project.getBaslamaTarihi(),
                    project.getBitisTarihi(),
                    project.getErtelemeMiktari(),
                    "Görev Ekle" // "Görev Ekle" butonunu son sütunda ekliyoruz
            };
            tableModel.addRow(rowData); // Yeni satır ekle
        }
    }

    // Yeni proje ekleme işlemi
    private void addNewProject() {
        JTextField projectNameField = new JTextField(20);
        JTextField startDateField = new JTextField(20);
        JTextField endDateField = new JTextField(20);
        JTextField delayAmountField = new JTextField(20);

        // Giriş alanlarını bir panelde düzenle
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Proje Adı:"));
        inputPanel.add(projectNameField);
        inputPanel.add(new JLabel("Başlangıç Tarihi (YYYY-MM-DD):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("Bitiş Tarihi (YYYY-MM-DD):"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel("Gecikme Miktarı (Gün):"));
        inputPanel.add(delayAmountField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Yeni Proje Ekle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String projectName = projectNameField.getText();
            LocalDate startDate = LocalDate.parse(startDateField.getText());
            LocalDate endDate = LocalDate.parse(endDateField.getText());
            String delayAmountStr = delayAmountField.getText();

            try {
                int delayAmount = Integer.parseInt(delayAmountStr);

                // Yeni projeyi ProjectService üzerinden veritabanına ekleyelim
                Proje newProject = new Proje(projectName, startDate, endDate, delayAmount);
                ProjeServisi projeServisi = new ProjeServisi();
                projeServisi.projeEkle(newProject);

                // Tabloya yeni satır ekle
                tableModel.addRow(new Object[]{newProject.getProjeId(), projectName, startDate, endDate, delayAmount, "Görev Ekle"});

                JOptionPane.showMessageDialog(this, "Proje başarıyla eklendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gecikme miktarı geçerli bir sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
