package com.wasd.database;

import com.wasd.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GameDAO {

    // Funcion para leer en SQL para GAMES solo id
    public Game getGameXId(int idGame) {

        Game game = null;

        String sql = "SELECT * FROM GAMES WHERE idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idGame);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Inicializamos el objeto
                game = new Game(rs.getString("nameGame"));

                game.setIdGame(idGame);
                game.setBanner(rs.getString("banner"));
                game.setPrice(rs.getDouble("price"));
                game.setDescription(rs.getString("description"));
                game.setDiscount(rs.getDouble("discount"));
                game.setPositiveReviews(rs.getInt("positiveReviews"));
                game.setNegativeReviews(rs.getInt("negativeReviews"));
                game.setRecommended(rs.getBoolean("recommended"));
                game.setStatus(rs.getInt("status"));
                game.setDeveloper(rs.getString("developer"));

                Date sqlDate = rs.getDate("relaseDate");
                if (sqlDate != null) {
                    game.setReleaseDate(sqlDate.toLocalDate());
                }

                // El rating
                RatingDAO ratingDAO = new RatingDAO();
                Rating rating = ratingDAO.searchRatingId(rs.getInt("idRating"));
                game.setClassification(rating);

                // El publisher
                PublisherDAO pubDAO = new PublisherDAO();
                Publisher publisher = pubDAO.searchPublisher(rs.getInt("idPublisher"));
                game.setPublisher(publisher);

                // TAAAAAAGS
                TagDAO tagDAO = new TagDAO();
                ArrayList<Tag> tags = tagDAO.getTags(idGame);
                game.setTags(tags);

                // El ArrayList de URLs para las imagenes
                PictureDAO picDAO = new PictureDAO();
                ArrayList<String> pictures = picDAO.searchPictures(idGame);
                game.setPictures(pictures);

                // La relacion de requerimiento
                RequirementDAO reqDAO = new RequirementDAO();
                ArrayList<Requirement> reqs = reqDAO.searchRequirement(idGame);

                for (Requirement r : reqs) {
                    if (r instanceof Windows w){
                        game.setWindowsRequirement(w);
                    }
                    else if (r instanceof Linux l){
                        game.setLinuxRequirement(l);
                    }
                    else if (r instanceof Mac m){
                        game.setMacRequirement(m);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    // leer para SQL pero por medio del nombre
    public Game getGameXName(String nameGame) {

        Game game = null;

        String sql = "SELECT * FROM GAMES WHERE nameGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nameGame);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                game = new Game(rs.getString("nameGame"));
                int idGame = rs.getInt("idGame");
                game.setIdGame(idGame);
                game.setBanner(rs.getString("banner"));
                game.setPrice(rs.getDouble("price"));
                game.setDescription(rs.getString("description"));
                game.setDiscount(rs.getDouble("discount"));
                game.setPositiveReviews(rs.getInt("positiveReviews"));
                game.setNegativeReviews(rs.getInt("negativeReviews"));
                game.setRecommended(rs.getBoolean("recommended"));
                game.setStatus(rs.getInt("status"));
                game.setDeveloper(rs.getString("developer"));

                Date sqlDate = rs.getDate("releaseDate");
                if (sqlDate != null) {
                    game.setReleaseDate(sqlDate.toLocalDate());
                }

                // El rating
                RatingDAO ratingDAO = new RatingDAO();
                Rating rating = ratingDAO.searchRatingId(rs.getInt("idRating"));
                game.setClassification(rating);

                // El publisher
                PublisherDAO pubDAO = new PublisherDAO();
                Publisher publisher = pubDAO.searchPublisher(rs.getInt("idPublisher"));
                game.setPublisher(publisher);

                // TAAAAAAGS
                TagDAO tagDAO = new TagDAO();
                ArrayList<Tag> tags = tagDAO.getTags(idGame);
                game.setTags(tags);

                // El ArrayList de URLs para las imagenes
                PictureDAO picDAO = new PictureDAO();
                ArrayList<String> pictures = picDAO.searchPictures(idGame);
                game.setPictures(pictures);

                // La relacion de requerimiento
                RequirementDAO reqDAO = new RequirementDAO();
                ArrayList<Requirement> reqs = reqDAO.searchRequirement(idGame);

                for (Requirement r : reqs) {
                    if (r instanceof Windows w){
                        game.setWindowsRequirement(w);
                    }
                    else if (r instanceof Linux l){
                        game.setLinuxRequirement(l);
                    }
                    else if (r instanceof Mac m){
                        game.setMacRequirement(m);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    // El Read o Insert eso, lo mismo par Game
    public boolean insertGame(Game game) {
        
        String sql = "INSERT INTO GAMES(nameGame, banner, price, description, discount, positiveReviews, negativeReviews, recommended, status, developer, releaseDate, idRating, idPublisher) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idGame";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            // Set de los atributos de Game
            stmt.setString(1, game.getNameGame());
            stmt.setString(2, game.getBanner());
            stmt.setDouble(3, game.getPrice());
            stmt.setString(4, game.getDescription());
            stmt.setDouble(5, game.getDiscount());
            stmt.setInt(6, game.getPositiveReviews());
            stmt.setInt(7, game.getNegativeReviews());
            stmt.setBoolean(8, game.isRecommended());
            stmt.setInt(9, game.getStatus());
            stmt.setString(10, game.getDeveloper());
            
            if (game.getReleaseDate() != null) {
                stmt.setDate(11, Date.valueOf(game.getReleaseDate()));
            } else {
                stmt.setNull(11, Types.DATE);
            }

            stmt.setInt(12, game.getClassification().getIdRating());
            stmt.setInt(13, game.getPublisher().getIdPublisher());

            // Ejecutar el insert y por medio del idGame insertamos lo demas en el resto de tablas
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGame = rs.getInt("idGame");
                game.setIdGame(idGame);

                PictureDAO pictureDAO = new PictureDAO();
                pictureDAO.insertPictures(idGame, game.getPictures());

                TagDAO tagDAO = new TagDAO();
                tagDAO.insertGame_Tags(idGame, game.getTags());
                RequirementDAO reqDAO = new RequirementDAO();
                if (game.getWindowsRequirement() != null) {
                    reqDAO.insertRequirements(idGame, game.getWindowsRequirement());
                }
                if (game.getLinuxRequirement() != null) {
                    reqDAO.insertRequirements(idGame, game.getLinuxRequirement());
                }
                if (game.getMacRequirement() != null) {
                    reqDAO.insertRequirements(idGame, game.getMacRequirement());
                }

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Funcion para actualizar el like de los juegos
    public boolean like(int idGame) {

        String sql = "UPDATE GAMES SET positiveReviews = positiveReviews + 1 WHERE idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idGame);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Funcion para actualizar el dislike de los juegos
    public boolean dislike(int idGame) {
        
        String sql = "UPDATE GAMES SET negativeReviews = negativeReviews + 1 WHERE idGame = ?";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idGame);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Arraylist con todos los juegos de la base de datos
    public ArrayList<Game> searchAllGames() {

        ArrayList<Game> games = new ArrayList<>();

        String sql = "SELECT idGame FROM GAMES";

        try (Connection con = ConnectionDB.connect();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idGame = rs.getInt("idGame");
                Game game = getGameXId(idGame);
                if (game != null) {
                    games.add(game);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

}

