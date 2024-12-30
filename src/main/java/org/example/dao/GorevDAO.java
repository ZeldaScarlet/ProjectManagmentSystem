package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Gorev;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GorevDAO {

    private ConnectionManager connectionManager;

    public GorevDAO(){
        this.connectionManager = ConnectionManager.getInstance();
    }

    public boolean gorevKaydet(Gorev gorev){
        String query = "INSERT INTO gorevler(gorev_adi, baslama_tarihi, bitis_tarihi, erteleme_miktari, adam_gun_sayisi, proje_id, calisan_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, gorev.getGorevAdi());
            preparedStatement.setDate(2, Date.valueOf(gorev.getBaslamaTarihi()));
            preparedStatement.setDate(3, Date.valueOf(gorev.getBitisTarihi()));
            preparedStatement.setInt(4, gorev.getErtelemeMiktari());
            preparedStatement.setInt(5, gorev.getAdamGunSayisi());
            preparedStatement.setInt(6, gorev.getProjeId());
            preparedStatement.setInt(7, gorev.getCalisanId());


            int rowsaffected = preparedStatement.executeUpdate();
            return rowsaffected > 0;

        } catch (SQLException e) {
            System.out.println("Gorev eklenirken DAO sınıfında bir hata oluştu!");
        }
        return false;
    }

    public List<Gorev> getTasksByProjectId(int projectId) {
        String query = "SELECT g.gorev_id, g.gorev_adi, g.baslangic_tarihi, g.bitis_tarihi, g.durum " +
                "FROM gorevler g WHERE g.proje_id = ?";

        List<Gorev> gorevler = new ArrayList<>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId); // Proje ID'sini parametreye bağla

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int gorevId = resultSet.getInt("gorev_id");
                    String gorevAdi = resultSet.getString("gorev_adi");
                    Date baslangicTarihi = resultSet.getDate("baslangic_tarihi");
                    Date bitisTarihi = resultSet.getDate("bitis_tarihi");
                    String durum = resultSet.getString("durum");
                    int ertelemeMiktari = resultSet.getInt("erteleme_miktari");
                    int projeId = resultSet.getInt("proje_id");
                    int calisanId = resultSet.getInt("calisan_id");
                    int adamgun = resultSet.getInt("adamgun_sayisi");


                    // Gorev nesnesi oluştur ve listeye ekle
                    Gorev gorev = new Gorev(gorevAdi, baslangicTarihi.toLocalDate(), bitisTarihi.toLocalDate(), ertelemeMiktari,adamgun, durum , projectId , calisanId);
                    gorevler.add(gorev);
                }
            }

        } catch (SQLException e) {
            System.out.println("Veritabanından görevler alınırken hata oluştu: " + e.getMessage());
        }

        return gorevler;
    }

}
