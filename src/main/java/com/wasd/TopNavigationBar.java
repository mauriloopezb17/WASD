package com.wasd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;

public class TopNavigationBar extends JPanel implements StyleConfig {

    public TopNavigationBar(MainWindow frame, Player player) {
        super(new BorderLayout());
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 53));


        // Main top bar with BorderLayout
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(TOP_BAR_COLOR);
        topBar.setPreferredSize(new Dimension(800, 50));
        makeDraggable(frame, topBar);

        // Button group on the right (EAST)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);
        {
            WindowButton minimizeButton = new WindowButton("/images/minimize.png");
            minimizeButton.addActionListener(e -> frame.setState(JFrame.ICONIFIED));
            buttonPanel.add(minimizeButton);

            WindowButton resizeButton = new WindowButton("/images/maximize.png");
            final boolean[] isMaximized = {false};
            final Dimension defaultSize = new Dimension(1280, 720);

            resizeButton.addActionListener(e -> {
                GraphicsConfiguration config = frame.getGraphicsConfiguration();
                Rectangle screenBounds = config.getBounds();
                Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
                System.out.println("TopNavBar size: " + this.getSize());
                System.out.println("ContentPane size: " + frame.getContentPane().getSize());

                if (isMaximized[0]) {
                    // Restore
                    frame.setSize(defaultSize);
                    frame.setLocationRelativeTo(null);
                    isMaximized[0] = false;

                    System.out.println("Restored to normal size.");
                } else {
                    // Maximize manually
                    int x = screenBounds.x + insets.left;
                    int y = screenBounds.y + insets.top;
                    int width = screenBounds.width - insets.left - insets.right;
                    int height = screenBounds.height - insets.top - insets.bottom;

                    frame.setBounds(x, y, width, height);
                    isMaximized[0] = true;

                    System.out.printf("Maximized to: %d x %d at (%d, %d)%n", width, height, x, y);
                }

                frame.revalidate();
                frame.repaint();
            });
            buttonPanel.add(resizeButton);

            WindowButton closeButton = new WindowButton("/images/close.png");
            closeButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
            buttonPanel.add(closeButton);
        }
        topBar.add(buttonPanel, BorderLayout.EAST);

        // Tab buttons
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        tabPanel.setOpaque(false);
        {
            JLabel spacerLabel = new JLabel();
            spacerLabel.setPreferredSize(new Dimension(10, 0));
            tabPanel.add(spacerLabel);
            TabButton homeButton = new TabButton("HOME", "/images/home_active.png", "/images/home_inactive.png", true);
            tabPanel.add(homeButton);
            TabButton libraryButton = new TabButton("LIBRARY", "/images/library_active.png", "/images/library_inactive.png", false);
            tabPanel.add(libraryButton);
            homeButton.addActionListener(e -> {
                if (!homeButton.isCurrentTab) {
                    frame.goHome();
                    homeButton.toggleActive();
                    libraryButton.toggleActive();
                }
            });
            libraryButton.addActionListener(e -> {
                if (!libraryButton.isCurrentTab) {
                    frame.goLibrary();
                    homeButton.toggleActive();
                    libraryButton.toggleActive();
                }
            });
        }
        topBar.add(tabPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(TOP_BAR_COLOR);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        {
            JPanel profileButton = new JPanel();
            profileButton.setBackground(TOP_BAR_COLOR);
            profileButton.setPreferredSize(new Dimension(100, 40));
            profileButton.setLayout(new BorderLayout(10, 10));
            {
                JLabel profileLabel = new JLabel();
                profileLabel.setText(player.getName().toUpperCase());
            }
        }
        topBar.add(centerPanel, BorderLayout.CENTER);

        // Cyan line under the top bar
        JPanel cyanLine = new JPanel();
        cyanLine.setBackground(DETAILS_COLOR);
        cyanLine.setPreferredSize(new Dimension(0, 3));

        this.add(topBar, BorderLayout.CENTER);
        this.add(cyanLine, BorderLayout.SOUTH);
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
