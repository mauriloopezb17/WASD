package com.wasd.models;

import java.util.ArrayList;

public class Publisher extends User {
    
    private int idPublisher;
    private int publishedGames;
    private ArrayList<Game> gamesPublished;

    public Publisher(String name){
        super(name);
        this.idPublisher = -1;
        this.gamesPublished = new ArrayList<>();
    }

    public Publisher(int idUser, int idPublisher, String name, String lastName, String username, String email, String country, String password, String avatar) {
        super(idUser, name, lastName, username, email, country, password, avatar, Role.PUBLISHER);
        this.idPublisher = idPublisher;
        this.gamesPublished = new ArrayList<>();
    }

    public int getIdPublisher() {
        return idPublisher;
    }
    public void setIdPublisher(int idPublisher) {
        this.idPublisher = idPublisher;
    }

    public int getPublishedGames() {
        return publishedGames;
    }
    public void setPublishedGames(int publishedGames){
        this.publishedGames = publishedGames;
    }

    public ArrayList<Game> getGamesPublished() {
        return gamesPublished;
    }
    public void setGamesPublished(ArrayList<Game> gamesPublished) {
        this.gamesPublished = gamesPublished;
    }

    @Override
    public String toString() {
        return "Publisher:" +
                "\nidPublisher=" + idPublisher +
                "\npublishedGames=" + publishedGames +
                super.toString();
    }

    @Override
    public void updatePassword(String newPassword) {

    }

    public void publishProduct(Game product){

    }

    public void updateProduct(Game product){

    }

}
