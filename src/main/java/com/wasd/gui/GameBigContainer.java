package com.wasd.gui;

import com.wasd.models.Game;

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

public class GameBigContainer extends PanelRound implements StyleConfig {
    int rad = 10;

    int width = 322;
    int height = 151 + 30;

    public GameBigContainer(Game game) {
        super();
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);
        
        this.setBackground(TOP_BAR_COLOR);
        this.setLayout(new BorderLayout(0,4));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        //banner container
        PanelRound bannerContainer = new PanelRound();
        bannerContainer.setRoundTopLeft(rad);
        bannerContainer.setRoundTopRight(rad);
        bannerContainer.setRoundBottomRight(rad);
        bannerContainer.setRoundBottomLeft(rad);
        bannerContainer.setBackground(TOP_BAR_COLOR);
        bannerContainer.setLayout(new BorderLayout(0,0));
        bannerContainer.setPreferredSize(new Dimension(width, height - 30));
        bannerContainer.setOpaque(false);

            //banner
            JLabel bannerLabel = new JLabel();
            bannerLabel.setIcon(AssetLoader.loadIconFromUrl(game.getBanner(), width, height - 30));
            bannerLabel.setAlignmentX(CENTER_ALIGNMENT);
            bannerLabel.setAlignmentY(CENTER_ALIGNMENT);
            bannerLabel.setPreferredSize(new Dimension(width, height - 30));
            bannerContainer.add(bannerLabel, BorderLayout.CENTER);
        this.add(bannerContainer, BorderLayout.NORTH);

        //lower container
        JPanel lowerContainer = new JPanel();
        lowerContainer.setLayout(new BorderLayout(0,0));
        lowerContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lowerContainer.setOpaque(false);
        lowerContainer.setPreferredSize(new Dimension(width, 30));

            //title
            JLabel titleLabel = new JLabel();
            titleLabel.setText(game.getNameGame().toUpperCase());
            titleLabel.setOpaque(false);
            titleLabel.setFont(SUBTITLE2_FONT);
            titleLabel.setForeground(TEXT_COLOR);
            titleLabel.setAlignmentX(CENTER_ALIGNMENT);
            titleLabel.setAlignmentY(CENTER_ALIGNMENT);
            titleLabel.setPreferredSize(new Dimension(width, 30));
            lowerContainer.add(titleLabel, BorderLayout.CENTER);
        this.add(lowerContainer, BorderLayout.CENTER);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                GameBigContainer.this.setBackground(HIGHLIGHT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                GameBigContainer.this.setBackground(TOP_BAR_COLOR);
            }
        });
    }
}
