package com.wasd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    
    private static final String URL = "jdbc:postgresql://100.76.173.106:5432/wasd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "ZTERRTHJ945";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args){
        try (Connection connection = connect()) {
            if (connection != null) {
                System.out.println("Conectado a la base de datos");
            } else {
                System.out.println("Fallo en la conexion xd");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
