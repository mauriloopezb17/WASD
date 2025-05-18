package com.wasd;

import java.util.ArrayList;

public class Player extends User{
    
    private int idPlayer;
    private ArrayList<Game> library;
    private ArrayList<Game> wishlist;
    private ArrayList<Game> cart;
    private ArrayList<Player> friends;
    private ArrayList<Player> requests;

    public Player(int idUser, int idGamer, String name, String lastName, String email, String country, String password, String avatar) {
        super(idUser, name, lastName, email, country, password, avatar);
        this.idPlayer = idGamer;
        this.library = new ArrayList<>();
        this.wishlist = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public int getIdPlayer() {
        return idPlayer;
    }
    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public ArrayList<Game> getLibrary() {
        return library;
    }
    public void setLibrary(ArrayList<Game> library) {
        this.library = library;
    }

    public ArrayList<Game> getWishlist() {
        return wishlist;
    }
    public void setWishlist(ArrayList<Game> wishlist) {
        this.wishlist = wishlist;
    }

    public ArrayList<Game> getCart() {
        return cart;
    }
    public void setCart(ArrayList<Game> cart) {
        this.cart = cart;
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<Player> friends) {
        this.friends = friends;
    }

    public ArrayList<Player> getRequests() {
        return requests;
    }
    public void setRequests(ArrayList<Player> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "Player:" +
                "\nidPlayer=" + idPlayer +
                "\nlibrary=" + library +
                "\nwishlist=" + wishlist +
                "\ncart=" + cart +
                "\nfriends=" + friends +
                "\nrequests=" + requests +
                super.toString();
    }

    @Override
    public void updatePassword(String newPassword) {

    }

    public void addWishlit(Game product){

    }

    public void removeWishlist(Game product){

    }

    public void buyGame(Game product){

    }

    public ArrayList<Game> purchaseHistory(){
        return null;
    }

    public boolean sendFriendRequest(Player player){
        return false;
    }

    public ArrayList<Player> getFriendRequest(){
        return null;
    }

    public void removeFriend(Player player){

    }

    public void askFriend(Player player){

    }

    public void acceptRequest(){

    }

    public void denyRequest(){

    }

}
