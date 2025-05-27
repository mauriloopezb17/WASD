package com.wasd.database;

import com.wasd.models.Tag;
import java.sql.*;
import java.util.ArrayList;

public class TagDAO {

    // Obtiene un Tag por nombre o lo inserta si no existe
    public Tag searchTag(String tagName) {

        Tag tag = null;
        String selectSql = "SELECT idTag, nameTag FROM TAGS WHERE nameTag = ?";
        String insertSql = "INSERT INTO TAGS(nameTag) VALUES (?) RETURNING idTag";

        try (Connection con = ConnectionDB.connect()) {
            // Buscar tag existente
            try (PreparedStatement selectStmt = con.prepareStatement(selectSql)) {
                selectStmt.setString(1, tagName);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    return new Tag(rs.getInt("idTag"), rs.getString("nameTag"));
                }
            }

            // Insertar si no existe
            try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                insertStmt.setString(1, tagName);
                ResultSet rs = insertStmt.executeQuery();
                if (rs.next()) {
                    return new Tag(rs.getInt("idTag"), tagName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tag;
    }

    // Obtiene todos los tags asociados a un juego
    public ArrayList<Tag> getTags(int idGame) {
        
        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT t.idTag, t.nameTag FROM TAGS t JOIN GAME_TAGS gt ON t.idTag = gt.idTag WHERE gt.idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idGame);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(new Tag(rs.getInt("idTag"), rs.getString("nameTag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    // Funcion para obtener todas las eriquetas
    public ArrayList<Tag> searchAllTags() {

        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT idTag, nameTag FROM TAGS ORDER BY nameTag ASC";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idTag");
                String name = rs.getString("nameTag");
                tags.add(new Tag(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

}

