package com.wasd.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHashUtil {

    private int iterations;       
    private int memory;    
    private int parallelism;       

    private final Argon2 argon2;

    public PasswordHashUtil() {
        this.argon2 = Argon2Factory.create();
        this.iterations  = 2;   // iteraciones del algoritmo hash, mientras mas alto el valor, mas seguro, pero mas lento
        this.memory = 65536;    // memoria usada en la ram al momento de hacer el hash, mientras mas alto, mas seguro, pero mas lento. En este caso 64MB
        this.parallelism = 1;   // núcleos usados para hacer el hash, mientras mas alto, mas seguro, pero mas lento

    }

    public PasswordHashUtil(int iterations, int memory, int parallelism) {      // Si luego hacemos varias versiones para celular, servidores, etc. Con esto podremos darle un mayor requerimiento o menor
        this.argon2 = Argon2Factory.create();
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = parallelism;
    }

    public String hashPassword(String password) {

        char[] passwordChars = password.toCharArray();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña esta vacía xd");
        }

        try{
            return argon2.hash(iterations, memory, parallelism, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars); // Limpia el array de contraseñas para evitar que quede en memoria
        }
        
    }

    public boolean verifyPassword(String hash, String password) {
        
        char[] passwordChars = password.toCharArray();

        if (hash == null || password == null) {
            return false;
        }

        try{
            return argon2.verify(hash, passwordChars);
        } finally {
            argon2.wipeArray(passwordChars); // Limpia el array de contraseñas para evitar que quede en memoria
        }
        
    } 
}