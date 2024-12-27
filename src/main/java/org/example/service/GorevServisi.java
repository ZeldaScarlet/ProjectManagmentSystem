package org.example.service;

import org.example.dao.GorevDAO;
import org.example.model.Gorev;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GorevServisi {
    private GorevDAO gorevDAO;

    public GorevServisi(){
        this.gorevDAO = new GorevDAO();
    }

    public boolean gorevEkle(Gorev gorev){

        return gorevDAO.gorevKaydet(gorev);
    }

    public List<Gorev> getTasksByProjectId(int projectId) {
        List<Gorev> gorevler = new ArrayList<>();
        return gorevler;
    }
}
