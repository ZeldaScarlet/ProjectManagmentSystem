package org.example.service;

import org.example.dao.GorevDAO;
import org.example.dao.ProjectDAO;
import org.example.model.Gorev;
import org.example.model.Proje;

import java.util.List;

public class ProjeServisi {

    private ProjectDAO projectDAO;
    GorevDAO gorevDAO= new GorevDAO();

    public ProjeServisi(){
        this.projectDAO = new ProjectDAO();
    }

    public boolean projeEkle(Proje proje){

        return projectDAO.projeKaydet(proje);
    }

    public List<Proje> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public List<Gorev> getAllProjectStuff(int id) {
        return gorevDAO.getTasksByProjectId(id);
    }

}
