package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Admin;
import com.wasd.models.Player;
import com.wasd.models.Role;

public class AdminDAO {

    // Funcion "Create" para SQL, pero para admin
    public boolean createPlayer(Admin admin) {
        
        String sql ="INSERT INTO users(name, lastName, userName, email, password, country, avatar, active, role, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO admins(idAdmin) VALUES (?)";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql); 
            PreparedStatement stmt2 = con.prepareStatement(sql2)){

            // Consulta para Users
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getUsername());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getPassword());
            stmt.setString(6, admin.getCountry());
            stmt.setString(7, admin.getAvatar());
            stmt.setBoolean(8, admin.isActive());
            stmt.setString(9, admin.getRole().name());
            stmt.setString(10, admin.getDescription());

            // Consulta para PLayers;
            stmt2.setInt(1, admin.getIdAdmin());

            // Ejecutar la consulta
            int updatedUser = stmt.executeUpdate();
            int updatedAdmin = stmt2.executeUpdate();
            return updatedUser > 0 && updatedAdmin > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del READ para SQL, pero busca al admin desde su id y construye al objeto Admin correspondiente
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

    // Usa la logica del UPDATE para SQL, actualizando el registro en las tablas USERS y ADMINS
    public boolean updatePublisher(Admin admin) {
        String sqlUser = "UPDATE USERS SET name = ?, lastName = ?, userName = ?, email = ?, password = ?, idCountry = ?, avatar = ?, description = ? WHERE idUser = ?";

        String sqlAdmin = "UPDATE ADMINS SET ";       // en el caso que anadamos algun atributo propio a Player y tengamos que actualizarlo

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtUser = con.prepareStatement(sqlUser);
            PreparedStatement stmtAdmin = con.prepareStatement(sqlAdmin)) {

            // Actualizacion de los atributos de la tabla USERS
            stmtUser.setString(1, admin.getName());
            stmtUser.setString(2, admin.getLastName());
            stmtUser.setString(3, admin.getUsername());
            stmtUser.setString(4, admin.getEmail());
            stmtUser.setString(5, admin.getPassword());
            stmtUser.setString(6, admin.getCountry());
            stmtUser.setString(7, admin.getAvatar());
            stmtUser.setString(8, admin.getDescription());
            stmtUser.setInt(9, admin.getIdUser());

            // Actualizacion de los atributos de la tabla PLAYERS
            // Como no hay ningun atributo propio que se pueda actualizar este campo lo dejo vacio por el momento

            // Ejecucion de la actualizacion tanto en USERS como en PLAYERS
            int updatedUser = stmtUser.executeUpdate();
            int updatedAdmin = stmtAdmin.executeUpdate();
            return updatedUser > 0 && updatedAdmin > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del DELETE para SQL, eliminando el registro en las tablas ADMINS y USERS (en este orden para evitar inconsistencias de FK)
    public boolean deleteAdmin(int idUser) {
        String sqlDeleteAdmin = "DELETE FROM PUBLISHERS WHERE idUser = ?";
        String sqlDeleteUser = "DELETE FROM USERS WHERE idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtAdmin = con.prepareStatement(sqlDeleteAdmin);
            PreparedStatement stmtUser = con.prepareStatement(sqlDeleteUser)) {

            // Elimina el registro de la tabla PUBLISHERS
            stmtAdmin.setInt(1, idUser);
            int rowsAffectedAdmin = stmtAdmin.executeUpdate();

            // Elimina el registro de la tabla USERS
            stmtUser.setInt(1, idUser);

            // Ejecucion de la eliminacion
            int rowsAffectedUser = stmtUser.executeUpdate();

            return rowsAffectedUser > 0 && rowsAffectedAdmin > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
