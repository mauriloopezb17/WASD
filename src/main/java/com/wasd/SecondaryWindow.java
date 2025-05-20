package com.wasd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.Flow;


public class SecondaryWindow extends JFrame implements StyleConfig {
    Player player;

    public SecondaryWindow(String title, MainWindow frame, Player player) {
        this.player = player;


        this.setTitle("WASD");
        this.setSize(300, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(20, 20));
        this.getContentPane().setBackground(BG_COLOR);
        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        makeDraggable(this, buttonPanel);

            WindowButton closeButton = new WindowButton("/images/close.png", CLOSE_COLOR);
            closeButton.addActionListener(e -> this.dispose());
            buttonPanel.add(closeButton);

        this.add(buttonPanel, BorderLayout.NORTH);
        

        //this.setVisible(true);
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
