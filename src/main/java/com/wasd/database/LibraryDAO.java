package com.wasd.database;

import com.wasd.models.Game;
import com.wasd.database.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;

public class LibraryDAO {

    // Agregar un juego en la librería, siempre y cuando no lo tenga ya
    public boolean addLibrary(int idUser, int idGame) {

        if (isOwned(idUser, idGame)) {
            return false;
        }

        String sql = "INSERT INTO LIBRARIES(idPlayer, idGame) VALUES (?, ?)";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idGame);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificacion si no tienes ya el juego
    public boolean isOwned(int idUser, int idGame) {

        String sql = "SELECT 1 FROM LIBRARIES WHERE idPlayer = ? AND idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idGame);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Consulta toda la libreria de juegos que tienes
    public ArrayList<Game> getLibraryByUser(int idUser) {
        ArrayList<Game> games = new ArrayList<>();
        String sql = "SELECT idGame FROM LIBRARIES WHERE idPlayer = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            GameDAO gameDAO = new GameDAO();

            while (rs.next()) {
                int idGame = rs.getInt("idGame");
                Game game = gameDAO.getGameXId(idGame);
                if (game != null) {
                    games.add(game);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    // Eliminar un juego de tu libreria
    public boolean removeLibrary(int idUser, int idGame) {

        String sql = "DELETE FROM LIBRARIES WHERE idPlayer = ? AND idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idGame);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cuantos juegos tienes en tu libreria
    public int countLibrary(int idUser) {

        String sql = "SELECT COUNT(*) FROM LIBRARIES WHERE idPlayer = ?";
        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ArrayList de idGame que almacena todos estos de un usuario
    public ArrayList<Integer> searchAllIdGames(int idUser) {

        ArrayList<Integer> ids = new ArrayList<>();

        String sql = "SELECT idGame FROM LIBRARIES WHERE idPlayer = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("idGame"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

}
