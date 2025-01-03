package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Proje {
    private int projeId;
    private String projeAdi;
    private LocalDate baslamaTarihi;
    private LocalDate bitisTarihi;
    private int ertelemeMiktari;
    private int olusturanKullaniciId;
    private List<Gorev> gorevler;

    // Parametresiz Constructor
    public Proje() {}

    // Parametreli Constructor
    public Proje(int projeId, String projeAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi) {
        this.projeId = projeId;
        this.projeAdi = projeAdi;
        this.baslamaTarihi = baslamaTarihi;
        this.bitisTarihi = bitisTarihi;
        /*this.ertelemeMiktari = ertelemeMiktari;
        this.gorevler = gorevler;*/
    }

    public Proje (String projeAdi, LocalDate baslamaTarihi, LocalDate bitisTarihi, int ertelemeMiktari){
        this.projeAdi = projeAdi;
        this.baslamaTarihi = baslamaTarihi;
        this.bitisTarihi = bitisTarihi;
        this.ertelemeMiktari = ertelemeMiktari;
    }



    // Getter ve Setter
    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public String getProjeAdi() {
        return projeAdi;
    }

    public void setProjeAdi(String projeAdi) {
        this.projeAdi = projeAdi;
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

    public int getOlusturanKullaniciId() {
        return olusturanKullaniciId;
    }

    public void setOlusturanKullaniciId(int olusturanKullaniciId) {
        this.olusturanKullaniciId = olusturanKullaniciId;
    }
}
