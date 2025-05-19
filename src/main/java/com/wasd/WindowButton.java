package com.wasd;
import javax.swing.*;
import java.awt.*;

public class WindowButton extends JButton {
    WindowButton(String path) {
        int width = 15;
        int height = width;
        this.setIcon(AssetLoader.loadIcon(path, width, height));
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(null);
    }
}
