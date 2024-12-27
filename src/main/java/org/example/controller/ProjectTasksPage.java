package org.example.controller;

import org.example.model.Gorev;
import org.example.service.GorevServisi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ProjectTasksPage extends JFrame {
    private JTextField txtGorevAdi;
    private JTextField txtBaslamaTarihi;
    private JTextField txtBitisTarihi;
    private JTextField txtAdamGunSayisi;
    private JComboBox<String> cmbDurum;
    private JTextField txtCalisanId;
    private int projeId; // İlgili projenin ID'si
    private GorevServisi gorevServisi;

    public ProjectTasksPage(int projeId) {
        this.projeId = projeId;
        this.gorevServisi = new GorevServisi();

        setTitle("Görev Ekle");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Form Elemanları
        JLabel lblGorevAdi = new JLabel("Görev Adı:");
        txtGorevAdi = new JTextField();

        JLabel lblBaslamaTarihi = new JLabel("Başlama Tarihi (YYYY-MM-DD):");
        txtBaslamaTarihi = new JTextField();

        JLabel lblBitisTarihi = new JLabel("Bitiş Tarihi (YYYY-MM-DD):");
        txtBitisTarihi = new JTextField();

        JLabel lblAdamGunSayisi = new JLabel("Adam Gün Sayısı:");
        txtAdamGunSayisi = new JTextField();

        JLabel lblDurum = new JLabel("Durum:");
        cmbDurum = new JComboBox<>(new String[]{"tamamlanacak", "devam ediyor", "tamamlandı"});

        JLabel lblCalisanId = new JLabel("Çalışan ID:");
        txtCalisanId = new JTextField();

        JButton btnKaydet = new JButton("Kaydet");
        btnKaydet.addActionListener(e -> kaydet());

        // Form Elemanlarını Eklemek
        add(lblGorevAdi);
        add(txtGorevAdi);
        add(lblBaslamaTarihi);
        add(txtBaslamaTarihi);
        add(lblBitisTarihi);
        add(txtBitisTarihi);
        add(lblAdamGunSayisi);
        add(txtAdamGunSayisi);
        add(lblDurum);
        add(cmbDurum);
        add(lblCalisanId);
        add(txtCalisanId);
        add(new JLabel()); // Boş yer tutucu
        add(btnKaydet);

        setVisible(true);
    }

    private void kaydet() {
        try {
            // Formdan Bilgileri Al
            String gorevAdi = txtGorevAdi.getText();
            LocalDate baslamaTarihi = LocalDate.parse(txtBaslamaTarihi.getText());
            LocalDate bitisTarihi = LocalDate.parse(txtBitisTarihi.getText());
            int adamGunSayisi = Integer.parseInt(txtAdamGunSayisi.getText());
            String durum = (String) cmbDurum.getSelectedItem();
            int calisanId = Integer.parseInt(txtCalisanId.getText());
            int ertelememiktari = 0;

            // Görev Nesnesi Oluştur
            Gorev yeniGorev = new Gorev(gorevAdi ,baslamaTarihi, bitisTarihi , ertelememiktari , adamGunSayisi ,durum , projeId , calisanId);

            // Servis Üzerinden Veritabanına Kaydet
            boolean basarili = gorevServisi.gorevEkle(yeniGorev);

            if (basarili) {
                JOptionPane.showMessageDialog(this, "Görev başarıyla eklendi!");
                dispose(); // Formu kapat
            } else {
                JOptionPane.showMessageDialog(this, "Görev eklenemedi. Lütfen tekrar deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hatalı giriş! Lütfen bilgileri kontrol edin.\n" + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}

