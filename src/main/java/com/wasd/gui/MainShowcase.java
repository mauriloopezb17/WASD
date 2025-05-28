package com.wasd.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;

import javax.swing.border.LineBorder;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

import com.wasd.models.Game;

public class MainShowcase extends JPanel implements StyleConfig {
    
    int width = 640;
    int height = 360;
    int rad = 30;
    int imageIndex = 0;

    JLabel bigImageLabel;

    public MainShowcase(Game game) {
        this.setLayout(new BorderLayout(8,0));
        this.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        this.setOpaque(false);

        //big image container
        PanelRound bigImageContainer = new PanelRound();
        bigImageContainer.setRoundTopLeft(rad);
        bigImageContainer.setRoundTopRight(rad);
        bigImageContainer.setRoundBottomRight(rad);
        bigImageContainer.setRoundBottomLeft(rad);
        bigImageContainer.setBackground(PANEL_COLOR);
        bigImageContainer.setLayout(new BorderLayout(0,4));
        bigImageContainer.setPreferredSize(new Dimension(width+16, height+9));
        bigImageContainer.setOpaque(false);
        
        //big image
        bigImageLabel = new JLabel();
        bigImageLabel.setIcon(AssetLoader.loadIconFromUrl(game.getPictures().get(0), width+16, height+9));
        bigImageLabel.setAlignmentX(CENTER_ALIGNMENT);
        bigImageLabel.setAlignmentY(CENTER_ALIGNMENT);
        bigImageLabel.setPreferredSize(new Dimension(width, height));
        bigImageContainer.add(bigImageLabel, BorderLayout.CENTER);

        //lower selector container
        JPanel lowerSelectorContainer = new JPanel();
        lowerSelectorContainer.setOpaque(false);
        lowerSelectorContainer.setLayout(new BorderLayout(0,4));
        lowerSelectorContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

            //images container
            JPanel imagesContainer = new JPanel();
            imagesContainer.setOpaque(false);
            imagesContainer.setLayout(new GridLayout(1,4, 5, 10));
            imagesContainer.setAlignmentX(CENTER_ALIGNMENT);
            imagesContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

                //images
                //store images in an arraylist so we can change them without having to reload them
                ArrayList<ImageIcon> smallImages = new ArrayList<>();
                ArrayList<ImageIcon> bigImages = new ArrayList<>();
                
                for(int i = 0; i < game.getPictures().size(); i++) {
                    String imageUrl = game.getPictures().get(i);
                    smallImages.add(AssetLoader.loadIconFromUrl(imageUrl, (int)(width/4.2), (int)(height/4.2)));
                    bigImages.add(AssetLoader.loadIconFromUrl(imageUrl, width+16, height+9));
                }

                SmallImageContainer sic1 = new SmallImageContainer(smallImages.get(0), bigImages.get(0));
                SmallImageContainer sic2 = new SmallImageContainer(smallImages.get(1), bigImages.get(1));
                SmallImageContainer sic3 = new SmallImageContainer(smallImages.get(2), bigImages.get(2));
                SmallImageContainer sic4 = new SmallImageContainer(smallImages.get(3), bigImages.get(3));

                imagesContainer.add(sic1);
                imagesContainer.add(sic2);
                imagesContainer.add(sic3);
                imagesContainer.add(sic4);

            lowerSelectorContainer.add(imagesContainer, BorderLayout.CENTER);

            //left arrow button
            JButton previousButton = new JButton();
            previousButton.setPreferredSize(new Dimension(20, 20));
            previousButton.setBorder(null);
            previousButton.setFocusable(false);
            previousButton.setBackground(PANEL_COLOR);
            previousButton.setIcon(AssetLoader.loadIcon("/images/leftArrow.png", 20, 20));
            previousButton.setFocusable(false);
            previousButton.setContentAreaFilled(false);
            previousButton.setFocusPainted(false);
            previousButton.setBorderPainted(false);
            previousButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            previousButton.addActionListener(e -> {
                imageIndex--;
                if (imageIndex < 0) imageIndex = game.getPictures().size()-1;

                sic1.setImages(smallImages.get((imageIndex)%game.getPictures().size()), bigImages.get((imageIndex)%game.getPictures().size()));
                sic2.setImages(smallImages.get((imageIndex+1)%game.getPictures().size()), bigImages.get((imageIndex+1)%game.getPictures().size()));
                sic3.setImages(smallImages.get((imageIndex+2)%game.getPictures().size()), bigImages.get((imageIndex+2)%game.getPictures().size()));
                sic4.setImages(smallImages.get((imageIndex+3)%game.getPictures().size()), bigImages.get((imageIndex+3)%game.getPictures().size()));
            });
            lowerSelectorContainer.add(previousButton, BorderLayout.WEST);

            //right arrow button
            JButton nextButton = new JButton();
            nextButton.setPreferredSize(new Dimension(20, 20));
            nextButton.setBorder(null);
            nextButton.setFocusable(false);
            nextButton.setBackground(PANEL_COLOR);
            nextButton.setIcon(AssetLoader.loadIcon("/images/rightArrow.png", 20, 20));
            nextButton.setFocusable(false);
            nextButton.setContentAreaFilled(false);
            nextButton.setFocusPainted(false);
            nextButton.setBorderPainted(false);
            nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            nextButton.addActionListener(e -> {
                imageIndex++;
                if (imageIndex > game.getPictures().size()-1) imageIndex = 0;

                sic1.setImages(smallImages.get((imageIndex)%game.getPictures().size()), bigImages.get((imageIndex)%game.getPictures().size()));
                sic2.setImages(smallImages.get((imageIndex+1)%game.getPictures().size()), bigImages.get((imageIndex+1)%game.getPictures().size()));
                sic3.setImages(smallImages.get((imageIndex+2)%game.getPictures().size()), bigImages.get((imageIndex+2)%game.getPictures().size()));
                sic4.setImages(smallImages.get((imageIndex+3)%game.getPictures().size()), bigImages.get((imageIndex+3)%game.getPictures().size()));
            });
            lowerSelectorContainer.add(nextButton, BorderLayout.EAST);

        this.add(bigImageContainer, BorderLayout.NORTH);
        //small spaecer
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0,4));
        spacer.setOpaque(false);
        //this.add(spacer);
        this.add(lowerSelectorContainer, BorderLayout.SOUTH);
    }

    public class SmallImageContainer extends PanelRound implements StyleConfig {
        int rad = 10;
        JLabel smallImageLabel;
        ImageIcon bigImageTransfer;

        public SmallImageContainer(ImageIcon smallImage, ImageIcon bigImage) {

            this.bigImageTransfer = bigImage;

            this.setRoundTopLeft(rad);
            this.setRoundTopRight(rad);
            this.setRoundBottomRight(rad);
            this.setRoundBottomLeft(rad);
            this.setBackground(PANEL_COLOR);
            this.setLayout(new BorderLayout(0,4));
            this.setOpaque(false);
            
            //small image
            smallImageLabel = new JLabel();
            smallImageLabel.setIcon(smallImage);
            smallImageLabel.setAlignmentX(CENTER_ALIGNMENT);
            smallImageLabel.setAlignmentY(CENTER_ALIGNMENT);
            smallImageLabel.setPreferredSize(new Dimension((int)(width/4.2), (int)(height/4.2)));
            this.add(smallImageLabel, BorderLayout.CENTER);
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));

            //make clickable
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("clicked");
                    bigImageLabel.setIcon(bigImageTransfer);
                }
            });
        }

        public void setImages(ImageIcon smallImage, ImageIcon bigImage) {
            this.smallImageLabel.setIcon(smallImage);
            this.bigImageTransfer = bigImage;
        }

    }
}