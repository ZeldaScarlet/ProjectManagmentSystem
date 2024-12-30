package org.example.controller;

import org.example.dao.GorevDAO;
import org.example.dao.ProjectDAO;
import org.example.model.Gorev;
import org.example.view.MainPage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GorevController {
    private GorevDAO gorevDAO;
    private MainPage mainPage;

    public GorevController(MainPage mainPage){
        this.mainPage = mainPage;
        try {
            this.gorevDAO = new GorevDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

   /* public boolean gorevEkle(Gorev gorev){

        return gorevDAO.gorevKaydet(gorev);
    }*/

    // Gorev silme işlemi
    public void deleteTask(int taskId) {
        try {
            gorevDAO.deleteTask(taskId);
            JOptionPane.showMessageDialog(null, "Çalışan başarıyla silindi.");
            //listTasksByProjectId(); // Gorev listesi güncelleniyor
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Çalışan silme hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Object[]> getTasksByProjectId(int projectId) {
        List<Gorev> gorevler = gorevDAO.getTasksByProjectId(projectId);
        List<Object[]> taskList = new ArrayList<>();

        for (Gorev gorev : gorevler) {
            taskList.add(new Object[]{
                    gorev.getGorevId(),
                    gorev.getGorevAdi(),
                    gorev.getBaslamaTarihi(),
                    gorev.getBitisTarihi(),
                    gorev.getAdamGunSayisi(),
                    gorev.getErtelemeMiktari() + " gün",
                    gorev.getDurum(),
                    gorev.getAtanancalisan()
            });
        }

        return taskList;
    }
}
