package org.example.view;

import org.example.controller.KullaniciController;

import javax.swing.*;
import java.awt.*;

public class RegisterPopup extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterPopup(JFrame parent) {
        super(parent, "Kaydol", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Kullanıcı Adı
        JLabel usernameLabel = createCustomLabel("Kullanıcı Adı:");
        usernameField = createCustomTextField();

        // Şifre
        JLabel passwordLabel = createCustomLabel("Şifre:");
        passwordField = createCustomPasswordField();

        // Şifre Onayı
        JLabel confirmPasswordLabel = createCustomLabel("Şifre Tekrar:");
        confirmPasswordField = createCustomPasswordField();

        // Hata Mesajı
        JLabel errorMessage = new JLabel("", SwingConstants.CENTER);
        errorMessage.setForeground(Color.RED);

        // Kayıt Butonu
        JButton registerButton = createCustomButton("Kaydol", new Color(75, 160, 230));
        registerButton.addActionListener(e -> registerUser(errorMessage));

        // Kapatma Butonu
        JButton closeButton = createCustomButton("İptal", new Color(220, 53, 69));
        closeButton.addActionListener(e -> dispose());

        // Bileşenleri ekle
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(new JLabel("<html><h2>Yeni Hesap Oluştur</h2></html>", SwingConstants.CENTER), gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        add(errorMessage, gbc);

        gbc.gridy = 5;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, gbc);

        // Dialog özellikleri
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void registerUser(JLabel errorMessage) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        KullaniciController kullaniciController = new KullaniciController();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessage.setText("Tüm alanları doldurun!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessage.setText("Şifreler eşleşmiyor!");
            return;
        }

        /*if (kullaniciController.isUsernameExists(username)) {
            errorMessage.setText("Bu kullanıcı adı zaten mevcut!");
            return;
        } */

        boolean isRegistered = kullaniciController.registerUser(username, password);
        if (isRegistered) {
            JOptionPane.showMessageDialog(this, "Kayıt başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            errorMessage.setText("Kayıt sırasında bir hata oluştu!");
        }
    }

    // Özel etiket oluşturucu
    private JLabel createCustomLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    // Özel metin alanı
    private JTextField createCustomTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    // Özel şifre alanı
    private JPasswordField createCustomPasswordField() {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        return passwordField;
    }

    // Özel buton oluşturucu
    private JButton createCustomButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }
}

