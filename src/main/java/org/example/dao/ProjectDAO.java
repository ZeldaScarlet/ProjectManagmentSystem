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

        String query = "INSERT INTO projeler(proje_adi, baslama_tarihi, bitis_tarihi) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, proje.getProjeAdi());
            preparedStatement.setDate(2, Date.valueOf(proje.getBaslamaTarihi()));
            preparedStatement.setDate(3, Date.valueOf(proje.getBitisTarihi()));

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

    public List<Proje> getProjectsByEmployeeId(int id) {
        String query = "SELECT * FROM projeler WHERE calisan_id = ?";
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







}
