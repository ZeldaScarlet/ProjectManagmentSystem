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


}