package org.example.service;

import org.example.dao.CalisanDAO;
import org.example.model.Calisan;
import org.example.model.Proje;

import java.util.List;

public class CalisanServisi {

    private CalisanDAO calisanDAO;

    public CalisanServisi(){
        this.calisanDAO = new CalisanDAO();
    }


    public List<Calisan> getAllEmployees() {
        return calisanDAO.getAllEmployees();
    }

    public List<Proje> getProjectsByEmployeeId(int calisanId) {
        return calisanDAO.getProjectsByEmployeeId(calisanId);
    }

    public void addEmployee(Calisan calisan) {
        calisanDAO.addEmployee(calisan);
    }

   /* public boolean addCalisan(String adiSoyadi) {
        if(adiSoyadi.isEmpty()){
            return false;
        }
        Calisan yeniCalisan = new Calisan(adiSoyadi);
         return calisanDAO.saveEmployee(yeniCalisan);

    }*/
}
