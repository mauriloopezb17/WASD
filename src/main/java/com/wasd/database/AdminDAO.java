package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Admin;
import com.wasd.models.Player;
import com.wasd.models.Role;

public class AdminDAO {

    // Funcion "Create" para SQL, pero para admin
    public boolean createPlayer(Admin admin) {
        
        String sqlCountryCheck = "SELECT idCountry FROM countries WHERE countryName = ?";
        String sqlInsertCountry = "INSERT INTO countries(countryName) VALUES (?) RETURNING idCountry";
        String sqlInsertUser = "INSERT INTO users(name, lastName, userName, email, password, avatar, active, role, description, idCountry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idUser";
        String sqlInsertAdmin = "INSERT INTO admins(idUser) VALUES (?) RETURNING idAdmin";

        try (Connection con = ConnectionDB.connect()) {

            int idCountry = 0;

            // Verificacion si el país ya existe
            try (PreparedStatement checkCountryStmt = con.prepareStatement(sqlCountryCheck)) {
                checkCountryStmt.setString(1, admin.getCountry());
                ResultSet rs = checkCountryStmt.executeQuery();
                if (rs.next()) {
                    idCountry = rs.getInt("idCountry");
                }
            }

            // Si no existe, se inserta
            if (idCountry == 0) {
                try (PreparedStatement insertCountryStmt = con.prepareStatement(sqlInsertCountry)) {
                    insertCountryStmt.setString(1, admin.getCountry());
                    ResultSet rs = insertCountryStmt.executeQuery();
                    if (rs.next()) {
                        idCountry = rs.getInt("idCountry");
                    } else {
                        throw new SQLException("No se pudo obtener el idCountry recién insertado");
                    }
                }
            }

            int idUser = 0;

            // Insertar en USERS
            try (PreparedStatement insertUserStmt = con.prepareStatement(sqlInsertUser)) {
                insertUserStmt.setString(1, admin.getName());
                insertUserStmt.setString(2, admin.getLastName());
                insertUserStmt.setString(3, admin.getUsername());
                insertUserStmt.setString(4, admin.getEmail());
                insertUserStmt.setString(5, admin.getPassword());
                insertUserStmt.setString(6, admin.getAvatar());
                insertUserStmt.setBoolean(7, admin.isActive());
                insertUserStmt.setString(8, admin.getRole().name());
                insertUserStmt.setString(9, admin.getDescription());
                insertUserStmt.setInt(10, idCountry);
                ResultSet rs = insertUserStmt.executeQuery();
                if (rs.next()) {
                    idUser = rs.getInt("idUser");
                } else {
                    throw new SQLException("No se pudo obtener el idUser recién insertado");
                }
            }

            // Insertar en PLAYERS
            try (PreparedStatement insertAdminStmt = con.prepareStatement(sqlInsertAdmin)) {
                insertAdminStmt.setInt(1, idUser);
                ResultSet rs = insertAdminStmt.executeQuery();
                if (rs.next()) {
                    int idAdmin = rs.getInt("idAdmin");
                    admin.setIdAdmin(idAdmin); // Guardar el ID en el objeto
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del READ para SQL, pero busca al admin desde su id y construye al objeto Admin correspondiente
    public Admin searchAdmin(int idUser) {
        String sql = "SELECT u.*, a.*, c.countryName FROM USERS u JOIN Admin a ON u.idUser = a.idUser JOIN COUNTRIES c ON u.idCountry = c.idCountry WHERE u.idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin(rs.getString("userName"));

                // Datos desde la tabla USERS
                admin.setIdUser(rs.getInt("idUser"));
                admin.setName(rs.getString("name"));
                admin.setLastName(rs.getString("lastName"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setCountry(rs.getString("idCountry"));
                admin.setAvatar(rs.getString("avatar"));
                admin.setActive(rs.getBoolean("active"));
                admin.setRole(Role.PUBLISHER); // Rol Admin default al ser clase Publisher
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
    public boolean updateAdmin(Admin admin) {

        String sqlCountryCheck = "SELECT idCountry FROM countries WHERE countryName = ?";
        String sqlUpdateCountry = "UPDATE countries SET countryName = ? WHERE idCountry = ?";
        String sqlInsertCountry = "INSERT INTO countries(countryName) VALUES (?) RETURNING idCountry";
        String sqlUser = "UPDATE USERS SET name = ?, lastName = ?, userName = ?, email = ?, password = ?, idCountry = ?, avatar = ?, description = ? WHERE idUser = ?";

        //String sqlAdmin = "UPDATE ADMINS SET ";       // en el caso que anadamos algun atributo propio a Admin y tengamos que actualizarlo

        try (Connection con = ConnectionDB.connect()) {

            int idCountry = 0;

            // Verificacion si el país ya existe
            try (PreparedStatement checkCountryStmt = con.prepareStatement(sqlCountryCheck)) {
                checkCountryStmt.setString(1, admin.getCountry());
                ResultSet rs = checkCountryStmt.executeQuery();
                if (rs.next()) {
                    idCountry = rs.getInt("idCountry");
                }
            }

            // Si no existe, se inserta
            if (idCountry == 0) {
                try (PreparedStatement insertCountryStmt = con.prepareStatement(sqlInsertCountry)) {
                    insertCountryStmt.setString(1, admin.getCountry());
                    ResultSet rs = insertCountryStmt.executeQuery();
                    if (rs.next()) {
                        idCountry = rs.getInt("idCountry");
                    } else {
                        throw new SQLException("No se pudo obtener el idCountry recién insertado");
                    }
                }
            }
            else {
                // Si ya existe, se actualiza
                try (PreparedStatement updateCountryStmt = con.prepareStatement(sqlUpdateCountry)) {
                    updateCountryStmt.setString(1, admin.getCountry());
                    updateCountryStmt.setInt(2, idCountry);
                    int rowsAffected = updateCountryStmt.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new SQLException("No se pudo actualizar el país existente");
                    }
                }
            }

            // Actualizar los demas atributos en la tabla USERS y ADMINS
            try (PreparedStatement stmtUser = con.prepareStatement(sqlUser);
                    /*PreparedStatement stmtAdmin = con.prepareStatement(sqlAdmin)*/) {

                    // Actualizacion de los atributos de la tabla USERS
                    stmtUser.setString(1, admin.getName());
                    stmtUser.setString(2, admin.getLastName());
                    stmtUser.setString(3, admin.getUsername());
                    stmtUser.setString(4, admin.getEmail());
                    stmtUser.setString(5, admin.getPassword());
                    stmtUser.setInt(6, idCountry);
                    stmtUser.setString(7, admin.getAvatar());
                    stmtUser.setString(8, admin.getDescription());
                    stmtUser.setInt(9, admin.getIdUser());

                    // Actualizacion de los atributos de la tabla ADMINS
                    // Como no hay ningun atributo propio que se pueda actualizar este campo lo dejo vacio por el momento

                    // Ejecucion de la actualizacion tanto en USERS como en ADMINS
                    int updatedUser = stmtUser.executeUpdate();
                    //int updatedAdmin = stmtPlayer.executeUpdate();
                    return updatedUser > 0 /*&& updatedAdmin > 0*/;
                } 
                
        }catch (SQLException e) {
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
