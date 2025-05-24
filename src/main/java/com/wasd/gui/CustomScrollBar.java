package com.wasd.gui;

import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import java.awt.*;

public class CustomScrollBar extends BasicScrollBarUI implements StyleConfig {

    private final int thickness = 6; // soft gray with some transparency

    @Override
    protected void configureScrollBarColors() {
        thumbHighlightColor = HIGHLIGHT_COLOR;
        thumbLightShadowColor = HIGHLIGHT_COLOR;
        thumbDarkShadowColor = HIGHLIGHT_COLOR;
        trackColor = HIGHLIGHT_COLOR;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(HIGHLIGHT_COLOR);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, thickness, thickness);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(PANEL_COLOR);
        // Draw the track
        g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, thickness, thickness);
        g2.dispose();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(thickness, thickness);
    }
}
