package com.wasd.gui;

import java.awt.*;
import javax.swing.*;

import com.wasd.models.Game;

import java.awt.event.*;

public class TagContainer extends PanelRound implements StyleConfig {
    
    public TagContainer(String tag) {
        super();
        int rad = 6;
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(10, 115));
        this.setBackground(PANEL_COLOR);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int br = 6;

        int width = 0;
        int height = 30;

        //tag label
        JLabel tagLabel = new JLabel(tag);
        tagLabel.setFont(SUBTITLE2_FONT);
        tagLabel.setForeground(TEXT_COLOR);
        tagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        tagLabel.setPreferredSize(new Dimension(width, height));
        tagLabel.setOpaque(false);
        this.add(tagLabel, BorderLayout.CENTER);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
            }
        });
    
}
