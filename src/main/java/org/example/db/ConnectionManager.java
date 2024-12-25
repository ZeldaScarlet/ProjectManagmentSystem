package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    // Veritabanı bağlantısı için gerekli bilgiler
    private static final String URL = "jdbc:mysql://localhost:3306/Veritabanıadı";
    private static final String USER = "root";
    private static final String PASSWORD = "Parola";

    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
