package org.example.service;

import org.example.dao.CalisanDAO;
import org.example.model.Calisan;

public class CalisanServisi {

    private CalisanDAO calisanDAO;

    public CalisanServisi(){
        this.calisanDAO = new CalisanDAO();
    }

   /* public boolean addCalisan(String adiSoyadi) {
        if(adiSoyadi.isEmpty()){
            return false;
        }
        Calisan yeniCalisan = new Calisan(adiSoyadi);
         return calisanDAO.saveEmployee(yeniCalisan);

    }*/
}
