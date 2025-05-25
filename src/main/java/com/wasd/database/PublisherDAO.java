package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Publisher;
import com.wasd.models.Role;

public class PublisherDAO {

    // Usa la logica del READ para SQL, pero busca al publisher desde su id y construye al objeto Publiser correspondiente
    public Publisher searchPublisher(int idUser) {
        String sql = "SELECT u.*, p.* FROM USERS u JOIN PUBLISHERS p ON u.idUser = p.idUser WHERE u.idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Publisher publisher = new Publisher();

                // Datos desde la tabla USERS
                publisher.setIdUser(rs.getInt("idUser"));
                publisher.setName(rs.getString("name"));
                publisher.setLastName(rs.getString("lastName"));
                publisher.setUsername(rs.getString("userName"));
                publisher.setEmail(rs.getString("email"));
                publisher.setPassword(rs.getString("password"));
                publisher.setCountry(rs.getString("idCountry"));
                publisher.setAvatar(rs.getString("avatar"));
                publisher.setActive(rs.getBoolean("active"));
                publisher.setRole(Role.PUBLISHER); // Rol PUBLISHER default al ser clase Publisher
                publisher.setDescription(rs.getString("description"));

                // Datos desde la tabla PUBLISHERS
                publisher.setIdPublisher(rs.getInt("idPublisher"));
                publisher.setPublishedGames(null);


                return publisher;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    // Usa la logica del UPDATE para SQL, actualizando el registro en las tablas USERS y PUBLISHERS
    public boolean updatePublisher(Publisher publisher) {
        String sqlUser = "UPDATE USERS SET name = ?, lastName = ?, userName = ?, email = ?, password = ?, idCountry = ?, avatar = ?, description = ? WHERE idUser = ?";

        String sqlPublisher = "UPDATE PUBLISHERS SET ";       // en el caso que anadamos algun atributo propio a Player y tengamos que actualizarlo

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtUser = con.prepareStatement(sqlUser);
            PreparedStatement stmtPublisher = con.prepareStatement(sqlPublisher)) {

            // Actualizacion de los atributos de la tabla USERS
            stmtUser.setString(1, publisher.getName());
            stmtUser.setString(2, publisher.getLastName());
            stmtUser.setString(3, publisher.getUsername());
            stmtUser.setString(4, publisher.getEmail());
            stmtUser.setString(5, publisher.getPassword());
            stmtUser.setString(6, publisher.getCountry());
            stmtUser.setString(7, publisher.getAvatar());
            stmtUser.setString(8, publisher.getDescription());
            stmtUser.setInt(9, publisher.getIdUser());

            // Actualizacion de los atributos de la tabla PLAYERS
            // Como no hay ningun atributo propio que se pueda actualizar este campo lo dejo vacio por el momento

            // Ejecucion de la actualizacion tanto en USERS como en PLAYERS
            int updatedUser = stmtUser.executeUpdate();
            int updatedPublisher = stmtPublisher.executeUpdate();
            return updatedUser > 0 && updatedPublisher > 0;
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
