package org.example.model;

public class Kullanici {
    private int kullaniciId;
    private String kullaniciAdi;
    private String parola;

    // Constructor

    public Kullanici(String kullaniciAdi, String parola){
        this.kullaniciAdi = kullaniciAdi;
        this.parola = parola;
    }

    // Getter ve Setter
    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
