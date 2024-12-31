package org.example.model;

public class Calisan {
    private int calisanId;
    private String calisanAdiSoyadi;

    // Constructor
    public Calisan(int calisanId, String calisanAdiSoyadi) {
        this.calisanId = calisanId;
        this.calisanAdiSoyadi = calisanAdiSoyadi;
    }

    // Getter ve Setter
    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }

    public String getCalisanAdiSoyadi() {
        return calisanAdiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        this.calisanAdiSoyadi = adiSoyadi;
    }

    public String toString(){
        return calisanAdiSoyadi;
    }
}

