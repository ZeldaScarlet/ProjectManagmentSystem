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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GorevController {
    private GorevDAO gorevDAO;
    private CalisanDAO calisanDAO;
    private ProjectDAO projectDAO;
    private MainPage mainPage;
    private TasksPagePanel tasksPagePanel;
    private int ertelemeMiktari;

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
            this.projectDAO = new ProjectDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı bağlantı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void gorevEkle(String gorevAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi, int adamGunSayisi, int projeId, Calisan atananCalisan) {


        // Bugünün tarihi
        LocalDate lokalZaman = LocalDate.now();

        // Görev durumu ve yeni bitiş tarihi
        String durum;
        LocalDate yeniBitisTarihi = bitisTarihi;

        // Durum ve erteleme mantığı
        if (lokalZaman.isBefore(baslamaTarihi)) {
            // Eğer şu anki tarih görevin başlangıç tarihinden önceyse
            durum = "tamamlanacak";
        } else if (lokalZaman.isAfter(baslamaTarihi) && lokalZaman.isBefore(bitisTarihi)) {
            // Eğer şu anki tarih başlangıç ve bitiş tarihinin arasındaysa
            durum = "devam ediyor";
        } else {
            // Eğer şu anki tarih bitiş tarihinden sonraysa
            durum = "devam ediyor";
            ertelemeMiktari = 7;

            // Gecikme miktarını hesapla (bitiş tarihi ile şu anki zaman arasındaki fark)
            int gecikmeMiktari = (int) ChronoUnit.DAYS.between(bitisTarihi, lokalZaman);

            // Yeni bitiş tarihi, şu anki tarihten 7 gün sonrasına ayarlanıyor
            yeniBitisTarihi = lokalZaman.plusDays(ertelemeMiktari);

            // Gecikme miktarı göreve atanabilir
            projectDAO.updateProjectDelayAndEndDate(projeId, gecikmeMiktari, yeniBitisTarihi);
            // Bu alana gecikme miktarını görevinize eklemek için kullanılabilir.
        }

        // Görev nesnesi oluşturuluyor
        Gorev gorev = new Gorev(gorevAdi, baslamaTarihi, yeniBitisTarihi, ertelemeMiktari, adamGunSayisi, durum, projeId, atananCalisan);

        // Görev kaydediliyor
        gorevDAO.gorevKaydet(gorev);
    }

    // Gorev silme işlemi
    public void deleteTask(int taskId) {
        try {
            gorevDAO.deleteTask(taskId);
            JOptionPane.showMessageDialog(null, "Görev Silindi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Görev silme hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


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
        return gorevDAO.getTasksByEmployeeId(Id);
    }

    public List<Calisan> listAllCalisan() {
        List<Calisan> calisanlar = calisanDAO.getAllEmployees();

        return calisanlar;
    }

    public void guncelleProjeDurumlarini() {
        // Veritabanındaki görevleri alıyoruz
        List<Gorev> gorevler = gorevDAO.getAllTasks(); // Veritabanından görevleri çekiyoruz
        for (Gorev gorev : gorevler) {


            LocalDate baslamaTarihi = gorev.getBaslamaTarihi();
            LocalDate bitisTarihi = gorev.getBitisTarihi();
            int gecikmeMiktari = gorev.getErtelemeMiktari();

            // Başlangıç tarihi lokal zamandan önce mi?
            if (gorev.getDurum() != ("Tamamlandı")){
                if (gorev.getBaslamaTarihi() != null && gorev.getBitisTarihi() != null) {
                    if (gorev.getBaslamaTarihi().isAfter(LocalDate.now())) {
                        gorev.setDurum("Tamamlanacak");
                    } else if (gorev.getBitisTarihi().isBefore(LocalDate.now())) {
                        long gecikmeGunleri = ChronoUnit.DAYS.between(gorev.getBitisTarihi(), LocalDate.now());
                        gorev.setErtelemeMiktari((int) gecikmeGunleri);
                    } else {
                        gorev.setDurum("Devam Ediyor");
                    }
                } else {
                    // Tarihlerden biri null ise, uygun bir işlem yapın veya varsayılan bir durum atayın.
                    gorev.setDurum("Tarih Bilgisi Eksik");
                }
            }
            // Durum ve erteleme miktarını veritabanına kaydet
            gorevDAO.updateTask(gorev);
        }

    }

    public void updateTaskStatus(int gorevId) {
        gorevDAO.updateTaskStatus(gorevId);
    }

    public LocalDate getProjectStartDateByProjectId(int projectId){
        return projectDAO.getStartDateByProjectId(projectId);
    }

    public LocalDate getProjectEndDateByProjectId(int projectId) {
        return projectDAO.getEndDateByProjectId(projectId);
    }

}
