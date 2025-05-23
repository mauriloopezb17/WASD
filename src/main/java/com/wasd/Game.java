package com.wasd;

import java.util.ArrayList;
import java.io.*;

public class Game implements Serializable {
    
    private int idGame;
    private String nameGame;
    private String banner;
    private ArrayList<String> pictures;
    private Publisher publisher;
    private double price;
    private int reviews;
    private int positiveReviews;
    private int negativeReviews;
    private String description;
    private ArrayList<String> tags;
    private int discount;
    
    // Requerimientos de cada plataforma y la clasificacion
    private Rating classification;
    private Windows windowsRequirement;
    private Linux linuxRequirement;
    private Mac macRequirement;

    public Game(String nameGame) {
        this.idGame = -1;
        this.nameGame = nameGame;
        this.banner = "/images/gameBanner_default.jpg";
        this.pictures = new ArrayList<>();
        this.pictures.add("/images/gameThumbnail_default.jpg");
        this.publisher = new Publisher("Unknown Publisher");
        this.price = 59.99;
        this.reviews = 0;
        this.positiveReviews = 0;
        this.negativeReviews = 0;
        this.description = "No description available.";
        this.tags = new ArrayList<>();

        this.tags.add("Action");
        this.tags.add("Adventure");
        this.tags.add("RPG");

        this.discount = 10;
        this.classification = new Rating();
        this.windowsRequirement = new Windows(); // assuming a default constructor exists
        this.linuxRequirement = new Linux();     // assuming a default constructor exists
        this.macRequirement = new Mac();         // assuming a default constructor exists
    }

    public Game(int idGame, String nameGame, String banner, ArrayList<String> pictures, Publisher publisher, double price, int reviews, int positiveReviews, 
                int negativeReviews, String description, ArrayList<String> tags, int discount, Rating classification, Windows windowsRequirement, 
                Linux linuxRequirement, Mac macRequirement) {
        this.idGame = idGame;
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
        return reviews;
    }
    public void setReviews(int reviews) {
        this.reviews = reviews;
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

    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
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
                "\nreviews=" + reviews +
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
