package com.wasd.gui;

import com.wasd.models.*;
import com.wasd.database.*;
import com.wasd.services.WishlistService;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.LineBorder;

import com.wasd.database.*;

public class WishlistWindow extends SecondaryWindow implements StyleConfig {

    MainWindow mainWindow;

    public WishlistWindow(Player player, MainWindow mainWindow) {
        super("Wishlist");
        this.mainWindow = mainWindow;

        JPanel gamesAddedToWishlistContainer = new JPanel();

        //container
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //top row container (label, buy all button)
            JPanel topRowContainer = new JPanel();
            topRowContainer.setOpaque(false);
            topRowContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            topRowContainer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            topRowContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

                //label
                JLabel label = new JLabel("WISHLIST");
                label.setOpaque(false);
                label.setFont(SUBTITLE_FONT);
                label.setForeground(TEXT_COLOR);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                topRowContainer.add(label);

                topRowContainer.add(Box.createHorizontalStrut(370));

                //button
                PanelRound button = new PanelRound();
                int br = 5;
                button.setRoundTopLeft(br);
                button.setRoundTopRight(br);
                button.setRoundBottomRight(br);
                button.setRoundBottomLeft(br);

                button.setBackground(PANEL_COLOR);
                button.setPreferredSize(new Dimension(150, 30));
                button.setLayout(new BorderLayout());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setBorder(null);
                button.setOpaque(false);

                    JLabel buttonLabel = new JLabel("BUY ALL");
                    buttonLabel.setFont(SUBTITLE2_FONT);
                    buttonLabel.setForeground(TEXT_COLOR);
                    buttonLabel.setPreferredSize(new Dimension(150, 30));
                    buttonLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    buttonLabel.setVerticalAlignment(SwingConstants.CENTER); 
                    button.add(buttonLabel, BorderLayout.CENTER);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(TEXT_COLOR);
                        buttonLabel.setForeground(PANEL_COLOR);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(PANEL_COLOR);
                        buttonLabel.setForeground(TEXT_COLOR);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(player.getWishlist().size() != 0){
                            System.out.println("Buy all");
                            gamesAddedToWishlistContainer.removeAll();

                            for(Game g : player.getWishlist()) {
                                player.getLibrary().add(g);
                            }

                            WishlistService wishlistService = new WishlistService();

                            JLabel boughtLabel = new JLabel("  Bought " + wishlistService.buyAll(player.getIdPlayer()) + " games");
                            player.setWishlist(new ArrayList<Game>());
                            boughtLabel.setFont(DESCRPTION_FONT);
                            boughtLabel.setForeground(TEXT_COLOR);
                            gamesAddedToWishlistContainer.add(boughtLabel);

                            gamesAddedToWishlistContainer.revalidate();
                            gamesAddedToWishlistContainer.repaint();

                            WishlistWindow.this.pack();
                            WishlistWindow.this.revalidate();
                            WishlistWindow.this.repaint();
                        }
                    }
                });
                topRowContainer.add(button);
            container.add(topRowContainer);

            //strut
            container.add(Box.createRigidArea(new Dimension(0, 10)));

            //detail line
            PanelRound detailLine = new PanelRound();
            int dlr = 1;
            detailLine.setRoundTopLeft(dlr);
            detailLine.setRoundTopRight(dlr);
            detailLine.setRoundBottomRight(dlr);
            detailLine.setRoundBottomLeft(dlr);
            
            detailLine.setBackground(DETAILS_COLOR);
            detailLine.setPreferredSize(new Dimension(gamesAddedToWishlistContainer.getWidth(), 2));
            detailLine.setAlignmentX(CENTER_ALIGNMENT);
            container.add(detailLine);

            //strut
            container.add(Box.createRigidArea(new Dimension(0, 10)));

            //games added to wishlist
            gamesAddedToWishlistContainer.setOpaque(false);
            gamesAddedToWishlistContainer.setLayout(new BoxLayout(gamesAddedToWishlistContainer, BoxLayout.Y_AXIS));
            gamesAddedToWishlistContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            gamesAddedToWishlistContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                gamesAddedToWishlistContainer.add(Box.createRigidArea(new Dimension(0, 10)));

                if(player.getWishlist().size() == 0) {
                    //no games label
                    JLabel noGamesLabel = new JLabel("No games yet...");
                    noGamesLabel.setFont(SUBTITLE2_FONT);
                    noGamesLabel.setForeground(TEXT_COLOR);
                    gamesAddedToWishlistContainer.add(noGamesLabel);
                }

                else for(Game game : player.getWishlist()) {
                    GameMediumContainer mediumContainer = new GameMediumContainer(game);
                    mediumContainer.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            mainWindow.goGame(game);
                        }
                    });
                    gamesAddedToWishlistContainer.add(mediumContainer);
                }
            container.add(gamesAddedToWishlistContainer);


        this.add(container, BorderLayout.CENTER);

        this.pack();
        this.setLocation((1980-this.getWidth())/2, (1080-this.getHeight())/2);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight()+25, 10, 10));
        this.setVisible(true);
    }
}
