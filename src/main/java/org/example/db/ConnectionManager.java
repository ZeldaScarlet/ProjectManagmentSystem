package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    // Veritabanı bağlantısı için gerekli bilgiler
    private static final String URL = "jdbc:mysql://localhost:3306/VeryLast";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Statik bir bağlantı değişkeni
    private static Connection connection;

    private ConnectionManager() {
        // Bu sınıfın örneği oluşturulamaz
    }

    // Bağlantı almak için statik yöntem
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Bağlantıyı kapatmak için statik yöntem
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
