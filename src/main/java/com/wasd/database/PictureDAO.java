package com.wasd.database;

import java.sql.*;
import java.util.ArrayList;

public class PictureDAO {

    // Obtener imágenes de un juego específico
    public ArrayList<String> searchPictures(int idGame) {

        ArrayList<String> pictures = new ArrayList<>();
        String sql = "SELECT picture FROM PICTURES WHERE idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idGame);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pictures.add(rs.getString("picture"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pictures;
    }

    // Insertar imágenes para un juego
    public void insertPictures(int idGame, ArrayList<String> pictures) {
        String sql = "INSERT INTO PICTURES(idGame, picture, isMain) VALUES (?, ?, false)";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            for (String url : pictures) {
                stmt.setInt(1, idGame);
                stmt.setString(2, url);
                stmt.addBatch();
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
