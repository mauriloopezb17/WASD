package com.wasd.gui;
import com.wasd.models.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.concurrent.Flow;

public class ProfileWindow extends SecondaryWindow{

    public ProfileWindow(MainWindow frame, Player player) {
        super(player.getUsername(), frame, player);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout(10,5));
        wrapperPanel.setOpaque(false);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        wrapperPanel.setBackground(BG_COLOR);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout(10,10));
        upperPanel.setOpaque(false);
        //upperPanel.setPreferredSize(new Dimension(300, 100));
        int avatarSize = 100;

            PanelRound avatarPanel = new PanelRound();
            int ar = 15;
            avatarPanel.setRoundTopLeft(ar);
            avatarPanel.setRoundTopRight(ar);
            avatarPanel.setRoundBottomRight(ar);
            avatarPanel.setRoundBottomLeft(ar);
            avatarPanel.setOpaque(false);
            avatarPanel.setBackground(BG_COLOR);
            avatarPanel.setLayout(new BorderLayout());
            avatarPanel.setPreferredSize(new Dimension(avatarSize, avatarSize));

            JLabel avatarImage = new JLabel();
            avatarImage.setIcon(AssetLoader.loadIcon(player.getAvatar(), avatarSize, avatarSize));
            avatarPanel.add(avatarImage, BorderLayout.CENTER);
            upperPanel.add(avatarPanel, BorderLayout.WEST);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,5,40));

                PanelRound friendButton = new PanelRound();
                int br = 12;
                friendButton.setRoundTopLeft(br);
                friendButton.setRoundTopRight(br);
                friendButton.setRoundBottomRight(br);
                friendButton.setRoundBottomLeft(br);
                friendButton.setBorderColor(DETAILS_COLOR);
                friendButton.setBorderStroke(2);
                friendButton.setBackground(PANEL_COLOR);
                friendButton.setPreferredSize(new Dimension(avatarSize-15, 24));
                friendButton.setLayout(new BorderLayout());
                friendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                friendButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel friendButtonLabel = new JLabel();
                friendButtonLabel.setText("+ Add friend");
                friendButtonLabel.setFont(DESCRPTION_FONT);
                friendButtonLabel.setForeground(TEXT_COLOR);
                friendButtonLabel.setPreferredSize(new Dimension(avatarSize-15, 24));
                friendButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
                friendButtonLabel.setVerticalAlignment(SwingConstants.CENTER); 
                friendButton.add(friendButtonLabel, BorderLayout.CENTER);

                friendButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        friendButton.setBackground(TEXT_COLOR);
                        friendButtonLabel.setForeground(PANEL_COLOR);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        friendButton.setBackground(PANEL_COLOR);
                        friendButtonLabel.setForeground(TEXT_COLOR);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //TODO
                    }
                });
            buttonPanel.add(friendButton);

            upperPanel.add(buttonPanel, BorderLayout.EAST);

        wrapperPanel.add(upperPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel namesPanel = new JPanel();
            namesPanel.setOpaque(false);
            namesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            
            JLabel usernameLabel = new JLabel(player.getUsername());
            usernameLabel.setFont(SUBTITLE_FONT);
            usernameLabel.setForeground(TEXT_COLOR);
            namesPanel.add(usernameLabel);

            JLabel namePanel = new JLabel(player.getName() + " " + player.getLastName());
            namePanel.setFont(DESCRPTION_FONT);
            namePanel.setForeground(SECONDARY_TEXT_COLOR);
            namesPanel.add(namePanel);

            infoPanel.add(namesPanel, BorderLayout.NORTH);
            //infoPanel.add(usernameLabel, BorderLayout.NORTH);

            JTextArea descriptionTextArea = new JTextArea(player.getDescription());

            if (player.getDescription() == "" || player.getDescription() == null) {
                descriptionTextArea.setText("No description available.");
            }

            descriptionTextArea.setFont(DESCRPTION_FONT);
            descriptionTextArea.setForeground(TEXT_COLOR);
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            descriptionTextArea.setOpaque(false);
            descriptionTextArea.setEditable(false);
            descriptionTextArea.setBackground(PANEL_COLOR);
            descriptionTextArea.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            infoPanel.add(descriptionTextArea, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel gamesPanel = new JPanel();
            gamesPanel.setOpaque(false);
            gamesPanel.setLayout(new BorderLayout(5,5));
            {
                JLabel gamesLabel = new JLabel("Games");
                gamesLabel.setFont(SUBTITLE_FONT);
                gamesLabel.setForeground(TEXT_COLOR);
                gamesPanel.add(gamesLabel, BorderLayout.NORTH);

                JPanel gamesRow = new JPanel();
                gamesRow.setOpaque(false);
                gamesRow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));

                try{
                    int minGames = Math.min(3, player.getLibrary().size());
                    for(int i = 0; i < minGames; i++) {
                        gamesRow.add(new GameSmallContainer(player.getLibrary().get(i)));
                    }
                    if (player.getLibrary().size() == 0) {
                        //no games label
                        JLabel noGamesLabel = new JLabel("No games yet...");
                        noGamesLabel.setFont(DESCRPTION_FONT);
                        noGamesLabel.setForeground(TEXT_COLOR);
                        gamesRow.add(noGamesLabel);
                    }
                }
                catch(Exception NullPointerException){
                    System.out.println("Error al mostrar jugador");
                }
                gamesPanel.add(gamesRow, BorderLayout.CENTER);
            }
            
            JPanel lowerPanel = new JPanel();
            lowerPanel.setOpaque(false);
            lowerPanel.setLayout(new BorderLayout());
            lowerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            //friends container
            JPanel friendsPanel = new JPanel();
            friendsPanel.setOpaque(false);
            friendsPanel.setLayout(new BorderLayout(5,5));
            friendsPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
            {
                JLabel friendsLabel = new JLabel("Friends");
                friendsLabel.setFont(SUBTITLE_FONT);
                friendsLabel.setForeground(TEXT_COLOR);
                friendsPanel.add(friendsLabel, BorderLayout.NORTH);

                JPanel friendsContainer = new JPanel();
                friendsContainer.setOpaque(false);
                friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));
                try{
                    int minFriends = Math.min(5, player.getFriends().size());
                    for(int i = 0; i < minFriends; i++) {
                        friendsContainer.add(new ProfileSmallContainer(player.getFriends().get(i)));
                        //add gap
                        friendsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
                    }
                    if (player.getFriends().size() == 0) {
                        //no friends label
                        JLabel noFriendsLabel = new JLabel("No friends yet...");
                        noFriendsLabel.setFont(DESCRPTION_FONT);
                        noFriendsLabel.setForeground(TEXT_COLOR);
                        noFriendsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        friendsContainer.add(noFriendsLabel);
                    }
                }catch(Exception NullPointerException){
                    System.out.println("Error al mostrar amigos");
                }
                friendsPanel.add(friendsContainer, BorderLayout.CENTER);
            }
            lowerPanel.add(friendsPanel, BorderLayout.CENTER);

            //temporary bottom spacer
            JPanel spacer = new JPanel();
            spacer.setOpaque(false);
            spacer.setPreferredSize(new Dimension(0,200));
            //lowerPanel.add(spacer, BorderLayout.SOUTH);
            
            lowerPanel.add(gamesPanel, BorderLayout.NORTH);

        wrapperPanel.add(infoPanel, BorderLayout.CENTER);
        wrapperPanel.add(lowerPanel, BorderLayout.SOUTH);

        this.add(wrapperPanel, BorderLayout.CENTER);
        
        this.pack();
        this.setLocation(MouseInfo.getPointerInfo().getLocation());
        this.setVisible(true);
    }
    
}
