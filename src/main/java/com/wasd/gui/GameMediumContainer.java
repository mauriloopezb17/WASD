package com.wasd.gui;
import com.wasd.models.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GameMediumContainer extends PanelRound implements StyleConfig {
    
    public GameMediumContainer(Game game) {
        super();
        int rad = 6;
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);
        this.setLayout(new BorderLayout(15,5));
        this.setPreferredSize(new Dimension(10, 115));
        this.setBackground(PANEL_COLOR);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        PanelRound bannerContainer = new PanelRound();
        int br = 6;

        int width = 230;
        int height = 107;

        bannerContainer.setRoundTopLeft(br);
        bannerContainer.setRoundTopRight(br);
        bannerContainer.setRoundBottomRight(br);
        bannerContainer.setRoundBottomLeft(br);
        bannerContainer.setPreferredSize(new Dimension(width, height));
        bannerContainer.setLayout(new BorderLayout(10, 10));
        bannerContainer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel bannerLabel = new JLabel();
        bannerLabel.setIcon(AssetLoader.loadIcon(game.getBanner(), width+9, height+8));
        bannerLabel.setPreferredSize(new Dimension(width, height));
        bannerContainer.add(bannerLabel);
        this.add(bannerContainer, BorderLayout.WEST);

        JPanel infoContainer = new JPanel();
        infoContainer.setOpaque(false);
        infoContainer.setLayout(new GridLayout(2,1));
        infoContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        {
            JLabel nameLabel = new JLabel(game.getNameGame());
            nameLabel.setFont(SUBTITLE2_FONT);
            nameLabel.setForeground(TEXT_COLOR);
            infoContainer.add(nameLabel);

            JPanel tagsPanel = new JPanel();
            tagsPanel.setOpaque(false);
            tagsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            {
                for (String tag : game.getTags()) {
                    JLabel tagLabel = new JLabel(tag + "    ");
                    tagLabel.setFont(SECONDARY_DESCRIPTION_FONT);
                    tagLabel.setForeground(SECONDARY_TEXT_COLOR);
                    tagsPanel.add(tagLabel);
                }
            }
            infoContainer.add(tagsPanel);
        }
        this.add(infoContainer, BorderLayout.CENTER);

        JPanel priceContainer = new JPanel();
        priceContainer.setOpaque(false);
        priceContainer.setLayout(new FlowLayout(FlowLayout.RIGHT,10,37));
        priceContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
            JLabel priceLabel = new JLabel("$" + game.getPrice());
            priceLabel.setFont(SUBTITLE_FONT);
            priceLabel.setForeground(TEXT_COLOR);
            priceContainer.add(priceLabel);

            PanelRound discountContainer = new PanelRound();
            int disRad = 6;
            discountContainer.setRoundTopLeft(disRad);
            discountContainer.setRoundTopRight(disRad);
            discountContainer.setRoundBottomRight(disRad);
            discountContainer.setRoundBottomLeft(disRad);
            discountContainer.setBorderColor(DISCOUNT_COLOR);
            discountContainer.setBorderStroke(2);
            discountContainer.setBackground(PANEL_COLOR);
            discountContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            discountContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
            discountContainer.setOpaque(false);
            discountContainer.setPreferredSize(new Dimension(70, 35));
            discountContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

            JLabel discountLabel = new JLabel();
            discountLabel.setFont(SUBTITLE2_FONT);
            discountLabel.setForeground(DISCOUNT_COLOR);
            discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            discountLabel.setText("-" + game.getDiscount() + "%");
            discountContainer.add(discountLabel);
            if (game.getDiscount() != 0) {
                priceContainer.add(discountContainer);
            }
        this.add(priceContainer, BorderLayout.EAST);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                GameMediumContainer.this.setBackground(HIGHLIGHT_COLOR);
                discountContainer.setBackground(HIGHLIGHT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                GameMediumContainer.this.setBackground(PANEL_COLOR);
                discountContainer.setBackground(PANEL_COLOR);
            }
        });
    }
}
