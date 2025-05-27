package com.wasd.models;

import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;

public class Game implements Serializable {
    
    private int idGame;
    private String nameGame;
    private String banner;
    private ArrayList<String> pictures;
    private Publisher publisher;
    private double price;
    private int positiveReviews;
    private int negativeReviews;
    private String description;
    private ArrayList<String> tags;
    private double discount;
    private LocalDate releaseDate;
    private boolean recommended;
    private int status;
    
    // Requerimientos de cada plataforma y la clasificacion
    private Rating classification;
    private Windows windowsRequirement;
    private Linux linuxRequirement;
    private Mac macRequirement;

    public Game(String nameGame) {
        this.idGame = -1;
        this.nameGame = nameGame;
        //this.banner = "https://100.76.173.106:8443/resources/gameBanner_default.jpg";
        this.banner = "https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/header.jpg?t=1747409289";
        this.pictures = new ArrayList<>();
        //this.pictures.add("/images/gameThumbnail_default.jpg");
            this.pictures.add("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/ss_ba66d42e3d1ea78e5b085682484210b390eb9ccc.600x338.jpg?t=1747409289");
            this.pictures.add("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/ss_bbb5ead71b9321c6ba9bfc8941917136f9961fdb.600x338.jpg?t=1747409289");
            this.pictures.add("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/ss_3c894cfd44c1c12a539d2dbcb93cbecfbdbbb383.600x338.jpg?t=1747409289");
            this.pictures.add("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/ss_4b9b93f38fb65007e7a6410264b2f7fdac0d3586.600x338.jpg?t=1747409289");
            this.pictures.add("https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/3017860/ss_95930b9074962300c643468587ad4c7a1a64abe0.600x338.jpg?t=1747409289");
        this.publisher = new Publisher("Unknown Publisher");
        this.price = 59.99;
        this.positiveReviews = 94;
        this.negativeReviews = 6;
        this.description = "No description available.";
        this.tags = new ArrayList<>();

        this.tags.add("Action");
        this.tags.add("Adventure");
        this.tags.add("RPG");

        this.discount = 10;
        this.releaseDate = LocalDate.of(2025,5,14);
        this.recommended = true;
        this.status = 1;
        this.classification = new Rating();
        this.windowsRequirement = new Windows(); // assuming a default constructor exists
        this.linuxRequirement = new Linux();     // assuming a default constructor exists
        this.macRequirement = new Mac();         // assuming a default constructor exists
    }

    public Game(int idGame, String nameGame, String banner, ArrayList<String> pictures, Publisher publisher, double price, int positiveReviews, 
                int negativeReviews, String description, ArrayList<String> tags, double discount, LocalDate releaseDate, boolean recommended, int status, Rating classification, Windows windowsRequirement, 
                Linux linuxRequirement, Mac macRequirement) {
        this.idGame = idGame;
        this.nameGame = nameGame;
        this.banner = banner;
        this.pictures = pictures;
        this.publisher = publisher;
        this.price = price;
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
        this.macRequirement = macRequirement;
    }

    public int getIdGame() {
        return idGame;
    }
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getNameGame() {
        return nameGame;
    }
    public void setNameGame(String nameGame) {
        this.nameGame = nameGame;
    }

    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }
    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public Publisher getPublisher() {
        return publisher;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getReviews() {
        return this.positiveReviews + this.negativeReviews;
    }

    public int getPositiveReviews() {
        return positiveReviews;
    }
    public void setPositiveReviews(int positiveReviews) {
        this.positiveReviews = positiveReviews;
    }

    public int getNegativeReviews() {
        return negativeReviews;
    }
    public void setNegativeReviews(int negativeReviews) {
        this.negativeReviews = negativeReviews;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isRecommended() {
        return recommended;
    }
    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public Rating getClassification() {
        return classification;
    }
    public void setClassification(Rating classification) {
        this.classification = classification;
    }

    public Windows getWindowsRequirement() {
        return windowsRequirement;
    }
    public void setWindowsRequirement(Windows windowsRequirement) {
        this.windowsRequirement = windowsRequirement;
    }

    public Linux getLinuxRequirement() {
        return linuxRequirement;
    }
    public void setLinuxRequirement(Linux linuxRequirement) {
        this.linuxRequirement = linuxRequirement;
    }

    public Mac getMacRequirement() {
        return macRequirement;
    }
    public void setMacRequirement(Mac macRequirement) {
        this.macRequirement = macRequirement;
    }

    @Override
    public String toString() {
        return "Game:" +
                "\nidGame=" + idGame +
                "\nnameGame='" + nameGame +
                "\nbanner='" + banner +
                "\npictures=" + pictures +
                "\npublisher=" + publisher +
                "\nprice=" + price +
                "\npositiveReviews=" + positiveReviews +
                "\nnegativeReviews=" + negativeReviews +
                "\ndescription='" + description +
                "\ntags=" + tags +
                "\ndiscount=" + discount +
                "\nclassification=" + classification +
                "\nwindowsRequirement=" + windowsRequirement +
                "\nlinuxRequirement=" + linuxRequirement +
                "\nmacRequirement=" + macRequirement;
    }
}
