package com.wasd.database;

import com.wasd.models.Rating;
import java.sql.*;

public class RatingDAO {

    // Obtener rating por id
    public Rating searchRatingId(int idRating) {
        String sql = "SELECT * FROM RATINGS WHERE idRating = ?";
        Rating rating = null;

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idRating);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rating = new Rating(
                    rs.getInt("idRating"),
                    rs.getString("nameRating"),
                    rs.getString("iconPath"),
                    rs.getString("abbreviation")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

    // Obtener rating por nombre
    public Rating searchRatingName(String name) {
        String sql = "SELECT * FROM RATINGS WHERE nameRating = ?";
        Rating rating = null;

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rating = new Rating(
                    rs.getInt("idRating"),
                    rs.getString("nameRating"),
                    rs.getString("iconPath"),
                    rs.getString("abbreviation")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

}
