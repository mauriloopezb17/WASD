package com.wasd.database;

import com.wasd.database.ConnectionDB;

import java.sql.*;

public class ComponentDAO {

    // Busca un componente por nombre y tipo. Si no existe, lo inserta.
    public int searchComponent(String name, String type) {
        int id = -1;

        String selectSQL = "SELECT idComponent FROM COMPONENTS WHERE nameComponent = ? AND type = ?";
        String insertSQL = "INSERT INTO COMPONENTS(nameComponent, type) VALUES (?, ?) RETURNING idComponent";

        try (Connection con = ConnectionDB.connect()) {
            // Buscar primero
            try (PreparedStatement selectStmt = con.prepareStatement(selectSQL)) {
                selectStmt.setString(1, name);
                selectStmt.setString(2, type);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("idComponent");
                }
            }

            // Insertar si no existe
            try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, type);
                ResultSet rs = insertStmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("idComponent");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
