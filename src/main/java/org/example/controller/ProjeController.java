package org.example.controller;

import org.example.dao.ProjectDAO;
import org.example.model.Proje;
import org.example.view.MainPage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ProjeController {

    private MainPage mainPage;
    private ProjectDAO projectDAO;

    public ProjeController(MainPage mainPage){
        this.mainPage = mainPage;
        try {
            this.projectDAO = new ProjectDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Proje ekleme işlemi
    public boolean projeEkle(Proje proje){
        return projectDAO.projeKaydet(proje);
    }

    // Projeleri veritabanından alır ve tabloyu günceller
    public void listAllProjects() {
        try {
            List<Proje> projects = projectDAO.getAllProjects(); // DAO'dan projeleri alıyoruz
            mainPage.updateProjectTable(projects); // Tabloyu güncelliyoruz
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainPage, "Projeleri listeleme hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Projeyi veritabanından silen metod
    public boolean deleteProject(int projeId) {
        boolean isDeleted = projectDAO.deleteProject(projeId);
        if (isDeleted) {
            // Silme başarılıysa projeleri yeniden listele
            listAllProjects();
        }
        return isDeleted;
    }

    public void getTasksByProjectId(int projectId){


    }


}
