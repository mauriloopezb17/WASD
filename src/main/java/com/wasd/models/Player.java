package com.wasd.models;

import java.util.ArrayList;

public class Player extends User {

    private int idPlayer;
    private ArrayList<Game> library;
    private ArrayList<Game> wishlist;
    private ArrayList<Game> cart;
    private ArrayList<Player> friends;
    private ArrayList<Player> requests;

    public Player() {
        super();
    }

    public Player(String name) {
        super(name);
        this.idPlayer = -1;
        this.description = "Gaming Gaming Gaming :)"; // default description
        this.library = new ArrayList<>();
        this.wishlist = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public Player(int idUser, int idGamer, String name, String lastName, String username, String email, String country, 
                    String password, String avatar) {
        super(idUser, name, lastName, username, email, country, password, avatar, Role.PLAYER);
        this.idPlayer = idGamer;
        this.library = new ArrayList<>();
        this.wishlist = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                "\ndescription=" + description +
                "\nlibrary=" + library +
                "\nwishlist=" + wishlist +
                "\ncart=" + cart +
                "\nfriends=" + friends +
                "\nrequests=" + requests +
                "\n" + super.toString();
    }

    @Override
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void addWishlit(Game product) {
        if (!wishlist.contains(product)) {
            wishlist.add(product);
        }
    }

    public void removeWishlist(Game product) {
        wishlist.remove(product);
    }

    public void buyGame(Game product) {
        if (!library.contains(product)) {
            library.add(product);
        }
        cart.remove(product);
    }

    public ArrayList<Game> purchaseHistory() {
        return new ArrayList<>(library);
    }

    public boolean sendFriendRequest(Player player) {
        if (!player.requests.contains(this) && !player.friends.contains(this)) {
            player.requests.add(this);
            return true;
        }
        return false;
    }

    public ArrayList<Player> getFriendRequest() {
        return new ArrayList<>(requests);
    }

    public void removeFriend(Player player) {
        friends.remove(player);
        player.friends.remove(this);
    }

    public void askFriend(Player player) {
        sendFriendRequest(player);
    }

    public void acceptRequest() {
        for (Player requester : new ArrayList<>(requests)) {
            if (!friends.contains(requester)) {
                friends.add(requester);
                requester.friends.add(this);
            }
        }
        requests.clear();
    }

    public void denyRequest() {
        requests.clear();
    }
}
