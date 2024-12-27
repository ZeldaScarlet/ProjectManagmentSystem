package org.example.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManagementApp extends JFrame {
    private CardLayout cardLayout; // Sayfalar arası geçişi yönetecek
    private JPanel mainPanel; // Ana panel, CardLayout kullanıyor

    public ProjectManagementApp() {
        // Ana pencere ayarları
        this.setTitle("Proje Yönetim Sistemi");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        this.setResizable(false);

        // CardLayout kullanarak ana panel oluşturuluyor
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Sayfalar oluşturulup ana panele ekleniyor
        LoginPage lp = new LoginPage(this);
        HomePage hp = new HomePage(this);
        ProjectListPage plp = new ProjectListPage(hp);
        EmployeeListPage elp = new EmployeeListPage(hp);
        mainPanel.add(lp, "LoginPage");
        mainPanel.add(hp, "HomePage");
        mainPanel.add(plp,  "ProjectListPage");
        mainPanel.add(elp , "EmployeeListPage");


        this.add(mainPanel);
        this.setVisible(true);
    }

    // Sayfalar arasında geçiş yapmayı sağlayan yöntem
    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }


    public static void main(String[] args) {
        new ProjectManagementApp();
    }

    public static class BackButton extends JPanel {
        public BackButton(JFrame currentFrame, JFrame previousFrame) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(60, 30)); // Küçük ve kompakt bir boyut

            JButton btnBack = new JButton("←");
            btnBack.setFont(new Font("Arial", Font.BOLD, 14)); // Basit ve okunabilir
            btnBack.setToolTipText("Geri Dön");
            btnBack.setFocusPainted(false);
            btnBack.setBorder(BorderFactory.createEmptyBorder());
            btnBack.setBackground(Color.LIGHT_GRAY);
            btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Tuşa basıldığında mevcut sayfa kapanır ve önceki sayfa açılır
            btnBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentFrame.dispose(); // Mevcut sayfayı kapat
                    previousFrame.setVisible(true); // Önceki sayfayı göster
                }
            });

            add(btnBack, BorderLayout.CENTER);
        }
    }
}