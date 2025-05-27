package com.wasd.models;
import java.io.*;

public class Rating implements Serializable {
    
    private int idRating;
    private String name;
    private String iconPath;
    private String abbreviation;

    public Rating() {
        this.name = "Rating Pending";
        this.iconPath = "/images/rating_default.png";
        this.abbreviation = "UR";
    }

    public Rating(int idRating, String name, String iconPath, String abbreviation) {
        this.idRating = idRating;
        this.name = name;
        this.iconPath = iconPath;
        this.abbreviation = abbreviation;
    }

    public int getIdRating() {
        return idRating;
    }
    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "Classification{" +
                "name='" + name +
                "\niconPath='" + iconPath +
                "\nabbreviation='" + abbreviation;
    }
}
