package com.wasd.gui;

import java.awt.*;
import javax.swing.*;

import com.wasd.models.Game;

import java.awt.event.*;

public class GameSmallContainer extends PanelRound implements StyleConfig {
    GameSmallContainer(Game game) {
        super();
        int width = 85;
        int height = 40;
        int rad = 6;
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(PANEL_COLOR);

        JLabel bannerLabel = new JLabel();
        bannerLabel.setIcon(AssetLoader.loadIconFromUrl(game.getBanner(), width, height));
        bannerLabel.setPreferredSize(new Dimension(width, height));

        this.add(bannerLabel, BorderLayout.CENTER);
    }
}
