package org.example.view;

import javax.swing.*;
import java.awt.*;

public class EmployeeDetailPage extends JPanel {
    private MainPage mainPage;
    private String employeeName;
    private int employeeId;
    private int completedTasks;
    private int ongoingTasks;
    private int upcomingTasks;

    public EmployeeDetailPage(MainPage mainPage, String employeeName, int employeeId) {
        this.mainPage = mainPage;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        //this.completedTasks = completedTasks;
        //this.ongoingTasks = ongoingTasks;
        //this.upcomingTasks = upcomingTasks;

        setLayout(new BorderLayout(20, 20)); // Genel kenar boşlukları

        // Başlık Paneli
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Çalışan Detayları", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Detaylar Paneli
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detailsPanel.add(createDetailRow("Çalışan Adı:", employeeName));
        detailsPanel.add(Box.createVerticalStrut(10)); // Dikey boşluk

        detailsPanel.add(createDetailRow("Çalışan ID:", String.valueOf(employeeId)));
        detailsPanel.add(Box.createVerticalStrut(10));

        detailsPanel.add(createDetailRow("Devam Eden Görevler:", String.valueOf(ongoingTasks)));
        detailsPanel.add(Box.createVerticalStrut(10));

        detailsPanel.add(createDetailRow("Tamamlanan Görevler:", String.valueOf(completedTasks)));
        detailsPanel.add(Box.createVerticalStrut(10));

        detailsPanel.add(createDetailRow("Yaklaşan Görevler:", String.valueOf(upcomingTasks)));

        add(detailsPanel, BorderLayout.CENTER);

        // Alt Panel (Geri Butonu)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> mainPage.getCardLayout().show(mainPage.getCards(), "EmployeesPage"));

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Bir detay satırı oluşturan yardımcı metot.
     */
    private JPanel createDetailRow(String labelText, String valueText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 16));

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(value, BorderLayout.EAST);

        return rowPanel;
    }
}
