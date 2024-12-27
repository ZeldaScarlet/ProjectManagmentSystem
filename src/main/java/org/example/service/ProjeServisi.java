package org.example.service;

import org.example.dao.ProjectDAO;
import org.example.model.Proje;

import java.util.List;

public class ProjeServisi {

    private ProjectDAO projectDAO;

    public ProjeServisi(){
        this.projectDAO = new ProjectDAO();
    }

    public boolean projeEkle(Proje proje){

        return projectDAO.projeKaydet(proje);
    }

    public List<Proje> getAllProjects() {
        return projectDAO.getAllProjects();
    }

}
