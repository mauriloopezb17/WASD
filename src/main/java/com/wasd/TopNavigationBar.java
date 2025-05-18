package com.wasd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TopNavigationBar extends Box {

    public TopNavigationBar(JFrame targetFrame) {
        super(BoxLayout.Y_AXIS);

        // Main top bar with BorderLayout
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));
        makeDraggable(targetFrame, topBar);

        // Button group on the right (EAST)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false); // make transparent to match topBar

        WindowButton minimizeButton = new WindowButton("/images/minimize.png");
        minimizeButton.addActionListener(e -> targetFrame.setState(JFrame.ICONIFIED));
        buttonPanel.add(minimizeButton);

        WindowButton resizeButton = new WindowButton("/images/maximize.png");
        resizeButton.addActionListener(e -> {
            int currentState = targetFrame.getExtendedState();
            if ((currentState & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                targetFrame.setExtendedState(JFrame.NORMAL);
            } else {
                targetFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        buttonPanel.add(resizeButton);

        WindowButton closeButton = new WindowButton("/images/close.png");
        closeButton.addActionListener(e -> targetFrame.dispatchEvent(new WindowEvent(targetFrame, WindowEvent.WINDOW_CLOSING)));
        buttonPanel.add(closeButton);

        topBar.add(buttonPanel, BorderLayout.EAST);

        // Cyan line under the top bar
        JPanel cyanLine = new JPanel();
        cyanLine.setBackground(StyleConfig.DETAILS_COLOR);
        cyanLine.setPreferredSize(new Dimension(0, 3));

        this.add(topBar);
        this.add(cyanLine);
    }

    private void makeDraggable(JFrame frame, JComponent component) {
        final Point[] initialClick = {null};

        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();
            }
        });

        component.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved = e.getX() - initialClick[0].x;
                int yMoved = e.getY() - initialClick[0].y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });
    }
}
