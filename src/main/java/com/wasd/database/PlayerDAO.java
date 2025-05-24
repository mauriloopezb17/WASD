package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Player;
import com.wasd.models.Role;

public class PlayerDAO {

    public Player searchPlayer(int idUser) {
        String sql = "SELECT u.*, p.* FROM USERS u JOIN PLAYERS p ON u.idUser = p.idUser WHERE u.idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Player player = new Player();

                // Datos desde la tabla USERS
                player.setIdUser(rs.getInt("idUser"));
                player.setName(rs.getString("name"));
                player.setLastName(rs.getString("lastName"));
                player.setUsername(rs.getString("userName"));
                player.setEmail(rs.getString("email"));
                player.setPassword(rs.getString("password"));
                player.setCountry(rs.getString("idCountry"));
                player.setAvatar(rs.getString("avatar"));
                player.setActive(rs.getBoolean("active"));
                player.setRole(Role.PLAYER); // Rol Player default al ser clase Player
                player.setDescription(rs.getString("description"));

                // Datos desde la tabla PLAYERS
                player.setIdPlayer(rs.getInt("idPlayer"));

                return player;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


}
