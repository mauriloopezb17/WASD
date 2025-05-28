package com.wasd.database;

import com.wasd.database.LibraryDAO;
import com.wasd.models.Game;

import java.sql.*;
import java.util.ArrayList;

import com.wasd.database.ConnectionDB;

public class WishlistDAO {

    // Anadir un juego a la wishlist
    public boolean addWishlist(int idUser, int idGame) {

        if (inWishlist(idUser, idGame)){
            return false;
        }

        String sql = "INSERT INTO WISHLISTS(idPlayer, idGame) VALUES (?, ?)";

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

    // Verificacion si tienes ya el juego en tu wishlist
    public boolean inWishlist(int idUser, int idGame) {

        String sql = "SELECT 1 FROM WISHLISTS WHERE idPlayer = ? AND idGame = ?";
        
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

    // Consulta toda la wishlist de juegos que tienes
    public ArrayList<Game> searchAllWishlist(int idUser) {

        ArrayList<Game> wishlist = new ArrayList<>();
        String sql = "SELECT idGame FROM WISHLISTS WHERE idPlayer = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            GameDAO gameDAO = new GameDAO();

            while (rs.next()) {
                int idGame = rs.getInt("idGame");
                Game game = gameDAO.getGameXId(idGame);
                if (game != null){
                    wishlist.add(game);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishlist;
    }

    // Elimina un juego de la wishlist
    public boolean removeWishlist(int idUser, int idGame) {

        String sql = "DELETE FROM WISHLISTS WHERE idPlayer = ? AND idGame = ?";
        
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

    // Funcion para obtener todos los ids que estan en la wishlist
    public ArrayList<Integer> searchAllIdGames(int idUser) {
        
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT idGame FROM WISHLISTS WHERE idPlayer = ?";

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
