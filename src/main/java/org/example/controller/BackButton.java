package org.example.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JPanel {

    public BackButton(JPanel currentFrame, JPanel previousFrame) {
        setLayout(new BorderLayout()); // BorderLayout kullanarak butonun ortalanması sağlanacak
        setPreferredSize(new Dimension(60, 30)); // Buton boyutlandırması

        JButton btnBack = new JButton("←");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setToolTipText("Geri Dön");
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder());
        btnBack.setBackground(Color.LIGHT_GRAY);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Geri butonuna tıklama işlevi
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // currentFrame'i gizle
                currentFrame.setVisible(false);

                // previousFrame'i göster
                previousFrame.setVisible(true);
            }
        });

        // Butonu yerleştir
        add(btnBack, BorderLayout.WEST);
    }
}


