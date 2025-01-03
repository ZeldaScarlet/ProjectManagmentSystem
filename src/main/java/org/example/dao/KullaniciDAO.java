package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Kullanici;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KullaniciDAO {

    public KullaniciDAO(){}

    public Kullanici kullaniciAdiVeParolaylaAl(String kullaniciAdi, String parola){

        String query = "SELECT * FROM kullanicilar WHERE kullanici_adi = ? AND parola = ?";
        Kullanici kullanici = null;


        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, parola);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                kullanici = new Kullanici(
                        resultSet.getString("kullanici_adi"),
                        resultSet.getString("parola")
                );
                kullanici.setKullaniciId(resultSet.getInt("kullanici_id"));
            }

        } catch (SQLException e) {
            System.out.println("Kullanıcı getirilirken DAO sınıfında bir hata oluştu!");
        }
        return kullanici;
    }

    public boolean kullaniciEkle(Kullanici kullanici){

        String query = "INSERT INTO kullanicilar(kullanici_adi, parola) VALUES (?, ?)";

        try(Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, kullanici.getKullaniciAdi());
            preparedStatement.setString(2, kullanici.getParola());

            int rowsaffected = preparedStatement.executeUpdate();
            return rowsaffected > 0;

        } catch (SQLException e) {
            System.out.println("Kullanıcı eklenirken DAO sınıfında bir hata oluştu!");
        }
        return false;
    }
}
