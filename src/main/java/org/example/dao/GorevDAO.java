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
        String query = "SELECT g.gorev_id, g.gorev_adi, g.baslama_tarihi, g.bitis_tarihi, g.adam_gun_sayisi, " +
                "g.durum, g.erteleme_miktari, g.calisan_id, c.adi_soyadi FROM gorevler g " +
                "JOIN calisanlar c ON g.calisan_id = c.calisan_id WHERE g.proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Gorev gorev = new Gorev();
                gorev.setGorevId(resultSet.getInt("gorev_id"));
                gorev.setGorevAdi(resultSet.getString("gorev_adi"));

                if (resultSet.getDate("baslama_tarihi") != null) {
                    gorev.setBaslamaTarihi(resultSet.getDate("baslama_tarihi").toLocalDate());
                }
                if (resultSet.getDate("bitis_tarihi") != null) {
                    gorev.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
                }

                gorev.setAdamGunSayisi(resultSet.getInt("adam_gun_sayisi"));
                gorev.setDurum(resultSet.getString("durum"));
                gorev.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));

                // Çalışan bilgisini doğrudan alıyoruz
                Calisan calisan = new Calisan();
                calisan.setAdiSoyadi(resultSet.getString("adi_soyadi"));
                gorev.setAtanancalisan(calisan);

                gorevler.add(gorev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gorevler;
    }

    public List<Gorev> getTasksByEmployeeId(int projectId) {
        List<Gorev> gorevler = new ArrayList<>();
        String query = "SELECT g.gorev_id, g.gorev_adi, g.baslama_tarihi, g.bitis_tarihi, g.adam_gun_sayisi, " +
                "g.durum, g.erteleme_miktari, g.calisan_id, c.adi_soyadi FROM gorevler g " +
                "JOIN calisanlar c ON g.calisan_id = c.calisan_id WHERE g.calisan_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Gorev gorev = new Gorev();
                gorev.setGorevId(resultSet.getInt("gorev_id"));
                gorev.setGorevAdi(resultSet.getString("gorev_adi"));

                if (resultSet.getDate("baslama_tarihi") != null) {
                    gorev.setBaslamaTarihi(resultSet.getDate("baslama_tarihi").toLocalDate());
                }
                if (resultSet.getDate("bitis_tarihi") != null) {
                    gorev.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
                }

                gorev.setAdamGunSayisi(resultSet.getInt("adam_gun_sayisi"));
                gorev.setDurum(resultSet.getString("durum"));
                gorev.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));

                // Çalışan bilgisini doğrudan alıyoruz
                Calisan calisan = new Calisan();
                calisan.setAdiSoyadi(resultSet.getString("adi_soyadi"));
                gorev.setAtanancalisan(calisan);

                gorevler.add(gorev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gorevler;
    }

    public void updateTask(Gorev gorev) {
        String updateQuery = "UPDATE gorevler SET durum = ?, erteleme_miktari = ? WHERE gorev_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, gorev.getDurum());
            preparedStatement.setInt(2, gorev.getErtelemeMiktari());
            preparedStatement.setInt(3, gorev.getGorevId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Gorev> getAllTasks() {
        List<Gorev> gorevler = new ArrayList<>();
        String query = "SELECT * FROM gorevler";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Gorev gorev = new Gorev();
                gorev.setGorevId(resultSet.getInt("gorev_id"));
                gorev.setGorevAdi(resultSet.getString("gorev_adi"));

                if (resultSet.getDate("baslama_tarihi") != null) {
                    gorev.setBaslamaTarihi(resultSet.getDate("baslama_tarihi").toLocalDate());
                }
                if (resultSet.getDate("bitis_tarihi") != null) {
                    gorev.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
                }

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

    public void updateTaskStatus(int gorevId) {
        String query = "UPDATE gorevler SET durum = 'Tamamlandı' WHERE gorev_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gorevId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
