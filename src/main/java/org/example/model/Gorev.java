package org.example.model;

import java.time.LocalDate;


public class Gorev {
    private int gorevId;
    private String gorevAdi;
    private LocalDate baslamaTarihi;
    private LocalDate bitisTarihi;
    private int ertelemeMiktari;
    private int adamGunSayisi;
    private String durum;
    private int projeId;
    private Calisan atanancalisan;
    private int atananCalisanId;

    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public Calisan getAtanancalisan() {
        return atanancalisan;
    }

    public int setAtananCalisanId;

    public void setAtanancalisan(Calisan atananCalisan) {
        this.atanancalisan = atananCalisan;
    }

    // Constructor
    public Gorev() {

    }

    public Gorev(int gorevId, String gorevAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi, int ertelemeMiktari, int adamGunSayisi, String durum, Calisan atanancalisan, int projeId){
        this.gorevId = gorevId;
        this.gorevAdi = gorevAdi;
        this.baslamaTarihi = baslamaTarihi;
        this.bitisTarihi = bitisTarihi;
        this.ertelemeMiktari = ertelemeMiktari;
        this.adamGunSayisi = adamGunSayisi;
        this.durum = durum;
        this.atanancalisan = atanancalisan;
        this.projeId = projeId;
    }

    public Gorev(String gorevAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi, int ertelemeMiktari, int adamGunSayisi, String durum, int projeId,Calisan atanancalisan) {

        this.gorevAdi = gorevAdi;
        this.baslamaTarihi = baslamaTarihi;
        this.bitisTarihi = bitisTarihi;
        this.ertelemeMiktari = ertelemeMiktari;
        this.adamGunSayisi = adamGunSayisi;
        this.durum = durum;
        this.projeId = projeId;
        this.atanancalisan = atanancalisan;
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

    public LocalDate getBaslamaTarihi() {
        return baslamaTarihi;
    }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) {
        this.baslamaTarihi = baslamaTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
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

}
