package com.wasd.services;

import com.wasd.utils.PasswordHashUtil;
import com.wasd.database.UserDAO;
import com.wasd.models.User;

public class UserService {

    private final UserDAO dao = new UserDAO();
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

}