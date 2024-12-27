package org.example.model;

public class Calisan {
    private int calisanId;
    private String adiSoyadi;

    // Constructor
    public Calisan(int calisanId, String adiSoyadi) {
        this.calisanId = calisanId;
        this.adiSoyadi = adiSoyadi;
    }

    // Getter ve Setter
    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }

    public String getAdiSoyadi() {
        return adiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        this.adiSoyadi = adiSoyadi;
    }
}

