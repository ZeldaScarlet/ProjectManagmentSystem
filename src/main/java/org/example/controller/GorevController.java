package org.example.controller;

import org.example.dao.CalisanDAO;
import org.example.dao.GorevDAO;
import org.example.dao.ProjectDAO;
import org.example.model.Calisan;
import org.example.model.Gorev;
import org.example.view.MainPage;
import org.example.view.TasksPagePanel;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GorevController {
    private GorevDAO gorevDAO;
    private CalisanDAO calisanDAO;
    private MainPage mainPage;
    private TasksPagePanel tasksPagePanel;

    public GorevController(MainPage mainPage){
        this.mainPage = mainPage;
        try {
            this.gorevDAO = new GorevDAO();
            this.calisanDAO = new CalisanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public GorevController(TasksPagePanel tasksPagePanel){
        this.tasksPagePanel = tasksPagePanel;
        try {
            this.gorevDAO = new GorevDAO();
            this.calisanDAO = new CalisanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void gorevEkle(String GorevAdi, String baslamaTarihi, String bitisTarihi, int adamGunSayisi, int projeId,Calisan atananCalisan){

        // erteleme işlemleri ve durum güncellemesi
        String durum = "Devam ediyor";
        Gorev gorev = new Gorev(GorevAdi, LocalDate.parse(baslamaTarihi), LocalDate.parse(bitisTarihi), 0, adamGunSayisi, durum, projeId, atananCalisan);
        gorevDAO.gorevKaydet(gorev);
    }

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

    /*public void addTask(String GorevAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi, ) {
        gorevDAO.gorevKaydet();
    }*/


    public List<Gorev[]> getTasksByProjectId(int projectId) {
        List<Gorev> gorevler = gorevDAO.getTasksByProjectId(projectId);
        List<Gorev[]> taskList = new ArrayList<>();

        for (Gorev gorev : gorevler) {
            Gorev[] gorevArray = new Gorev[]{
                    new Gorev(
                            gorev.getGorevId(),
                            gorev.getGorevAdi(),
                            gorev.getBaslamaTarihi(),
                            gorev.getBitisTarihi(),
                            gorev.getErtelemeMiktari(),
                            gorev.getAdamGunSayisi(),
                            gorev.getDurum(),
                            gorev.getAtanancalisan(),
                            gorev.getProjeId()
                    )
            };
            taskList.add(gorevArray);
        }

        return taskList;
    }

    public List<Gorev> getTasksByEmployeeId(int Id) {
        return gorevDAO.getTasksByProjectId(Id);
    }

    public List<Calisan> listAllCalisan() {
        List<Calisan> calisanlar = calisanDAO.getAllEmployees();

        return calisanlar;
    }

}
