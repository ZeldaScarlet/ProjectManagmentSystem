package org.example.model;

import java.sql.Date;

import java.sql.Date;

public class Gorev {
    private int gorevId;
    private String gorevAdi;
    private Date baslamaTarihi;
    private Date bitisTarihi;
    private int ertelemeMiktari;
    private int adamGunSayisi;
    private String durum;
    private int projeId;
    private int calisanId;

    // Constructor
    public Gorev(int gorevId, String gorevAdi, Date baslamaTarihi, Date bitisTarihi, int ertelemeMiktari, int adamGunSayisi, String durum, int projeId, int calisanId) {
        this.gorevId = gorevId;
        this.gorevAdi = gorevAdi;
        this.baslamaTarihi = baslamaTarihi;
        this.bitisTarihi = bitisTarihi;
        this.ertelemeMiktari = ertelemeMiktari;
        this.adamGunSayisi = adamGunSayisi;
        this.durum = durum;
        this.projeId = projeId;
        this.calisanId = calisanId;
    }

    // Getter ve Setter
    public int getGorevId() {
        return gorevId;
    }

    public void setGorevId(int gorevId) {
        this.gorevId = gorevId;
    }

    public String getGorevAdi() {
        return gorevAdi;
    }

    public void setGorevAdi(String gorevAdi) {
        this.gorevAdi = gorevAdi;
    }

    public Date getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(Date baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public int getErtelemeMiktari() {
        return ertelemeMiktari;
    }

    public void setErtelemeMiktari(int ertelemeMiktari) {
        this.ertelemeMiktari = ertelemeMiktari;
    }

    public int getAdamGunSayisi() {
        return adamGunSayisi;
    }

    public void setAdamGunSayisi(int adamGunSayisi) {
        this.adamGunSayisi = adamGunSayisi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }
}
