package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Proje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.time.temporal.ChronoUnit;


public class ProjectDAO {


    public ProjectDAO() throws SQLException {
    }

    public boolean projeKaydet(Proje proje) {

        String query = "INSERT INTO projeler(proje_adi, baslama_tarihi, bitis_tarihi, erteleme_miktari) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, proje.getProjeAdi());
            preparedStatement.setDate(2, Date.valueOf(proje.getBaslamaTarihi()));
            preparedStatement.setDate(3, Date.valueOf(proje.getBitisTarihi()));
            preparedStatement.setInt(4, proje.getErtelemeMiktari());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Proje> getAllProjects() {
        String query = "SELECT * FROM projeler";
        List<Proje> projects = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Proje proje = new Proje(
                        resultSet.getInt("proje_id"),
                        resultSet.getString("proje_adi"),
                        resultSet.getDate("baslama_tarihi").toLocalDate(),
                        resultSet.getDate("bitis_tarihi").toLocalDate()
                );

                proje.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));
                proje.setOlusturanKullaniciId(resultSet.getInt("olusturan_kullanici_id"));
                projects.add(proje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    // Projeyi silen metod
    public boolean deleteProject(int projeId) {
        String sql = "DELETE FROM projeler WHERE proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projeId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Silme işlemi başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Proje> getProjectsByProjectId(int id) {
        String query = "SELECT * FROM projeler WHERE proje_id = ?";
        List<Proje> projects = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id); // Çalışan ID'sini parametre olarak ekle
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Proje proje = new Proje(
                        resultSet.getInt("proje_id"),
                        resultSet.getString("proje_adi"),
                        resultSet.getDate("baslama_tarihi").toLocalDate(),
                        resultSet.getDate("bitis_tarihi").toLocalDate()
                );

                proje.setErtelemeMiktari(resultSet.getInt("erteleme_miktari"));
                proje.setOlusturanKullaniciId(resultSet.getInt("olusturan_kullanici_id"));
                projects.add(proje); // Listeye proje ekle
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects; // Projeler listesini döndür
    }

    // Projenin başlama tarihini ID'sine göre çekme metodu (LocalDate döndüren)
    public LocalDate getStartDateByProjectId(int projectId) {
        LocalDate startDate = null;
        String query = "SELECT baslama_tarihi FROM projeler WHERE proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Parametreyi yerleştiriyoruz
            stmt.setInt(1, projectId);

            // Sorguyu çalıştırıp sonucu alıyoruz
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Veritabanındaki tarih değerini LocalDate'e dönüştür
                    startDate = rs.getDate("baslama_tarihi").toLocalDate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public LocalDate getEndDateByProjectId(int projectId) {
        LocalDate endDate = null;
        String query = "SELECT bitis_tarihi FROM projeler WHERE proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Parametreyi yerleştiriyoruz
            stmt.setInt(1, projectId);

            // Sorguyu çalıştırıp sonucu alıyoruz
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Veritabanındaki tarih değerini LocalDate'e dönüştür
                    endDate = rs.getDate("bitis_tarihi").toLocalDate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return endDate;
    }

    // Proje gecikme miktarını güncelleyen metod
    public boolean updateProjectDelayAndEndDate(int projectId, int delayAmount, LocalDate endDate) {
        String query = "UPDATE projeler SET erteleme_miktari = ?, bitis_tarihi = ? WHERE proje_id = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Parametreleri set ediyoruz
            preparedStatement.setInt(1, delayAmount);
            preparedStatement.setDate(2, Date.valueOf(endDate));
            preparedStatement.setInt(3, projectId);

            // Sorguyu çalıştırıyoruz
            int affectedRows = preparedStatement.executeUpdate();

            // Eğer herhangi bir satır güncellenmişse, başarılı demektir
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Hata durumunda false döner
        }
    }



}
