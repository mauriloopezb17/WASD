package com.wasd;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ProfileSmallContainer extends PanelRound implements StyleConfig {
    
    ProfileSmallContainer(Player player) {
        super();
        int width = 280;
        int height = 40;
        int rad = 6;
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(PANEL_COLOR);

        //small avatar
        PanelRound avatarPanel = new PanelRound();
        int ar = 10;
        avatarPanel.setRoundTopLeft(ar);
        avatarPanel.setRoundTopRight(ar);
        avatarPanel.setRoundBottomRight(ar);
        avatarPanel.setRoundBottomLeft(ar);
        avatarPanel.setOpaque(false);
        avatarPanel.setBackground(BG_COLOR);
        avatarPanel.setLayout(new BorderLayout(15,0));
        avatarPanel.setPreferredSize(new Dimension(height-5, height-5));

        JLabel avatarImage = new JLabel();
        avatarImage.setIcon(AssetLoader.loadIcon(player.getAvatar(), height-5, height-5));
        avatarPanel.add(avatarImage, BorderLayout.CENTER);
        this.add(avatarPanel, BorderLayout.WEST);

        //username
        JLabel usernameLabel = new JLabel("  " + player.getUsername());
        usernameLabel.setFont(SUBTITLE2_FONT);
        usernameLabel.setForeground(TEXT_COLOR);
        this.add(usernameLabel, BorderLayout.CENTER);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ProfileSmallContainer.this.setBackground(HIGHLIGHT_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ProfileSmallContainer.this.setBackground(PANEL_COLOR);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO
            }
        });
    }
}
