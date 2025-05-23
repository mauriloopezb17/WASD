package com.wasd;

public class Admin extends User {

    private int idAdmin;

    public Admin(String name){
        super(name);
        this.idAdmin = -1;
    }

    public Admin(int idUser, int idAdmin, String name, String lastName, String username, String email, String country, String password, String avatar) {
        super(idUser, name, lastName, username, email, country, password, avatar, Role.ADMIN);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return "Admin: " +
                "\nidAdmin=" + idAdmin +
                super.toString();
    }

    @Override
    public void updatePassword(String newPassword) {

    }

    public void banUser(User user){

    }

    public void denyProduct(Game product){

    }

    public void acceptProduct(Game product){

    }

    public void deleteProduct(Game product){

    }

} 
