package com.wasd;
import javax.swing.*;
import java.awt.*;

public class WindowButton extends JButton {
    WindowButton(String path) {
        this.setIcon(AssetLoader.loadIcon(path, 25, 25));
        this.setPreferredSize(new Dimension(25, 25));
        this.setBorder(null);
    }
}
