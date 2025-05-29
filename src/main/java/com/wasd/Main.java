package com.wasd;

<<<<<<< HEAD
import com.wasd.database.*;
import com.wasd.models.*;
import com.wasd.utils.*;

public class Main 
{
    public static void main(String[] args) {
=======
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;

import com.wasd.database.GameDAO;

import javax.swing.*;
import java.awt.*;


import com.wasd.gui.LoginWindow;
import com.wasd.gui.MainWindow;
import com.wasd.gui.Showcase;
import com.wasd.gui.MainShowcase;
import com.wasd.models.Game;
import com.wasd.models.Linux;
import com.wasd.models.Player;
import com.wasd.models.Publisher;
import com.wasd.models.Rating;
import com.wasd.models.Windows;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Testing WASD" );
        //ArrayList<Game> games = new ArrayList<>();
        /*
        games.add(new Game("Counter-Strike 2"));
        games.get(0).setPrice(0);
        games.add(new Game("Half-Life"));
        games.add(new Game("Portal"));
        games.add(new Game("Doom Eternal"));
        games.add(new Game("The Witcher 3"));
        */
        ArrayList<Game> recommendedGames = new ArrayList<>();
        /*
        recommendedGames.add(new Game("Counter-Strike 2"));
        recommendedGames.add(new Game("Half-Life"));
        recommendedGames.add(new Game("Portal"));
        */


        //new MainWindow(games, recommendedGames, player);


        //JUEGOS TEMPORALES
        /*this.idGame = idGame;
        this.nameGame = nameGame;
        this.banner = banner;
        this.pictures = pictures;
        this.publisher = publisher;
        this.price = price;
        this.reviews = reviews;
        this.positiveReviews = positiveReviews;
        this.negativeReviews = negativeReviews;
        this.description = description;
        this.tags = tags;
        this.discount = discount;
        this.releaseDate = releaseDate;
        this.recommended = recommended;
        this.status = status;
        this.classification = classification;
        this.windowsRequirement = windowsRequirement;
        this.linuxRequirement = linuxRequirement;
        this.macRequirement = macRequirement;*/

>>>>>>> 15f19cef0fee669c3c6ccfc44f4f20e51d8eea95
        
        PublisherDAO publisherDAO = new PublisherDAO();
        Publisher newPublisher = new Publisher("Riot Games");
        PasswordHashUtil passwordHashUtil = new PasswordHashUtil();

        newPublisher.setName("Brandon");
        newPublisher.setLastName("Beck");
        newPublisher.setUsername("Riot Games");
        newPublisher.setEmail("brandon.beck@gmail.com");
        newPublisher.setCountry("USA");
        newPublisher.setPassword(passwordHashUtil.hashPassword("Admin"));
        newPublisher.setAvatar("https://100.76.173.106:8443/resources/avatars/riot.jpg");
        newPublisher.setActive(true);
        newPublisher.setRole(Role.PUBLISHER);
        newPublisher.setDescription("CEO of Riot Games");

        boolean created = publisherDAO.createPublisher(newPublisher);
        if (created) {
            System.out.println("Publisher creado exitosamente con ID: " + newPublisher.getIdPublisher());
        } else {
            System.out.println("Error al crear el Publisher.");
        }
<<<<<<< HEAD
=======

        Game balatro = new Game("Balatro"); {
            balatro.setIdGame(1);
            balatro.setBanner("https://100.76.173.106:8443/resources/banners/balatro.jpg");
            ArrayList<String> balatro_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                balatro_promos.add("https://100.76.173.106:8443/resources/promos/balatro_" + i + ".jpg");
            }
            balatro.setPictures(balatro_promos);
            balatro.setPublisher(new Publisher("PlayStack"));
            balatro.setPrice(7.99);
            //balatro.setReviews(100);
            balatro.setPositiveReviews(95);
            balatro.setNegativeReviews(5);
            balatro.setDescription("The poker roguelike. Balatro is a hypnotically satisfying deckbuilder where you play illegal poker hands, discover game-changing jokers, and trigger adrenaline-pumping, outrageous combos.");
            ///balatro.setTags(new ArrayList<>(Arrays.asList("Cards", "Poker", "Roguelike", "Multiplayer", "Singleplayer")));
            balatro.setDiscount(10);
            balatro.setReleaseDate(LocalDate.of(2024,2,20));
            balatro.setRecommended(true);
            balatro.setStatus(1);
        }
        
        Game cp2077 = new Game("Cyberpunk 2077"); {
            cp2077.setIdGame(2);
            cp2077.setBanner("https://100.76.173.106:8443/resources/banners/cp2077.jpg");
            ArrayList<String> cp2077_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                cp2077_promos.add("https://100.76.173.106:8443/resources/promos/cp2077_" + i + ".jpg");
            }
            cp2077.setPictures(cp2077_promos);
            cp2077.setPublisher(new Publisher("CD Projekt Red"));
            cp2077.setPrice(59.99);
            //cp2077.setReviews(100);
            cp2077.setPositiveReviews(95);
            cp2077.setNegativeReviews(5);
            cp2077.setDescription("Cyberpunk 2077 is an open-world, action-adventure RPG set in the dark future of Night City — a dangerous megalopolis obsessed with power, glamor, and ceaseless body modification.");
            //cp2077.setTags(new ArrayList<>(Arrays.asList("Cyberpunk", "Role-Playing", "Open-World", "Singleplayer", "Sci-Fi")));
            cp2077.setDiscount(15);
            cp2077.setReleaseDate(LocalDate.of(2020,12,9));
            cp2077.setRecommended(true);
            cp2077.setStatus(1);
        }
        
        Game dtda = new Game("Doom: The Dark Ages"); {
            dtda.setIdGame(3);
            dtda.setBanner("https://100.76.173.106:8443/resources/banners/dtda.jpg");
            ArrayList<String> dtda_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                dtda_promos.add("https://100.76.173.106:8443/resources/promos/dtda_" + i + ".jpg");
            }
            dtda.setPictures(dtda_promos);
            dtda.setPublisher(new Publisher("Bethesda Softworks"));
            dtda.setPrice(49.99);
            //dtda.setReviews(100);
            dtda.setPositiveReviews(95);
            dtda.setNegativeReviews(5);
            dtda.setDescription("DOOM: The Dark Ages is the prequel to the critically acclaimed DOOM (2016) and DOOM Eternal that tells an epic cinematic story worthy of the DOOM Slayer’s legend. Players will step into the blood-stained boots of the DOOM Slayer, in this never-before-seen dark and sinister medieval war against");
            //dtda.setTags(new ArrayList<>(Arrays.asList("Action", "Demons", "FPS", "Sci-Fi", "Singleplayer")));
            dtda.setDiscount(0);
            dtda.setReleaseDate(LocalDate.of(2025,5,14));
            dtda.setRecommended(true);
            dtda.setStatus(1);
        }

        Game hk = new Game("Hollow Knight"); {
            hk.setIdGame(4);
            hk.setBanner("https://100.76.173.106:8443/resources/banners/hk.jpg");
            ArrayList<String> hk_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                hk_promos.add("https://100.76.173.106:8443/resources/promos/hk_" + i + ".jpg");
            }
            hk.setPictures(hk_promos);
            hk.setPublisher(new Publisher("Team Cherry"));
            hk.setPrice(4.99);
            //hk.setReviews(100);
            hk.setPositiveReviews(95);
            hk.setNegativeReviews(5);
            hk.setDescription("Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes. Explore twisting caverns, battle tainted creatures and befriend bizarre bugs, all in a classic, hand-drawn 2D style.");
            //hk.setTags(new ArrayList<>(Arrays.asList("Metdon", "Souls-like", "Platformer", "Singleplayer", "Diffucult")));
            hk.setDiscount(0); 
            hk.setReleaseDate(LocalDate.of(2017,2,24));
            hk.setRecommended(true);
        }

        Game hl2 = new Game("Half-Life 2"); {
            hl2.setIdGame(5);
            hl2.setBanner("https://100.76.173.106:8443/resources/banners/hl2.jpg");
            ArrayList<String> hl2_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                hl2_promos.add("https://100.76.173.106:8443/resources/promos/hl2_" + i + ".jpg");
            }
            hl2.setPictures(hl2_promos);
            hl2.setPublisher(new Publisher("Valve"));
            hl2.setPrice(5.79);
            //hl2.setReviews(100);
            hl2.setPositiveReviews(95);
            hl2.setNegativeReviews(5);
            hl2.setDescription("Reawakened from stasis in the occupied metropolis of City 17, Gordon Freeman is joined by Alyx Vance as he leads a desperate human resistance. Experience the landmark first-person shooter packed with immersive world-building, boundary-pushing physics, and exhilarating combat.");
            //hl2.setTags(new ArrayList<>(Arrays.asList("FPS", "Action", "Singleplayer", "Classic")));
            hl2.setDiscount(0);
            hl2.setReleaseDate(LocalDate.of(2004,11,16));
            hl2.setRecommended(true);
            hl2.setStatus(1);
        }
        
        Game R6S = new Game("Rainbow Six: Siege"); {
            R6S.setIdGame(6);
            R6S.setBanner("https://100.76.173.106:8443/resources/banners/R6S.jpg");
            ArrayList<String> R6S_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                R6S_promos.add("https://100.76.173.106:8443/resources/promos/R6S_" + i + ".jpg");
            }
            R6S.setPictures(R6S_promos);
            R6S.setPublisher(new Publisher("Ubisoft"));
            R6S.setPrice(15.99);
            //R6S.setReviews(100);
            R6S.setPositiveReviews(95);
            R6S.setNegativeReviews(5);
            R6S.setDescription("Tom Clancy's Rainbow Six® Siege is an elite, tactical team-based shooter where superior planning and execution triumph.");
            //R6S.setTags(new ArrayList<>(Arrays.asList("FPS", "Shooter", "Multiplayer", "Action", "Competitive")));
            R6S.setDiscount(15);
            R6S.setReleaseDate(LocalDate.of(2015,12,1));
            R6S.setRecommended(true);
            R6S.setStatus(1);
        }

        Game repo = new Game("R.E.P.O."); {
            repo.setIdGame(7);
            repo.setBanner("https://100.76.173.106:8443/resources/banners/repo.jpg");
            ArrayList<String> repo_promos = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                repo_promos.add("https://100.76.173.106:8443/resources/promos/repo_" + i + ".jpg");
            }
            repo.setPictures(repo_promos);
            repo.setPublisher(new Publisher("semiwork"));
            repo.setPrice(5.79);
            //repo.setReviews(100);
            repo.setPositiveReviews(95);
            repo.setNegativeReviews(5);
            repo.setDescription("An online co-op horror game with up to 6 players. Locate valuable, fully physics-based objects and handle them with care as you retrieve and extract to satisfy your creator's desires.");
            //repo.setTags(new ArrayList<>(Arrays.asList("Horror", "Co-Op", "Multiplayer", "Comedy", "3D")));
            repo.setDiscount(10);
            repo.setReleaseDate(LocalDate.of(2025,2,26));
            repo.setRecommended(true);
            repo.setStatus(1);
        }
        /*
        games.add(cs2);
        games.add(balatro);
        games.add(cp2077);
        games.add(dtda);
        games.add(hk);
        games.add(hl2);
        games.add(R6S);
        games.add(repo);

        recommendedGames.add(cs2);
        recommendedGames.add(dtda);
        recommendedGames.add(balatro);
        recommendedGames.add(cp2077);*/
        

        /*for(int i = 0; i < 5; i++) {
            games.add(new Game("DOOM: The Dark Ages"));
            recommendedGames.add(new Game("DOOM: The Dark Ages"));
        }*/

        //
        GameDAO gameDAO = new GameDAO();
        ArrayList<Game> games = gameDAO.searchAllGames();

        Player player = new Player("Pancake99");
        player.setLibrary(recommendedGames);
        
        ArrayList<Player> friends = new ArrayList<>();
        friends.add(new Player("CoBine17"));
        friends.add(new Player("Tommy999"));
        friends.add(new Player("SergioA2"));

        player.setFriends(friends);

        new LoginWindow(games, recommendedGames);
        //MainWindow mainWindow = new MainWindow(games, recommendedGames, player);
        //mainWindow.goGame(balatro);

        //temporary frame to test the showcase
        /*
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());
        myFrame.getContentPane().setBackground(new Color(0x181818));

        MainShowcase showcase = new MainShowcase(dtda);
        myFrame.add(showcase);
        myFrame.pack();
        myFrame.setVisible(true);
        */

>>>>>>> 15f19cef0fee669c3c6ccfc44f4f20e51d8eea95
    }
}
