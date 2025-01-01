package org.example.view;

import org.example.controller.ProjeController;
import org.example.model.Proje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainPage extends JFrame {
    private JPanel cards;  // CardLayout içeriklerini tutan panel
    private CardLayout cardLayout;  // CardLayout nesnesi
    private JButton addProjectButton;
    private JButton deleteProjectButton;
    private JButton viewEmployeesButton;
    private JButton viewTasksButton;
    private JTable projectTable;
    private DefaultTableModel tableModel;
    private ProjeController projeController = new ProjeController(this);

    public MainPage() {
        // Ana pencere ayarları
        setTitle("Giriş ve Yönetim Sistemi");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //projeController.updateProjectStatusAndDelay();

        // CardLayout oluşturuluyor
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // LoginPage'i ekle
        LoginPage loginPage = new LoginPage(this);
        cards.add(loginPage, "LoginPage");

        // Ana sayfa paneli ve diğer panelleri ekle
        JPanel mainPagePanel = createMainPagePanel();
        EmployeesPagePanel employeesPagePanel = new EmployeesPagePanel(this);
        TasksPagePanel tasksPagePanel = new TasksPagePanel(this);

        cards.add(mainPagePanel, "MainPage");
        cards.add(employeesPagePanel, "EmployeesPage");
        cards.add(tasksPagePanel, "TasksPage");

        add(cards);

        setVisible(true);

        // Başlangıçta LoginPage göster
        cardLayout.show(cards, "LoginPage");

        ProjeController projeController = new ProjeController(this);
        projeController.listAllProjects();
    }

    public void updateProjectTable(List<Proje> projeler) {
        tableModel.setRowCount(0); // Tabloyu temizle
        for (Proje proje : projeler) {
            tableModel.addRow(new Object[]{proje.getProjeId(), proje.getProjeAdi(), proje.getBaslamaTarihi(), proje.getBitisTarihi(), proje.getErtelemeMiktari()});
        }
    }

    private JPanel createMainPagePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Tablo oluşturma
        String[] columnNames = {"Proje ID", "Proje Adı", "Başlama Tarihi", "Bitiş Tarihi", "Gecikme Miktarı"};
        tableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        projectTable = new JTable(tableModel);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectTable.getSelectionModel().addListSelectionListener(e -> {
            viewTasksButton.setEnabled(projectTable.getSelectedRow() != -1);
        });

        JScrollPane tableScrollPane = new JScrollPane(projectTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Düğme paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        addProjectButton = new JButton("Proje Ekle");
        addProjectButton.addActionListener(e -> openAddProjectPage());
        buttonPanel.add(addProjectButton);

        deleteProjectButton = new JButton("Projeyi Sil");
        deleteProjectButton.addActionListener(e -> deleteSelectedProject());
        buttonPanel.add(deleteProjectButton);

        viewEmployeesButton = new JButton("Çalışanlar");
        viewEmployeesButton.addActionListener(e -> cardLayout.show(cards, "EmployeesPage"));
        buttonPanel.add(viewEmployeesButton);

        viewTasksButton = new JButton("Görevler");
        viewTasksButton.setEnabled(false);
        viewTasksButton.addActionListener(e -> {
            int selectedRow = projectTable.getSelectedRow();
            if (selectedRow != -1) {
                int projeID = (int) tableModel.getValueAt(selectedRow, 0);
                String projeAdi = (String) tableModel.getValueAt(selectedRow, 1);

                TasksPagePanel tasksPagePanel = (TasksPagePanel) cards.getComponent(3);
                tasksPagePanel.setProjectId(projeID);
                tasksPagePanel.setProjectName(projeAdi);
                tasksPagePanel.loadTasksForProject(projeID);
                cardLayout.show(cards, "TasksPage");
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen bir proje seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(viewTasksButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void openAddProjectPage() {
        SwingUtilities.invokeLater(() -> new ProjeEklePage(new ProjeController(this)));
    }

    private void deleteSelectedProject() {
        int selectedRow = projectTable.getSelectedRow();
        if (selectedRow != -1) {
            int projeId = (int) projectTable.getValueAt(selectedRow, 0);
            ProjeController projeController = new ProjeController(this);
            boolean isDeleted = projeController.deleteProject(projeId);
            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Seçilen proje silindi.");
                projeController.listAllProjects();
            } else {
                JOptionPane.showMessageDialog(this, "Projeyi silerken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir proje seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }

    public JPanel getCards() {
        return cards;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPage::new);
    }

    public void showMainPage() {
        cardLayout.show(cards, "MainPage");
    }

}
