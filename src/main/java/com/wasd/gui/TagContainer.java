package com.wasd.gui;

import java.awt.*;
import javax.swing.*;

import com.wasd.models.Game;

import java.awt.event.*;

public class TagContainer extends PanelRound implements StyleConfig {

    public TagContainer(String tag) {
        super();
        int rad = 4;
        this.setRoundTopLeft(rad);
        this.setRoundTopRight(rad);
        this.setRoundBottomRight(rad);
        this.setRoundBottomLeft(rad);

        this.setLayout(new BorderLayout());
        this.setBackground(TEXT_COLOR);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // tag label
        JLabel tagLabel = new JLabel(tag.toUpperCase());
        tagLabel.setFont(DESCRPTION_FONT);
        tagLabel.setForeground(TOP_BAR_COLOR);
        tagLabel.setOpaque(false);
        tagLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // get label size
        Dimension labelSize = tagLabel.getPreferredSize();
        int paddingX = 10; // padding left + right
        int paddingY = 4;  // padding top + bottom
        int finalWidth = labelSize.width + paddingX;
        int finalHeight = labelSize.height + paddingY;

        // apply size to container
        this.setPreferredSize(new Dimension(finalWidth, finalHeight));
        this.setMaximumSize(new Dimension(finalWidth, finalHeight));

        this.add(tagLabel, BorderLayout.CENTER);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked: " + tag);
            }
        });
    }
}
