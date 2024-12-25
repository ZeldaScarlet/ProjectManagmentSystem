package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Proje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectDAO {
    private ConnectionManager connectionManager;

    public ProjectDAO(){
        this.connectionManager = ConnectionManager.getInstance();
    }

    public boolean projeKaydet(Proje proje) {

        String query = "INSERT INTO projeler(proje_adi, baslama_tarihi, bitis_tarihi, erteleme_miktari) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionManager.getInstance().getConnection();
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
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Proje proje = new Proje(
                        resultSet.getInt("proje_id"),
                        resultSet.getString("proje_adi"),
                        resultSet.getDate("baslama_tarihi").toLocalDate(),
                        resultSet.getDate("bitis_tarihi").toLocalDate(),
                        resultSet.getInt("erteleme_miktari"),
                        resultSet.getInt("olusturan_kullanici_id")
                );
                projects.add(proje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }



}
