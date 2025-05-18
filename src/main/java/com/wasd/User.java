package com.wasd;

public abstract class User {

    protected int idUser;
    protected String name;
    protected String lastName;
    protected String email;
    protected String country;
    protected String password;
    protected String avatar;
    protected boolean active;

    public User(int idUser, String name, String lastName, String email, String country, String password, String avatar) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.password = password;
        this.avatar = avatar;
        this.active = true; // por defecto el usuario estara activo
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
                "\nactive=" + active;
    }

    public void login(String emailOrUsername, String password){

    }

    public void logOut() {

    }

    public void updateProfile(String name, String lastName, String email, String country, String avatar){

    }

    /* la funcion actualizarContrasena sera una clase abstracta porque para algunos usuarios como admin podran
    cambiar la contrasena sin ninguna verificacion a diferencia de los usuarios jugadores y publishers
    */
    public abstract void updatePassword(String newPassword);
    
}
