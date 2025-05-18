package com.wasd;

import javax.swing.*;
import java.awt.*;

public class AssetLoader {
    public static ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(AssetLoader.class.getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
