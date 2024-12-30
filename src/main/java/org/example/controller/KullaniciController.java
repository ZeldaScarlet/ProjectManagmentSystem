package org.example.controller;

import org.example.dao.KullaniciDAO;
import org.example.model.Kullanici;

public class KullaniciController {
    private KullaniciDAO kullaniciDAO;

    public KullaniciController(){
        this.kullaniciDAO = new KullaniciDAO();
    }

    public boolean registerUser(String kullaniciAdi, String parola) {

        if(kullaniciAdi.isEmpty() && parola.isEmpty()){
            return false;
        }
        // Kullanıcıyı veritabanına kaydediyoruz
        Kullanici newUser = new Kullanici(kullaniciAdi, parola);
        return kullaniciDAO.kullaniciEkle(newUser);
    }

    public boolean kullaniciDogrula(String kullaniciAdi, String parola){

        Kullanici kullanici = kullaniciDAO.kullaniciAdiVeParolaylaAl(kullaniciAdi, parola);
        if(kullanici != null && kullanici.getParola().equals(parola)){
            return true;
        }
        System.out.println("İki");
        return false;
    }
}
