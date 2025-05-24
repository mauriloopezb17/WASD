package com.wasd.models;

import java.util.ArrayList;

public class Publisher extends User {
    
    private int idPublisher;
    private ArrayList<Game> publishedGames;
    
    public Publisher(String name){
        super(name);
        this.idPublisher = -1;
        this.publishedGames = new ArrayList<>();
    }

    public Publisher(int idUser, int idPublisher, String name, String lastName, String username, String email, String country, String password, String avatar) {
        super(idUser, name, lastName, username, email, country, password, avatar, Role.PUBLISHER);
        this.idPublisher = idPublisher;
        this.publishedGames = new ArrayList<>();
    }

    public int getIdPublisher() {
        return idPublisher;
    }
    public void setIdPublisher(int idPublisher) {
        this.idPublisher = idPublisher;
    }

    public ArrayList<Game> getPublishedGames() {
        return publishedGames;
    }
    public void setPublishedGames(ArrayList<Game> publishedGames) {
        this.publishedGames = publishedGames;
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
