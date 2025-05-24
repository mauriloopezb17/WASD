package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Admin;
import com.wasd.models.Role;

public class AdminDAO {

    public Admin searchAdmin(int idUser) {
        String sql = "SELECT u.*, a.* FROM USERS u JOIN ADMINS a ON u.idUser = a.idUser WHERE u.idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();

                // Datos desde la tabla USERS
                admin.setIdUser(rs.getInt("idUser"));
                admin.setName(rs.getString("name"));
                admin.setLastName(rs.getString("lastName"));
                admin.setUsername(rs.getString("userName"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setCountry(rs.getString("idCountry"));
                admin.setAvatar(rs.getString("avatar"));
                admin.setActive(rs.getBoolean("active"));
                admin.setRole(Role.PUBLISHER); // Rol PUBLISHER default al ser clase Publisher
                admin.setDescription(rs.getString("description"));

                // Datos desde la tabla ADMINS
                admin.setIdAdmin(rs.getInt("idAdmin"));

                return admin;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
