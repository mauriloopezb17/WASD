package com.wasd.gui;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.lang.reflect.Array;

import org.w3c.dom.events.MouseEvent;

import com.wasd.models.Game;

public class Showcase extends JPanel implements StyleConfig {
    ArrayList<Game> Games = new ArrayList<>();
    int i;
    int slideshowIndex = 0;
    JLabel priceLabel;
    JLabel nameLabel;
    JLabel imageLabel;
    JLabel discountLabel;
    PanelRound discountContainer;

    public Showcase(ArrayList<Game> games) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setOpaque(false);
        this.i = 0;
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

            ArrayList<ArrayList<ImageIcon>> thumbnails = new ArrayList<>(); //crea una lista de thumbnails y miniaturas
            ArrayList<ArrayList<ImageIcon>> previews = new ArrayList<>();
            for (int gameIndex = 0; gameIndex < games.size(); gameIndex++) {
                thumbnails.add(new ArrayList<ImageIcon>());
                previews.add(new ArrayList<ImageIcon>());
                for (int thumbnaiIndex = 0; thumbnaiIndex < games.get(gameIndex).getPictures().size(); thumbnaiIndex++) {
                    thumbnails.get(gameIndex).add(AssetLoader.loadIconFromUrl(games.get(gameIndex).getPictures().get(thumbnaiIndex), 640, 360));
                    previews.get(gameIndex).add(AssetLoader.loadIconFromUrl(games.get(gameIndex).getPictures().get(thumbnaiIndex), 160, 90));
                }
            }

            PanelRound gameCard = new PanelRound();
            int rad = 6;
            gameCard.setRoundTopLeft(rad);
            gameCard.setRoundTopRight(rad);
            gameCard.setRoundBottomRight(rad);
            gameCard.setRoundBottomLeft(rad);
            gameCard.setBorderColor(PANEL_COLOR);
            gameCard.setBorderStroke(2);
            gameCard.setOpaque(false);
            gameCard.setLayout(new BoxLayout(gameCard, BoxLayout.Y_AXIS));
            gameCard.setPreferredSize(new Dimension(640, 415));
            gameCard.setBackground(TOP_BAR_COLOR);
            gameCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
            //gameCard.setBorder(new RoundedBorder(20));
            {
                imageLabel = new JLabel();
                imageLabel.setIcon(thumbnails.get(0).get(0));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel infoRow = new JPanel(new BorderLayout());
                infoRow.setBackground(TOP_BAR_COLOR);
                infoRow.setMaximumSize(new Dimension(640, 50)); // fix the row height
                infoRow.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
                infoRow.setAlignmentY(Component.CENTER_ALIGNMENT);

                nameLabel = new JLabel(games.get(0).getNameGame());
                nameLabel.setFont(SUBTITLE_FONT);
                nameLabel.setForeground(TEXT_COLOR);

                JLabel priceContainer = new JLabel();
                priceContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));;
                priceContainer.setPreferredSize(new Dimension(230, 60));
                {
                    if (games.get(0).getPrice() != 0) {
                        priceLabel = new JLabel("$" + games.get(0).getPrice() + "  ");
                    }
                    else {
                        priceLabel = new JLabel("Free");
                    }
                    priceLabel.setFont(SUBTITLE_FONT);
                    priceLabel.setForeground(TEXT_COLOR);
                    priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                    priceContainer.add(priceLabel);

                    discountContainer = new PanelRound();
                    int disRad = 6;
                    discountContainer.setRoundTopLeft(disRad);
                    discountContainer.setRoundTopRight(disRad);
                    discountContainer.setRoundBottomRight(disRad);
                    discountContainer.setRoundBottomLeft(disRad);
                    discountContainer.setBorderColor(DISCOUNT_COLOR);
                    discountContainer.setBorderStroke(2);
                    discountContainer.setBackground(TOP_BAR_COLOR);
                    discountContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
                    discountContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
                    discountContainer.setPreferredSize(new Dimension(70, 35));
                    discountContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

                    discountLabel = new JLabel();
                    discountLabel.setFont(SUBTITLE2_FONT);
                    discountLabel.setForeground(DISCOUNT_COLOR);
                    discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    discountLabel.setText("-" + games.get(0).getDiscount() + "%");

                    if (games.get(0).getDiscount() != 0) {
                        discountContainer.setVisible(true);
                    }
                    else {
                        discountContainer.setVisible(false);
                    }

                    discountContainer.add(discountLabel);
                    priceContainer.add(discountContainer);
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

            previousButton.addActionListener(e -> {
                this.i--;
                if(this.i < 0) this.i = games.size()-1;
                if (games.get(i%games.size()).getPrice() != 0) {
                    priceLabel.setText("$" + games.get(i%games.size()).getPrice());
                }
                else {
                    priceLabel.setText("Free");
                }
                nameLabel.setText(games.get(i%games.size()).getNameGame());
                imageLabel.setIcon(thumbnails.get(i%thumbnails.size()).get(0));

                if (games.get(i%games.size()).getDiscount() != 0) {
                    discountLabel.setText("-" + games.get(i%games.size()).getDiscount() + "%");
                    discountContainer.setVisible(true);
                }
                else {
                    discountContainer.setVisible(false);
                }
            });
            nextButton.addActionListener(e -> {
                this.i++;
                if (games.get(i%games.size()).getPrice() != 0) {
                    priceLabel.setText("$" + games.get(i%games.size()).getPrice());
                }
                else {
                    priceLabel.setText("Free");
                }
                nameLabel.setText(games.get(i%games.size()).getNameGame());
                imageLabel.setIcon(thumbnails.get(i%thumbnails.size()).get(0));
                
                if (games.get(i%games.size()).getDiscount() != 0) {
                    discountLabel.setText("-" + games.get(i%games.size()).getDiscount() + "%");
                    discountContainer.setVisible(true);
                }
                else {
                    discountContainer.setVisible(false);
                }
            });
            Timer autoScrollTimer = new Timer(2500, e -> {
                this.slideshowIndex++;
                imageLabel.setIcon(thumbnails.get(i % thumbnails.size()).get(slideshowIndex % thumbnails.get(i % thumbnails.size()).size()));
            });
            autoScrollTimer.start();

            previousButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.add(previousButton);
            this.add(gameCard);
            this.add(nextButton);
        }
    }
}
