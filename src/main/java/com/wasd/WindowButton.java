package com.wasd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowButton extends JButton implements StyleConfig {
    WindowButton(String path, Color hoverColor) {
        int width = 15;
        int height = width;
        this.setIcon(AssetLoader.loadIcon(path, width, height));
        this.setBackground(TOP_BAR_COLOR);
        this.setPreferredSize(new Dimension(width+15, height+10));
        this.setFocusable(false);
        this.setBorder(null);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                WindowButton.this.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                WindowButton.this.setBackground(TOP_BAR_COLOR);
            }
        });
    }
}
