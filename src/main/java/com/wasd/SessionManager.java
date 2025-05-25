package com.wasd;

import com.wasd.models.User;
import com.wasd.models.Player;
import com.wasd.models.Publisher;
import com.wasd.models.Admin;

// Esta clase guarda la sesion del usuario hasta que se cierre la aplicacion o se cierre la sesion
public class SessionManager {

    /* Aclaración: Hasta donde sé este es el único lugar donde usaremos una variable estática. Si bien está mal
     * usarlos porque rompen con el encapsulamiento, con esto la POO, además puede afectar a fugas de algún que otro dato 
     * o dificultar pruebas unitarias; en este caso si está bien usarla porque el usuario debería guardarse desde que se hace el login
     * y las verificaciones hasta que se cierre la aplicación (se borra todo de la RAM) o se cierre la sesión. En otras palabras
     * usamos el static porque solo se tendrá una cuenta o usuario logueado a la vez, la aplicación debería poder llamarlo
     * en cualquier momento y además poder hacer un logout global de toda la aplicación.
     */

    private static User currentUser;
    private static Player currentPlayer;
    private static Publisher currentPublisher;
    private static Admin currentAdmin;

    // Función que obtiene al usuario actual
    public static User getCurrentUser() {
        return currentUser;
    }

    // Función para guarda la sesión iniciada del usuario y establecerla como la activa
    public static void loginSession(User user) {
        currentUser = user;
    }

    public static void loginSessionPlayer(Player player){
        currentPlayer = player;
    }

    public static void loginSessionPublisher(Publisher publisher){
        currentPublisher = publisher;
    }

    public static void loginSessionAdmin(Admin admin){
        currentAdmin = admin;
    }

    // Función que verifica si hay una sesión activa
    public static boolean activeSession() {
        return currentUser != null;
    }

    // Función que cierra la sesión del usuario, borrándolo de la memoria RAM
    public static void logOutSession() {
        currentUser = null;
    }

    // Función que verifica si el usuario tiene un rol específico (Player, Publisher o Admin)
    public static boolean consultRole(String role) {        // Esta función nos puede servir al momento de verificar si se debe abrir una ventana u otra dependiendo su rol
        return currentUser != null && currentUser.getRole().name().equalsIgnoreCase(role);
    }
}

