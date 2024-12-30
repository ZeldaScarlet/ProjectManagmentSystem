package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Calisan;
import org.example.model.Kullanici;
import org.example.model.Proje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalisanDAO {


    public CalisanDAO() throws SQLException {

    }


    public List<Calisan> getAllEmployees() {
        List<Calisan> calisanlar = new ArrayList<>();
        String query = "SELECT * FROM calisanlar";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Calisan calisan = new Calisan(
                        resultSet.getInt("calisan_id"),
                        resultSet.getString("adi_soyadi")
                );
                calisanlar.add(calisan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return calisanlar;
    }

    /*public List<Proje> getProjectsByEmployeeId(int calisanId) {
        List<Proje> projeler = new ArrayList<>();
        String query = "SELECT p.proje_id, p.proje_adi, p.baslama_tarihi, p.bitis_tarihi, p.erteleme_miktari, p.olusturan_kullanici_id " +
                "FROM projeler p " +
                "JOIN gorevler g ON p.proje_id = g.proje_id " +
                "WHERE g.calisan_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, calisanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Proje proje = new Proje(
                        resultSet.getInt("proje_id"),
                        resultSet.getString("proje_adi"),
                        resultSet.getDate("baslama_tarihi").toLocalDate(),
                        resultSet.getDate("bitis_tarihi").toLocalDate(),
                        resultSet.getInt("erteleme_miktari"),
                        resultSet.getInt("olusturan_kullanici_id")
                );
                projeler.add(proje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projeler;
    }*/

    public void addEmployee(Calisan calisan) {
        String query = "INSERT INTO calisanlar (adi_Soyadi) VALUES (?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, calisan.getCalisanAdiSoyadi());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Çalışan eklenirken hata oluştu", e);
        }
    }

    // Çalışanı ID'ye göre silme metodu
    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM calisanlar WHERE calisan_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeId); // ID parametresini ayarla
            preparedStatement.executeUpdate(); // Silme işlemini gerçekleştir

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Çalışan silinirken bir hata oluştu: " + e.getMessage());
        }
    }

    public Calisan getEmployeeById(int id) throws SQLException {
        String query = "SELECT * FROM calisanlar WHERE calisan_id = ?";
        Calisan calisan = null;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int employeeId = rs.getInt("calisan_id");
                    String employeeName = rs.getString("adi_soyadi");
                    calisan = new Calisan(employeeId, employeeName);
                }
            }
        }

        return calisan;
    }



}


