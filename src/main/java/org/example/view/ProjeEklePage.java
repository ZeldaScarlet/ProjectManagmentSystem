package org.example.view;

import javax.swing.*;
import java.awt.*;
import org.example.controller.ProjeController;
import org.example.model.Proje;
import org.jdatepicker.impl.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

public class ProjeEklePage extends JFrame {
    private JTextField projeAdField;
    private JDatePickerImpl baslangicTarihiPicker;
    private JDatePickerImpl bitisTarihiPicker;
    private JButton saveButton;
    private JButton cancelButton;

    private ProjeController projeController;

    public ProjeController getProjeController() {
        return projeController;
    }

    public ProjeEklePage(ProjeController projeController) {
        this.projeController = projeController;

        // Ana pencere ayarları
        setTitle("Proje Ekle");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel ve Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Proje adı
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Proje Adı:"), gbc);

        gbc.gridx = 1;
        projeAdField = new JTextField(20);
        projeAdField.setPreferredSize(new Dimension(250, 30));
        panel.add(projeAdField, gbc);

        // Başlangıç tarihi
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Başlangıç Tarihi:"), gbc);

        gbc.gridx = 1;
        baslangicTarihiPicker = createDatePicker();
        panel.add(baslangicTarihiPicker, gbc);

        // Bitiş tarihi
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Bitiş Tarihi:"), gbc);

        gbc.gridx = 1;
        bitisTarihiPicker = createDatePicker();
        panel.add(bitisTarihiPicker, gbc);

        // Kaydet ve İptal butonları
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        saveButton = new JButton("Kaydet");
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.setBackground(new Color(50, 205, 50));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(50, 205, 50), 2));
        saveButton.addActionListener(e -> saveProject());
        buttonPanel.add(saveButton);

        cancelButton = new JButton("İptal");
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(new Color(255, 99, 71));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(255, 99, 71), 2));
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);
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

    private void saveProject() {
        String projeAd = projeAdField.getText();
        String baslangicTarihi = baslangicTarihiPicker.getJFormattedTextField().getText();
        String bitisTarihi = bitisTarihiPicker.getJFormattedTextField().getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.forLanguageTag("tr"));

        // Tarih formatı ayarlama

        // String'i LocalDate'e çevirme
        LocalDate baslangic = LocalDate.parse(baslangicTarihi, formatter);
        LocalDate bitis = LocalDate.parse(bitisTarihi, formatter);

        // ProjeController aracılığıyla projeyi kaydet
        if (projeController.projeEkle(projeAd, baslangic, bitis)) {
            JOptionPane.showMessageDialog(this, "Proje başarıyla kaydedildi.");
            projeController.listAllProjects();
            dispose();  // Sayfayı kapat
        } else {
            JOptionPane.showMessageDialog(this, "Proje kaydedilemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // MainPage controller oluşturulup, ProjeEklePage'e iletilmeli
        SwingUtilities.invokeLater(() -> new ProjeEklePage(new ProjeController(new MainPage())));
    }
}
