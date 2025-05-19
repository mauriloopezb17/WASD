package com.wasd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Flow;

public class MainWindow extends JFrame implements ActionListener, StyleConfig {
    ArrayList<Game> allGames = new ArrayList<>();
    ArrayList<Game> recommendedGames = new ArrayList<>();
    Player player = new Player("Pancake99");

    public MainWindow(ArrayList<Game> allGames, ArrayList<Game> recommendedGames, Player player) {
        this.allGames = allGames;
        this.recommendedGames = recommendedGames;
        this.player = player;


        this.setTitle("WASD");
        this.setSize(1366, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(20, 20));
        this.getContentPane().setBackground(BG_COLOR);
        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 10, 10));
        
        TopNavigationBar topBar = new TopNavigationBar(this, player);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyWindowShape();
            }
        });

        this.add(topBar, BorderLayout.NORTH);

        this.goHome();

        this.setVisible(true);
        applyWindowShape();
    }

    private void applyWindowShape() {
    this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
    }

    public void goHome() {
        Container contentPane = this.getContentPane();
        Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        this.setLayout(new BorderLayout(20, 20));
        this.add(topbar, BorderLayout.NORTH);

        //espacio a la izquierda
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(150, 0));
        this.add(spacerLabel, BorderLayout.WEST);

        {//Panel central
            JPanel centralPanel = new JPanel();
            centralPanel.setBackground(PANEL_COLOR);
            centralPanel.setLayout(new BorderLayout(10, 10));
            {
                JLabel recommendedLabel = new JLabel("    Recommended Games");
                recommendedLabel.setFont(TITLE_FONT);
                recommendedLabel.setForeground(TEXT_COLOR);
                centralPanel.add(recommendedLabel, BorderLayout.NORTH);

                Showcase galleryPanel = new Showcase(recommendedGames);
                centralPanel.add(galleryPanel, BorderLayout.CENTER);

                //temporal bottom spacer
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(20, 250));
                spacer.setOpaque(false);
                centralPanel.add(spacer, BorderLayout.WEST);
            }

            //temporal right spacer
            JPanel spacer = new JPanel();
            spacer.setPreferredSize(new Dimension(250, 0));
            spacer.setOpaque(false);
            this.add(spacer, BorderLayout.EAST);

            this.add(centralPanel, BorderLayout.CENTER);
        }

    contentPane.revalidate();
    contentPane.repaint();
    }

    public void goLibrary() {
        Container contentPane = this.getContentPane();
        Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        this.setLayout(new BorderLayout(20, 20));
        this.add(topbar, BorderLayout.NORTH);

        //temporarily fill the other borders with empty space
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(20, 250));
        spacer.setOpaque(false);
        this.add(spacer, BorderLayout.WEST);
        this.add(spacer, BorderLayout.EAST);
        this.add(spacer, BorderLayout.SOUTH);
        this.add(spacer, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getContentPane()) {
            this.dispose();
        }
    }
}
