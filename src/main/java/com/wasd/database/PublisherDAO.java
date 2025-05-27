package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Player;
import com.wasd.models.Publisher;
import com.wasd.models.Role;

public class PublisherDAO {

    // Funcion "Create" para SQL, pero para publisher
    public boolean createPublisher(Publisher publisher) {
        
        String sqlCheckCountry = "SELECT idCountry FROM countries WHERE countryName = ?";
        String sqlInsertCountry = "INSERT INTO countries(countryName) VALUES (?) RETURNING idCountry";
        String sqlInsertUser ="INSERT INTO users(name, lastName, userName, email, password, avatar, active, role, description, idCountry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idUser";
        String sqlInsertPublisher = "INSERT INTO publishers(idPublisher) VALUES (?) RETURNING idPublisher";

        try (Connection con = ConnectionDB.connect()){

            int idCountry = 0;

            // Verificacion si el pa[is] ya existe
            try (PreparedStatement checkCountryStmt = con.prepareStatement(sqlCheckCountry)){
                checkCountryStmt.setString(1, publisher.getCountry());
                ResultSet rs = checkCountryStmt.executeQuery();
                if (rs.next()) {
                    idCountry = rs.getInt("idCountry");
                }
            }

            // Si no existe, lo inserta
            if (idCountry == 0) {
                try (PreparedStatement insertCountryStmt = con.prepareStatement(sqlInsertCountry)){
                    insertCountryStmt.setString(1, publisher.getCountry());
                    ResultSet rs = insertCountryStmt.executeQuery();
                    if (rs.next()) {
                        idCountry = rs.getInt("idCountry");
                    }
                }
            }

            int idUser = 0;

            // Insertar en USERS
            try (PreparedStatement insertUserStmt = con.prepareStatement(sqlInsertUser)){
                insertUserStmt.setString(1, publisher.getName());
                insertUserStmt.setString(2, publisher.getLastName());
                insertUserStmt.setString(3, publisher.getUsername());
                insertUserStmt.setString(4, publisher.getEmail());
                insertUserStmt.setString(5, publisher.getPassword());
                insertUserStmt.setString(6, publisher.getAvatar());
                insertUserStmt.setBoolean(7, publisher.isActive());
                insertUserStmt.setString(8, publisher.getRole().name());
                insertUserStmt.setString(9, publisher.getDescription());
                insertUserStmt.setInt(10, idCountry);
                
                ResultSet rs = insertUserStmt.executeQuery();
                if (rs.next()) {
                    idUser = rs.getInt("idUser");
                } else {
                    throw new SQLException("No se puede obtener el idUser reci[en] insertado");
                }
            }

            try (PreparedStatement insertPublisherStmt = con.prepareStatement(sqlInsertPublisher)) {
                insertPublisherStmt.setInt(1, idUser);
                ResultSet rs = insertPublisherStmt.executeQuery();
                if (rs.next()) {
                    int idPublisher = rs.getInt("idPublisher");
                    publisher.setIdPublisher(idPublisher);
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Usa la logica del READ para SQL, pero busca al publisher desde su id y construye al objeto Publiser correspondiente
    public Publisher searchPublisher(int idUser) {
        String sql = "SELECT u.*, p.*, c.countryName FROM USERS u JOIN PUBLISHERS p ON u.idUser = p.idUser JOIN COUNTRIES c on u.idCountry = c.idCountry WHERE u.idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Publisher publisher = new Publisher(rs.getString("username"));

                // Datos desde la tabla USERS
                publisher.setIdUser(rs.getInt("idUser"));
                publisher.setName(rs.getString("name"));
                publisher.setLastName(rs.getString("lastName"));
                publisher.setEmail(rs.getString("email"));
                publisher.setPassword(rs.getString("password"));
                publisher.setCountry(rs.getString("countryName"));
                publisher.setAvatar(rs.getString("avatar"));
                publisher.setActive(rs.getBoolean("active"));
                publisher.setRole(Role.PUBLISHER); // Rol PUBLISHER default al ser clase Publisher
                publisher.setDescription(rs.getString("description"));

                // Datos desde la tabla PUBLISHERS
                publisher.setIdPublisher(rs.getInt("idPublisher"));

                return publisher;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    // Usa la logica del UPDATE para SQL, actualizando el registro en las tablas USERS y PUBLISHERS
    public boolean updatePublisher(Publisher publisher) {

        String sqlCountryCheck = "SELECT idCountry FROM countries WHERE countryName = ?";
        String sqlUpdateCountry = "UPDATE countries SET countryName = ? WHERE idCountry = ?";
        String sqlInsertCountry = "INSERT INTO countries(countryName) VALUES (?) RETURNING idCountry";
        String sqlUser = "UPDATE USERS SET name = ?, lastName = ?, userName = ?, email = ?, password = ?, idCountry = ?, avatar = ?, description = ? WHERE idUser = ?";
        
        //String sqlPublisher = "UPDATE PUBLISHERS SET ";       // en el caso que anadamos algun atributo propio a Player y tengamos que actualizarlo

        try (Connection con = ConnectionDB.connect()){

            int idCountry = 0;

            // Verificacion si el pais ya existe
            try(PreparedStatement checkCountryStmt = con.prepareStatement(sqlCountryCheck)) {
                checkCountryStmt.setString(1, publisher.getCountry());
                ResultSet rs = checkCountryStmt.executeQuery();
                if (rs.next()) {
                    idCountry = rs.getInt("idCountry");
                }
            }

            // Si no existe, lo inserta
            if (idCountry == 0) {
                try (PreparedStatement insertCountryStmt = con.prepareStatement(sqlInsertCountry)) {
                    insertCountryStmt.setString(1, publisher.getCountry());
                    ResultSet rs = insertCountryStmt.executeQuery();
                    if (rs.next()) {
                        idCountry = rs.getInt("idCountry");
                    } else {
                        throw new SQLException("No se pudo obtener el idCountry recien insertado");
                    }
                }
            } else {
                // Si existe, se actualiza el nombre del pais
                try (PreparedStatement updateCountryStmt = con.prepareStatement(sqlUpdateCountry)) {
                    updateCountryStmt.setString(1, publisher.getCountry());
                    updateCountryStmt.setInt(2, idCountry);
                    int rowsAffected = updateCountryStmt.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new SQLException("No se pudo actualizar el pais existente");
                    }
                }
            }

            // Actualizar los demas atributos en la tabla USERS y PUBLISHERS
            try (PreparedStatement stmtUser = con.prepareStatement(sqlUser);
                /*PreparedStatement stmtPlayer = con.prepareStatement(sqlPublisher)*/) {

                // Actualizacion de los atributos de la tabla USERS
                stmtUser.setString(1, publisher.getName());
                stmtUser.setString(2, publisher.getLastName());
                stmtUser.setString(3, publisher.getUsername());                    
                stmtUser.setString(4, publisher.getEmail());
                stmtUser.setString(5, publisher.getPassword());
                stmtUser.setInt(6, idCountry);
                stmtUser.setString(7, publisher.getAvatar());
                stmtUser.setString(8, publisher.getDescription());
                stmtUser.setInt(9, publisher.getIdUser());

                // Actualizacion de los atributos de la tabla PUBLISHERS
                // Como no hay ningun atributo propio que se pueda actualizar este campo lo dejo vacio por el momento

                // Ejecucion de la actualizacion tanto en USERS como en PUBLISHERS
                int updatedUser = stmtUser.executeUpdate();
                //int updatedPublisher = stmtPublisher.executeUpdate();
                return updatedUser > 0 /*&& updatedPublisher > 0*/;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del DELETE para SQL, eliminando el registro en las tablas PUBLISHERS y USERS (en este orden para evitar inconsistencias de FK)
    public boolean deletePublisher(int idUser) {
        String sqlDeletePublisher = "DELETE FROM PUBLISHERS WHERE idUser = ?";
        String sqlDeleteUser = "DELETE FROM USERS WHERE idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtPublisher = con.prepareStatement(sqlDeletePublisher);
            PreparedStatement stmtUser = con.prepareStatement(sqlDeleteUser)) {

            // Elimina el registro de la tabla PUBLISHERS
            stmtPublisher.setInt(1, idUser);
            int rowsAffectedPublisher = stmtPublisher.executeUpdate();

            // Elimina el registro de la tabla USERS
            stmtUser.setInt(1, idUser);

            // Ejecucion de la eliminacion
            int rowsAffectedUser = stmtUser.executeUpdate();

            return rowsAffectedUser > 0 && rowsAffectedPublisher > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
