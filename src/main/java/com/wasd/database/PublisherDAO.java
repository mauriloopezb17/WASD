package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Publisher;
import com.wasd.models.Role;

public class PublisherDAO {

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

}
