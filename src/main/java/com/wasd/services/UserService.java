package com.wasd.services;

import com.wasd.utils.PasswordHashUtil;
import com.wasd.database.UserDAO;
import com.wasd.database.PlayerDAO;
import com.wasd.database.PublisherDAO;
import com.wasd.database.AdminDAO;
import com.wasd.models.User;
import com.wasd.models.Player;
import com.wasd.models.Admin;
import com.wasd.models.Publisher;

public class UserService {

    private final UserDAO dao = new UserDAO();
    private final PlayerDAO dao2 = new PlayerDAO();
    private final PublisherDAO dao3 = new PublisherDAO();
    private final AdminDAO dao4 = new AdminDAO();
    private final PasswordHashUtil passwordHashUtil = new PasswordHashUtil();

    // validacion para registrar un nuevo usuario
    public boolean registerUser(User user) {

        if (dao.repetitiveEmail(user.getEmail())) {
            System.out.println("El correo ya ha sido registrado");
            return false;
        }

        if (dao.repetitiveUserName(user.getUsername())) {
            System.out.println("El nombre de usuario ya ha sido registrado");
            return false;
        }

        // Encriptar la contrasena con el algoritmo Argon2
        String hashedPassword = passwordHashUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        // Si no se repite Email ni UserName, recien se crea el usuario
        return dao.createUser(user);
    }

    // validacion para iniciar sesion
    public User login(String input, String inputPassword) {
        // buscar al usuario
        User user = dao.searchUser(input);

        if (user == null) {
            System.out.println("Usuario no encontrado");
            return null;
        }

        // Validacion de la contrasena
        if (passwordHashUtil.verifyPassword(user.getPassword(), inputPassword)) {
            System.out.println("Login exitoso: " + user.getUsername());
            return user;
        } else {
            System.out.println("Incorrect password");
            return null;
        }
    }

    public Player loginPlayer(String input, String inputPassword) {

        User user = dao.searchUser(input);
        Player player = dao2.searchPlayer(user.getIdUser());

        // Validacion de la contrasena
        if (passwordHashUtil.verifyPassword(player.getPassword(), inputPassword)) {
            System.out.println("Login exitoso: " + user.getUsername());
            return player;
        } else {
            System.out.println("Incorrect password");
            return null;
        }
    }

    public Publisher loginPublisher(String input, String inputPassword) {

        User user = dao.searchUser(input);
        Publisher publisher = dao3.searchPublisher(user.getIdUser());

        // Validacion de la contrasena
        if (passwordHashUtil.verifyPassword(publisher.getPassword(), inputPassword)) {
            System.out.println("Login exitoso: " + user.getUsername());
            return publisher;
        } else {
            System.out.println("Incorrect password");
            return null;
        }
    }

    public Admin loginAdmin(String input, String inputPassword) {

        User user = dao.searchUser(input);
        Admin admin = dao4.searchAdmin(user.getIdUser());

        // Validacion de la contrasena
        if (passwordHashUtil.verifyPassword(admin.getPassword(), inputPassword)) {
            System.out.println("Login exitoso: " + user.getUsername());
            return admin;
        } else {
            System.out.println("Incorrect password");
            return null;
        }
    }

}