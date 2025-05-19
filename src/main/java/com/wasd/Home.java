package com.wasd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.*;

public class Home extends JFrame implements ActionListener, StyleConfig {

    public Home() {
        this.setTitle("WASD");
        this.setSize(1366, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(20, 20));
        this.getContentPane().setBackground(BG_COLOR);
        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 10, 10));
        
        TopNavigationBar topBar = new TopNavigationBar(this);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyWindowShape();
            }
        });
        {
            JLabel spacerLabel = new JLabel();
            spacerLabel.setPreferredSize(new Dimension(150, 0));
            this.add(spacerLabel, BorderLayout.WEST);
        }

        this.add(topBar, BorderLayout.NORTH);

        this.setVisible(true);
        applyWindowShape();
    }

    private void applyWindowShape() {
    this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getContentPane()) {
            this.dispose();
        }
    }
}
