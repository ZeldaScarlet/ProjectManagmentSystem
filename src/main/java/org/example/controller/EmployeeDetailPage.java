package org.example.controller;

import org.example.model.Proje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDetailPage extends JPanel {

    public EmployeeDetailPage(JPanel previousFrame , int id) {

        int userId = id;

       String[] projeColumnNames = {"Proje Adı", "Durum"};
        DefaultTableModel projeTableModel = new DefaultTableModel(projeColumnNames, 0);
        JTable projeTable = new JTable(projeTableModel);
        JScrollPane projeScrollPane = new JScrollPane(projeTable);


    }
}
