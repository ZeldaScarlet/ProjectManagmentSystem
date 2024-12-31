package org.example.controller;

import org.example.dao.CalisanDAO;
import org.example.model.Calisan;
import org.example.view.EmployeesPagePanel;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CalisanController {

    private EmployeesPagePanel employeesPagePanel;
    private CalisanDAO employeeDAO;

    public CalisanController(EmployeesPagePanel employeesPagePanel) {
        this.employeesPagePanel = employeesPagePanel;
        try {
            this.employeeDAO = new CalisanDAO(); // DAO sınıfının örneğini oluştur
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Çalışanları listeleme işlemi
    public void listAllEmployees() {
        try {
            List<Calisan> employees = employeeDAO.getAllEmployees(); // DAO'dan çalışanları alıyoruz
            employeesPagePanel.updateEmployeeTable(employees); // Tabloyu güncelliyoruz
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Çalışanları listeleme hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Çalışan ekleme işlemi
    public void addEmployee(String calisanAdi) {
        if (calisanAdi != null && !calisanAdi.trim().isEmpty()) {
            Calisan eklenenenCalisan = new Calisan(0, calisanAdi);

            try {
                employeeDAO.addEmployee(eklenenenCalisan);
                JOptionPane.showMessageDialog(null, "Çalışan başarıyla eklendi.");
                listAllEmployees(); // Çalışanlar listesi güncelleniyor
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Çalışan eklenirken bir hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Çalışan adı boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Çalışan silme işlemi
    public void deleteEmployee(int employeeId) {
        try {
            employeeDAO.deleteEmployee(employeeId);
            JOptionPane.showMessageDialog(null, "Çalışan başarıyla silindi.");
            listAllEmployees(); // Çalışanlar listesi güncelleniyor
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Çalışan silme hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    //İd ile çalışan bilgileri alma

    public void getEmployeeById(int employeeId) {
        try {
            Calisan calisan = employeeDAO.getEmployeeById(employeeId); // DAO'dan çalışan bilgilerini alıyoruz
            if (calisan != null) {
                // Çalışan bilgilerini göster
                JOptionPane.showMessageDialog(null,
                        "Çalışan ID: " + calisan.getCalisanId() + "\nÇalışan Adı: " + calisan.getCalisanAdiSoyadi(),
                        "Çalışan Bilgileri",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Bu ID'ye sahip bir çalışan bulunamadı.",
                        "Bilgi",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Çalışan bilgilerini alırken bir hata oluştu: " + e.getMessage(),
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
