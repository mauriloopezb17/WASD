package com.wasd.gui;
import com.wasd.models.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.wasd.models.Player;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;

public class TopNavigationBar extends JPanel implements StyleConfig {
    private ProfileWindow profileWindow = null;

    TabButton homeButton;
    TabButton libraryButton;

    public TopNavigationBar(MainWindow frame, Player player) {
        super(new BorderLayout());
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 53));


        // Main top bar with BorderLayout
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(TOP_BAR_COLOR);
        topBar.setPreferredSize(new Dimension(800, 50));
        makeDraggable(frame, topBar);

        // Button group on the right (EAST)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        {
            WindowButton minimizeButton = new WindowButton("/images/minimize.png", HIGHLIGHT_COLOR);
            minimizeButton.addActionListener(e -> frame.setState(JFrame.ICONIFIED));
            buttonPanel.add(minimizeButton);

            WindowButton resizeButton = new WindowButton("/images/maximize.png", HIGHLIGHT_COLOR);
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
                }

                frame.revalidate();
                frame.repaint();
            });
            //buttonPanel.add(resizeButton);

            WindowButton closeButton = new WindowButton("/images/close.png", CLOSE_COLOR);
            closeButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
            buttonPanel.add(closeButton);
        }
        topBar.add(buttonPanel, BorderLayout.EAST);

        // Tab buttons
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        tabPanel.setOpaque(false);
        {

            //logo
            JLabel logoLabel = new JLabel();
            logoLabel.setIcon(AssetLoader.loadIcon("/images/logo.png", 50, 50));
            logoLabel.setPreferredSize(new Dimension(50, 50));
            logoLabel.setOpaque(false);
            tabPanel.add(logoLabel);

            homeButton = new TabButton("HOME", "/images/home_active.png", "/images/home_inactive.png", true);
            tabPanel.add(homeButton);

            libraryButton = new TabButton("LIBRARY", "/images/library_active.png", "/images/library_inactive.png", false);
            tabPanel.add(libraryButton);

            homeButton.addActionListener(e -> {
                if (!homeButton.isCurrentTab) {
                    frame.goHome();
                    homeButton.setActive(true);
                    libraryButton.setActive(false);
                }
            });
            libraryButton.addActionListener(e -> {
                if (!libraryButton.isCurrentTab) {
                    frame.goLibrary();
                    homeButton.setActive(false);
                    libraryButton.setActive(true);
                }
            });
        }
        topBar.add(tabPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(TOP_BAR_COLOR);
        centerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        {
            PanelRound bellButton = new PanelRound();
            int br = 5;
            bellButton.setRoundTopLeft(br);
            bellButton.setRoundTopRight(br);
            bellButton.setRoundBottomRight(br);
            bellButton.setRoundBottomLeft(br);

            bellButton.setPreferredSize(new Dimension(30, 30));
            bellButton.setLayout(new BorderLayout());
            bellButton.setBorder(null);
            //bellButton.setFocusable(false);
            bellButton.setOpaque(false);
            bellButton.setBackground(TOP_BAR_COLOR);
            bellButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            bellButton.setFocusable(false);

                //bell icon
                JLabel bellIcon = new JLabel();
                bellIcon.setIcon(AssetLoader.loadIcon("/images/bell.png", 30, 30));
                bellIcon.setOpaque(false);
                bellIcon.setHorizontalAlignment(SwingConstants.CENTER);
                bellIcon.setVerticalAlignment(SwingConstants.CENTER);
                bellIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                bellButton.add(bellIcon, BorderLayout.CENTER);

            bellButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    bellButton.setBackground(HIGHLIGHT_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bellButton.setBackground(TOP_BAR_COLOR);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Bell");
                    new NotificationWindow(player);
                }
            });
            centerPanel.add(bellButton);

            PanelRound profileButton = new PanelRound();
            int rad = 18;
            profileButton.setRoundTopLeft(rad);
            profileButton.setRoundTopRight(rad);
            profileButton.setRoundBottomRight(rad);
            profileButton.setRoundBottomLeft(rad);
            profileButton.setBorderColor(DETAILS_COLOR);
            profileButton.setBorderStroke(2);
            profileButton.setBackground(TOP_BAR_COLOR);
            profileButton.setPreferredSize(new Dimension(150, 36));
            profileButton.setLayout(new BorderLayout(10, 10));
            profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                JLabel profileLabel = new JLabel();
                profileLabel.setText(player.getUsername().toUpperCase());
                profileLabel.setPreferredSize(new Dimension(400, 40));
                profileLabel.setFont(DESCRPTION_FONT);
                profileLabel.setForeground(TEXT_COLOR);
                profileLabel.setOpaque(false);
                profileButton.add(profileLabel, BorderLayout.CENTER);

                JLabel profileIcon = new JLabel();
                profileIcon.setIcon(AssetLoader.loadCircularIconFromUrl(player.getAvatar(), 30));
                profileIcon.setPreferredSize(new Dimension(35,30));
                profileIcon.setOpaque(false);
                profileIcon.setHorizontalAlignment(SwingConstants.RIGHT);
                //profileIcon.setBorder(new RoundedBorder(10));
                profileButton.add(profileIcon, BorderLayout.WEST);

                JLabel statusDot = new JLabel();
                statusDot.setPreferredSize(new Dimension(25,15));
                statusDot.setIcon(AssetLoader.loadCircularIcon("/images/online_dot.png", 15));
                statusDot.setOpaque(false);
                profileButton.add(statusDot, BorderLayout.EAST);

            profileButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    profileButton.setBackground(TEXT_COLOR);
                    profileLabel.setForeground(TOP_BAR_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    profileButton.setBackground(TOP_BAR_COLOR);
                    profileLabel.setForeground(TEXT_COLOR);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (profileWindow == null || !profileWindow.isDisplayable()) {
                        profileWindow = new ProfileWindow(frame, player);
                    } else {
                        profileWindow.toFront();
                        profileWindow.requestFocus();
                    }
                }
            });

            centerPanel.add(profileButton);
            //spacer
            JPanel spacer = new JPanel();
            spacer.setPreferredSize(new Dimension(30, 0));
            spacer.setOpaque(false);
            centerPanel.add(spacer);
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

    public void tabsToInactive() {
        homeButton.setActive(false);
        libraryButton.setActive(false);
        System.out.println("Tabs to inactive");
    }

    public void setToHome() {
        homeButton.setActive(true);
    }
    public void setToLibrary() {
        libraryButton.setActive(true);
    }
}
