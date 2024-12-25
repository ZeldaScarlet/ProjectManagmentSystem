package org.example.controller;

import org.example.service.KullaniciServisi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private KullaniciServisi kullaniciServisi;

    LoginPage(ProjectManagementApp app) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Sayfa arka planı
        this.setBackground(new Color(240, 240, 240));

        // Başlık
        JLabel titleLabel = new JLabel("Proje Yönetim Sistemi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(54, 69, 79)); // Modern koyu gri renk

        // Kullanıcı Adı
        JLabel usernameLabel = createCustomLabel("Kullanıcı Adı:");
        usernameField = createCustomTextField();

        // Şifre
        JLabel passwordLabel = createCustomLabel("Şifre:");
        passwordField = createCustomPasswordField();

        // Giriş Butonu
        JButton loginButton = createCustomButton("Giriş Yap");
        JLabel errorMessage = new JLabel("", SwingConstants.CENTER);
        errorMessage.setForeground(Color.RED);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println(username + " " + password);
            kullaniciServisi =  new KullaniciServisi();

            if(kullaniciServisi.kullaniciDogrula(username, password)){

                app.showPage("HomePage");
            }else {
                System.out.println("Kullanıcı adı veya parola hatalı!!");
            }

        });

        // GridBagLayout ile bileşenleri yerleştir
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        this.add(usernameLabel, gbc);

        gbc.gridx = 1;
        this.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        this.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        this.add(loginButton, gbc);

        gbc.gridy = 4;
        this.add(errorMessage, gbc);
    }

    // Özel etiket oluşturucu
    private JLabel createCustomLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(new Color(54, 69, 79)); // Modern gri renk
        return label;
    }

    // Özel metin alanı
    private JTextField createCustomTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    // Özel şifre alanı
    private JPasswordField createCustomPasswordField() {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return passwordField;
    }

    // Özel buton oluşturucu
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(75, 160, 230)); // Modern mavi
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}

