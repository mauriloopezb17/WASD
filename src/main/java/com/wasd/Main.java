package com.wasd;

import com.wasd.database.*;
import com.wasd.models.*;
import com.wasd.utils.*;

public class Main 
{
    public static void main(String[] args) {
        
        PublisherDAO publisherDAO = new PublisherDAO();
        Publisher newPublisher = new Publisher("Riot Games");
        PasswordHashUtil passwordHashUtil = new PasswordHashUtil();

        newPublisher.setName("Brandon");
        newPublisher.setLastName("Beck");
        newPublisher.setUsername("Riot Games");
        newPublisher.setEmail("brandon.beck@gmail.com");
        newPublisher.setCountry("USA");
        newPublisher.setPassword(passwordHashUtil.hashPassword("Admin"));
        newPublisher.setAvatar("https://100.76.173.106:8443/resources/avatars/riot.jpg");
        newPublisher.setActive(true);
        newPublisher.setRole(Role.PUBLISHER);
        newPublisher.setDescription("CEO of Riot Games");

        boolean created = publisherDAO.createPublisher(newPublisher);
        if (created) {
            System.out.println("Publisher creado exitosamente con ID: " + newPublisher.getIdPublisher());
        } else {
            System.out.println("Error al crear el Publisher.");
        }
    }
}
