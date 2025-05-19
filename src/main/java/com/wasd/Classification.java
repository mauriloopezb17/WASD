package com.wasd;
import java.io.*;

public class Classification implements Serializable {
    
    private String name;
    private String iconPath;
    private String abbreviation;

    public Classification() {
        this.name = "Rating Pending";
        this.iconPath = "/images/rating_default.png";
        this.abbreviation = "UR";
    }

    public Classification(String name, String iconPath, String abbreviation) {
        this.name = name;
        this.iconPath = iconPath;
        this.abbreviation = abbreviation;
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
