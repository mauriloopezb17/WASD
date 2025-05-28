package com.wasd.services;

import com.wasd.database.WishlistDAO;
import com.wasd.database.LibraryDAO;
import java.util.ArrayList;

public class WishlistService {

    // Funcion para comprar un solo juego
    public boolean buyOne(int idUser, int idGame) {

        WishlistDAO wishlistDAO = new WishlistDAO();
        LibraryDAO libraryDAO = new LibraryDAO();

        boolean added = libraryDAO.addLibrary(idUser, idGame);
        if (added) {
            wishlistDAO.removeWishlist(idUser, idGame);
        }

        return added;
    }

    // Funcion para comprar todos los juegos, te devuelve la cantidad hecho efectivamente
    public int buyAll(int idUser) {

        LibraryDAO libraryDAO = new LibraryDAO();
        WishlistDAO wishlistDAO = new WishlistDAO();

        ArrayList<Integer> buyingAllGames = wishlistDAO.searchAllIdGames(idUser);

        int cont = 0;

        for (int idGame : buyingAllGames) {
            if (!libraryDAO.isOwned(idUser, idGame)) {
                if (libraryDAO.addLibrary(idUser, idGame)) {
                    wishlistDAO.removeWishlist(idUser, idGame);
                    cont++;
                }
            } else {
                wishlistDAO.removeWishlist(idUser, idGame); 
            }
        }

        return cont;
    }

}
