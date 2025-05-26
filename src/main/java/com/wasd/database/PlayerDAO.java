package com.wasd.database;

import java.sql.*;

import com.wasd.database.ConnectionDB;
import com.wasd.models.Player;
import com.wasd.models.Role;
import com.wasd.models.User;

public class PlayerDAO {

    // Funcion "Create" para SQL, pero para player
    public boolean createPlayer(Player player) {
        
        String sql ="INSERT INTO users(name, lastName, userName, email, password, avatar, active, role, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO players(idPlayer) VALUES (?)";
        String sql3 = "INSERT INTO COUNTRY(countryName) VALUES (?)";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql); 
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            PreparedStatement stmt3 = con.prepareStatement(sql2)){

            // Consulta para Users
            stmt.setString(1, player.getName());
            stmt.setString(2, player.getLastName());
            stmt.setString(3, player.getUsername());
            stmt.setString(4, player.getEmail());
            stmt.setString(5, player.getPassword());
            stmt.setString(7, player.getAvatar());
            stmt.setBoolean(8, player.isActive());
            stmt.setString(9, player.getRole().name());
            stmt.setString(10, player.getDescription());

            // Consulta para PLayers;
            stmt2.setInt(1, player.getIdPlayer());

            // Consulta para Country;
            stmt3.setString(1, player.getCountry());

            // Ejecutar la consulta
            int updatedUser = stmt.executeUpdate();
            int updatedPlayer = stmt2.executeUpdate();
            int updatedCountry = stmt3.executeUpdate();
            return updatedUser > 0 && updatedPlayer > 0 && updatedCountry > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del READ para SQL, pero busca al jugador desde su id y construye al objeto Player correspondiente
    public Player searchPlayer(int idUser) {
        
        String sql = "SELECT u.*, p.*, c.countryName FROM USERS u JOIN PLAYERS p ON u.idUser = p.idUser JOIN u.idCountry = c.idCountry WHERE u.idUser = ?";

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
                player.setCountry(rs.getString("countryName"));
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

    // Usa la logica del UPDATE para SQL, actualizando el registro en las tablas USERS y PLAYERS
    public boolean updatePlayer(Player player) {

        String sqlUser = "UPDATE USERS SET name = ?, lastName = ?, userName = ?, email = ?, password = ?, idCountry = ?, avatar = ?, description = ? WHERE idUser = ?";

        String sqlPlayer = "UPDATE PLAYERS SET ";       // en el caso que anadamos algun atributo propio a Player y tengamos que actualizarlo

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtUser = con.prepareStatement(sqlUser);
            PreparedStatement stmtPlayer = con.prepareStatement(sqlPlayer)) {

            // Actualizacion de los atributos de la tabla USERS
            stmtUser.setString(1, player.getName());
            stmtUser.setString(2, player.getLastName());
            stmtUser.setString(3, player.getUsername());
            stmtUser.setString(4, player.getEmail());
            stmtUser.setString(5, player.getPassword());
            stmtUser.setString(6, player.getCountry());
            stmtUser.setString(7, player.getAvatar());
            stmtUser.setString(8, player.getDescription());
            stmtUser.setInt(9, player.getIdUser());
            
            // Actualizacion de los atributos de la tabla PLAYERS
            // Como no hay ningun atributo propio que se pueda actualizar este campo lo dejo vacio por el momento

            // Ejecucion de la actualizacion tanto en USERS como en PLAYERS
            int updatedUser = stmtUser.executeUpdate();
            int updatedPlayer = stmtPlayer.executeUpdate();
            return updatedUser > 0 && updatedPlayer > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usa la logica del DELETE para SQL, eliminando el registro en las tablas PLAYERS y USERS (en este orden para evitar inconsistencias de FK)
    public boolean deletePlayer(int idUser) {

        String sqlDeletePlayer = "DELETE FROM PLAYERS WHERE idUser = ?";
        
        String sqlDeleteUser = "DELETE FROM USERS WHERE idUser = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmtPlayer = con.prepareStatement(sqlDeletePlayer);
            PreparedStatement stmtUser = con.prepareStatement(sqlDeleteUser)) {

            // Elimina el registro de la tabla PLAYERS
            stmtPlayer.setInt(1, idUser);
            int rowsAffectedPlayer = stmtPlayer.executeUpdate();

            // Elimina el registro de la tabla USERS
            stmtUser.setInt(1, idUser);

            // Ejecucion de la eliminacion
            int rowsAffectedUser = stmtUser.executeUpdate();

            return rowsAffectedPlayer > 0 && rowsAffectedUser > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
