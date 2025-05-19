package com.wasd;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.awt.font.*;
import java.awt.event.*;

public class TabButton extends JButton implements StyleConfig{
    String text;
    ImageIcon iconActive;
    ImageIcon iconInactive;

    int width = 35;
    int height = width;
    ImageIcon defaultIcon;
    boolean isCurrentTab;

    TabButton(String text,String activeIconPath, String inactiveIconPath, boolean isCurrentTab) {
        this.iconActive = AssetLoader.loadIcon(activeIconPath, width, height);
        this.iconInactive = AssetLoader.loadIcon(inactiveIconPath, width, height);
        //this.setPreferredSize(new Dimension(width, height));
        this.setBorder(null);
        this.text = text;
        this.setFont(getBoldUnderlinedFont(BUTTON_LABEL_FONT));
        this.setForeground(DETAILS_COLOR);
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setVerticalTextPosition(JLabel.TOP);
        // inactive state (default)
        if (isCurrentTab) {
            this.isCurrentTab = true;
            defaultIcon = iconActive;
            this.setIcon(iconActive);
            this.setText(text);
        } else {
            this.isCurrentTab = false;
            defaultIcon = iconInactive;
            this.setIcon(iconInactive);
            this.setText("");
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(iconActive);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(defaultIcon);
            }
        });
    }
    
    public void toggleActive() {
        if (isCurrentTab) {
            this.isCurrentTab = !isCurrentTab;
            defaultIcon = iconInactive;
            this.setIcon(iconInactive);
            this.setText("");
        } else {
            this.isCurrentTab = !isCurrentTab;
            defaultIcon = iconActive;
            this.setIcon(iconActive);
            this.setText(text);
        }
    }

    private Font getBoldUnderlinedFont(Font baseFont) {
        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(baseFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return baseFont.deriveFont(attributes);
    }

}
