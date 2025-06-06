package com.wasd.models;
import java.io.*;

public abstract class User implements Serializable {

    protected int idUser;
    protected String name;
    protected String lastName;
    protected String username;
    protected String email;
    protected String country;
    protected String password;
    protected String avatar;
    protected boolean active;
    protected Role role;
    protected String description;

    public User() {
        
    }

    public User(String username) {
        this.idUser = -1;
        this.name = "Ale";
        this.lastName = "Bobarin :)";
        this.username = username;
        this.email = "unknown@email.com";
        this.country = "Unknown Country";
        this.password = "unknown";
        this.avatar = "https://100.76.173.106:8443/resources/default.jpg";
        this.active = true;
        this.description = "No description provided";
    }

    public User(int idUser, String name, String lastName, String username, String email, String country, String password, 
                String avatar, Role role ) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.country = country;
        this.password = password;
        this.avatar = avatar;
        this.active = true; // por defecto el usuario estara activo
        this.role = role;
        this.description = "No description provided";
    }

    // constructor based on registerWindow
    public User(String email, String username, String password, String name, String lastName, String country, Role role) {
        this.idUser = -1;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.country = country;
        this.password = password;
        this.avatar = "https://100.76.173.106:8443/resources/default.jpg";
        this.active = true; // por defecto el usuario estara activo
        this.role = role;
        this.description = "No description provided";
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User:" +
                "\nidUser=" + idUser +
                "\nname=" + name +
                "\nlast name=" + lastName +
                "\nusername=" + username +
                "\nemail=" + email +
                "\ncountry=" + country +
                "\npassword=" + password +
                "\navatar=" + avatar +
                "\nactive=" + active +
                "\nrol=" + role;
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
