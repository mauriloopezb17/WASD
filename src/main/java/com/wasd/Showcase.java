package com.wasd;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.w3c.dom.events.MouseEvent;

public class Showcase extends JPanel implements StyleConfig {
    ArrayList<Game> Games = new ArrayList<>();
    public Showcase(ArrayList<Game> games) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setOpaque(false);
        {
            //left arrow button
            JButton previousButton = new JButton();
            previousButton.setPreferredSize(new Dimension(30, 30));
            previousButton.setBorder(null);
            previousButton.setFocusable(false);
            previousButton.setBackground(PANEL_COLOR);
            previousButton.setIcon(AssetLoader.loadIcon("/images/leftArrow.png", 30, 30));
            previousButton.setFocusable(false);
            previousButton.setContentAreaFilled(false);
            previousButton.setFocusPainted(false);
            previousButton.setBorderPainted(false);
            previousButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            previousButton.addActionListener(e -> System.out.println("Previous"));
            this.add(previousButton);

            ArrayList<ImageIcon> thumbnails = new ArrayList<>(); //crea una lista de thumbnails y miniaturas
            ArrayList<ImageIcon> previews = new ArrayList<>(); 
            for (Game game : games) {
                System.out.println(game.getPictures().get(0));
                thumbnails.add(AssetLoader.loadIcon(game.getPictures().get(0), 640, 360));
                previews.add(AssetLoader.loadIcon(game.getPictures().get(0), 160, 90));
            }

            JPanel gameCard = new JPanel();
            gameCard.setLayout(new BoxLayout(gameCard, BoxLayout.Y_AXIS));
            gameCard.setPreferredSize(new Dimension(640, 405));
            gameCard.setBackground(TOP_BAR_COLOR);
            gameCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
            {
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(thumbnails.get(0));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel infoRow = new JPanel(new BorderLayout());
                infoRow.setBackground(TOP_BAR_COLOR);
                infoRow.setMaximumSize(new Dimension(640, 50)); // fix the row height
                infoRow.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding

                JLabel nameLabel = new JLabel(games.get(0).getNameGame());
                nameLabel.setFont(SUBTITLE_FONT);
                nameLabel.setForeground(TEXT_COLOR);

                JLabel priceContainer = new JLabel();
                priceContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));;
                priceContainer.setPreferredSize(new Dimension(230, 60));
                {
                    JLabel priceLabel = new JLabel("$" + games.get(0).getPrice());
                    priceLabel.setFont(SUBTITLE_FONT);
                    priceLabel.setForeground(TEXT_COLOR);
                    priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                    priceContainer.add(priceLabel);

                    JLabel discountLabel = new JLabel();
                    discountLabel.setFont(SUBTITLE2_FONT);
                    discountLabel.setForeground(DISCOUNT_COLOR);
                    discountLabel.setPreferredSize(new Dimension(70, 30));
                    discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    discountLabel.setText("-" + games.get(0).getDiscount() + "%");
                    Border greenRoundedBorder = new LineBorder(DISCOUNT_COLOR, 2, true);
                    discountLabel.setBorder(greenRoundedBorder);
                    priceContainer.add(discountLabel);
                }

                // Add name and price to info row
                infoRow.add(nameLabel, BorderLayout.WEST);
                infoRow.add(priceContainer, BorderLayout.EAST);

                gameCard.add(imageLabel);
                gameCard.add(infoRow);
            }

            //button like
            gameCard.addMouseListener(new MouseAdapter() {
                //@Override
                //public void mouseClicked(MouseEvent e) { //what's wrong here?       
                    // do something, like open the game's page
                //}
            });



            this.add(gameCard);

            //right arrow button
            JButton nextButton = new JButton();
            nextButton.setPreferredSize(new Dimension(30, 30));
            nextButton.setBorder(null);
            nextButton.setFocusable(false);
            nextButton.setBackground(PANEL_COLOR);
            nextButton.setIcon(AssetLoader.loadIcon("/images/rightArrow.png", 30, 30));
            nextButton.setFocusable(false);
            nextButton.setContentAreaFilled(false);
            nextButton.setFocusPainted(false);
            nextButton.setBorderPainted(false);
            nextButton.addActionListener(e -> System.out.println("Next"));
            nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.add(nextButton);
        }
    }
}
