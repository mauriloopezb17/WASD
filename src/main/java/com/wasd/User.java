package com.wasd;
import java.io.*;

public abstract class User implements Serializable {

    protected int idUser;
    protected String name;
    protected String lastName;
    protected String email;
    protected String country;
    protected String password;
    protected String avatar;
    protected String username;
    protected boolean active;

    public User(String username) {
        this.idUser = -1;
        this.name = "Ale";
        this.lastName = "Bobarin :)";
        this.email = "unknown@email.com";
        this.country = "Unknown Country";
        this.password = "unknown";
        this.avatar = "/images/user_default.jpg";
        this.username = username;
        this.active = true;
    }

    public User(int idUser, String name, String lastName, String email, String country, String password, String avatar, String username) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.password = password;
        this.avatar = avatar;
        this.username = username;
        this.active = true;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User:" +
                "\nidUser=" + idUser +
                "\nname=" + name +
                "\nlast name=" + lastName +
                "\nemail=" + email +
                "\ncountry=" + country +
                "\npassword=" + password +
                "\navatar=" + avatar +
                "\nusername=" + username +
                "\nactive=" + active;
    }

    public void login(String emailOrUsername, String password) {
        // Implementation to be defined in subclasses or later
    }

    public void logOut() {
        // Implementation to be defined in subclasses or later
    }

    public void updateProfile(String name, String lastName, String email, String country, String avatar) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.avatar = avatar;
    }

    public abstract void updatePassword(String newPassword);
}
