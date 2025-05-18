package com.wasd;
import javax.swing.*;
import java.awt.*;

public class TabButton extends JButton implements StyleConfig {
    String text;
    ImageIcon iconActive;
    ImageIcon iconInactive;
    TabButton(String text,String activeIconPath, String inactiveIconPath) {
        this.iconActive = AssetLoader.loadIcon(activeIconPath, 25, 25);
        this.iconInactive = AssetLoader.loadIcon(inactiveIconPath, 25, 25);
        this.setPreferredSize(new Dimension(50, 50));
        this.setBorder(null);
        this.text = text;
        // inactive state (default)
        this.setIcon(iconInactive);
        this.setText("");
    }

    public void toggleActive(boolean isActive) {
        if (isActive) {
            this.setIcon(AssetLoader.loadIcon("/images/tab-active.png", 25, 25));
            this.setText("");
        } else {
            this.setIcon(AssetLoader.loadIcon("/images/tab-inactive.png", 25, 25));
        }
    }
}
