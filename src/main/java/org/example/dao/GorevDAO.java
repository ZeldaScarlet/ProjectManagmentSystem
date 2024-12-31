package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Calisan;
import org.example.model.Gorev;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GorevDAO {


    public GorevDAO(){
    }

    public void gorevKaydet(Gorev gorev){
        String query = "INSERT INTO gorevler(gorev_adi, baslama_tarihi, bitis_tarihi, erteleme_miktari, adam_gun_sayisi, durum ,proje_id, calisan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, gorev.getGorevAdi());
            preparedStatement.setDate(2, Date.valueOf(gorev.getBaslamaTarihi()));
            preparedStatement.setDate(3, Date.valueOf(gorev.getBitisTarihi()));
            preparedStatement.setInt(4, gorev.getErtelemeMiktari());
            preparedStatement.setInt(5, gorev.getAdamGunSayisi());
            preparedStatement.setString(6, gorev.getDurum());
            preparedStatement.setInt(7, gorev.getProjeId());
            preparedStatement.setInt(8, (gorev.getAtanancalisan()).getCalisanId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Gorev eklenirken DAO sınıfında bir hata oluştu!" + e.getMessage());
        }
    }


    // Görevi ID'ye göre silme metodu
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM gorevler WHERE gorev_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, taskId); // ID parametresini ayarla
            preparedStatement.executeUpdate(); // Silme işlemini gerçekleştir

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Görev silinirken bir hata oluştu: " + e.getMessage());
        }
    }


    public List<Gorev> getTasksByProjectId(int projectId) {
        List<Gorev> gorevler = new ArrayList<>();
        String query = "SELECT * FROM gorevler WHERE proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Gorev gorev = new Gorev();
                gorev.setGorevId(resultSet.getInt("gorev_id"));
                gorev.setGorevAdi(resultSet.getString("gorev_adi"));
                gorev.setBaslamaTarihi(resultSet.getDate("baslama_tarihi").toLocalDate());
                gorev.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
                gorev.setAdamGunSayisi(resultSet.getInt("adam_gun_sayisi"));
                gorev.setDurum(resultSet.getString("durum"));
                gorev.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));
                gorevler.add(gorev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gorevler;
    }

    public List<Gorev> getTasksByEmployeeId(int employeeId) {
        List<Gorev> gorevler = new ArrayList<>();
        String query = "SELECT * FROM gorevler WHERE calisan_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Gorev gorev = new Gorev();
                gorev.setGorevId(resultSet.getInt("gorev_id"));
                gorev.setGorevAdi(resultSet.getString("gorev_adi"));
                gorev.setBaslamaTarihi(resultSet.getDate("baslama_tarihi").toLocalDate());
                gorev.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
                gorev.setAdamGunSayisi(resultSet.getInt("adam_gun_sayisi"));
                gorev.setDurum(resultSet.getString("durum"));
                gorev.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));
                gorevler.add(gorev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gorevler;
    }
}
