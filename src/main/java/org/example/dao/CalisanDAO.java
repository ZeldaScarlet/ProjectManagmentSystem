package org.example.dao;

import org.example.db.ConnectionManager;
import org.example.model.Calisan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalisanDAO {

    private ConnectionManager connectionManager;

    public CalisanDAO(){
        this.connectionManager = ConnectionManager.getInstance();
    }


}
