package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Gorev;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
}
