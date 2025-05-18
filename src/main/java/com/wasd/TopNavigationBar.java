package com.wasd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TopNavigationBar extends JPanel {

    public TopNavigationBar(JFrame targetFrame) {
        super(new BorderLayout());
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 53));


        // Main top bar with BorderLayout
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));
        makeDraggable(targetFrame, topBar);

        // Button group on the right (EAST)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false); // match topBar
        {
            WindowButton minimizeButton = new WindowButton("/images/minimize.png");
            minimizeButton.addActionListener(e -> targetFrame.setState(JFrame.ICONIFIED));
            buttonPanel.add(minimizeButton);

            WindowButton resizeButton = new WindowButton("/images/maximize.png");
            final boolean[] isMaximized = {false};
            final Dimension defaultSize = new Dimension(1280, 720);

            resizeButton.addActionListener(e -> {
                GraphicsConfiguration config = targetFrame.getGraphicsConfiguration();
                Rectangle screenBounds = config.getBounds();
                Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
                System.out.println("TopNavBar size: " + this.getSize());
                System.out.println("ContentPane size: " + targetFrame.getContentPane().getSize());

                if (isMaximized[0]) {
                    // Restore
                    targetFrame.setSize(defaultSize);
                    targetFrame.setLocationRelativeTo(null);
                    isMaximized[0] = false;

                    System.out.println("Restored to normal size.");
                } else {
                    // Maximize manually
                    int x = screenBounds.x + insets.left;
                    int y = screenBounds.y + insets.top;
                    int width = screenBounds.width - insets.left - insets.right;
                    int height = screenBounds.height - insets.top - insets.bottom;

                    targetFrame.setBounds(x, y, width, height);
                    isMaximized[0] = true;

                    System.out.printf("Maximized to: %d x %d at (%d, %d)%n", width, height, x, y);
                }

                targetFrame.revalidate();
                targetFrame.repaint();
            });
            buttonPanel.add(resizeButton);

            WindowButton closeButton = new WindowButton("/images/close.png");
            closeButton.addActionListener(e -> targetFrame.dispatchEvent(new WindowEvent(targetFrame, WindowEvent.WINDOW_CLOSING)));
            buttonPanel.add(closeButton);
        }
        topBar.add(buttonPanel, BorderLayout.EAST);

        // Cyan line under the top bar
        JPanel cyanLine = new JPanel();
        cyanLine.setBackground(StyleConfig.DETAILS_COLOR);
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
