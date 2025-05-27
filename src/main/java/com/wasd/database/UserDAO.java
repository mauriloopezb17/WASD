package com.wasd.database;


import com.wasd.database.ConnectionDB;
import com.wasd.database.PlayerDAO;
import com.wasd.database.PublisherDAO;
import com.wasd.database.AdminDAO;
import com.wasd.models.User;
import com.wasd.models.Player;
import com.wasd.models.Role;

import java.sql.*;

public class UserDAO {

    // Función "CREATE" para SQL y crear un nuevo registro usuario
    public boolean createUser(User user) {
        
        String sqlCountryCheck = "SELECT idCountry FROM countries WHERE countryName = ?";
        String sqlInsertCountry = "INSERT INTO countries(countryName) VALUES (?) RETURNING idCountry";
        String sqlInsertUser ="INSERT INTO users(name, lastName, userName, email, password, country, avatar, active, role, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionDB.connect()) {

            int idCountry = 0;

            try (PreparedStatement checkCountryStmt = con.prepareStatement(sqlCountryCheck)) {
                checkCountryStmt.setString(1, user.getCountry());
                ResultSet rs = checkCountryStmt.executeQuery();
                if (rs.next()) {
                    idCountry = rs.getInt("idCountry");
                }
            }

            if (idCountry == 0) {
                try (PreparedStatement insertCountryStmt = con.prepareStatement(sqlInsertCountry)) {
                    insertCountryStmt.setString(1, user.getCountry());
                    ResultSet rs = insertCountryStmt.executeQuery();
                    if (rs.next()) {
                        idCountry = rs.getInt("idCountry");
                    } else {
                        throw new SQLException("No se pudo obtener el idCountry recién insertado");
                    }
                }
            }

            // Insertar en USERS
            try (PreparedStatement insertUserStmt = con.prepareStatement(sqlInsertUser)) {
                insertUserStmt.setString(1, user.getName());
                insertUserStmt.setString(2, user.getLastName());
                insertUserStmt.setString(3, user.getUsername());
                insertUserStmt.setString(4, user.getEmail());
                insertUserStmt.setString(5, user.getPassword());
                insertUserStmt.setString(6, user.getAvatar());
                insertUserStmt.setBoolean(7, user.isActive());
                insertUserStmt.setString(8, user.getRole().name());
                insertUserStmt.setString(9, user.getDescription());
                insertUserStmt.setInt(10, idCountry);

                int rows = insertUserStmt.executeUpdate();

                if (rows == 0) {
                    throw new SQLException("No se insertó ningún usuario");
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // función "READ" para SQL
    public User searchUser(String input) {
        String sql;

        boolean isEmail = input.contains("@gmail") || input.contains("@hotmail") || input.contains("@outlook") || input.contains("@yahoo") || input.contains("@ucb");
        if (isEmail) {
            sql = "SELECT * FROM users WHERE email = ?";
        } else {
            sql = "SELECT * FROM users WHERE userName = ?";
        }

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                int idUser = rs.getInt("idUser");

                return switch (role) {
                    case "PLAYER" -> new PlayerDAO().searchPlayer(idUser);
                    case "PUBLISHER" -> new PublisherDAO().searchPublisher(idUser);
                    case "ADMIN" -> new AdminDAO().searchAdmin(idUser);
                    default -> throw new IllegalArgumentException("Invalid role: " + role);
                };
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // funcion para buscar si el correo usado ya existe en la base de datos
    public boolean repetitiveEmail(String email) {

        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // funcion para buscar si el nombre de usuario ya existe en la base de datos
    public boolean repetitiveUserName(String username) {
        
        String sql = "SELECT 1 FROM users WHERE userName = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
