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
        
        TopNavigationBar topBar = new TopNavigationBar(this);
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

                JPanel galleryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                galleryPanel.setOpaque(false);
                {
                    //left arrow button
                    JButton previousButton = new JButton();
                    previousButton.setPreferredSize(new Dimension(30, 30));
                    previousButton.setBorder(null);
                    previousButton.setFocusable(false);
                    previousButton.setBackground(PANEL_COLOR);
                    previousButton.setIcon(AssetLoader.loadIcon("/images/leftArrow.png", 30, 30));
                    galleryPanel.add(previousButton);

                    ArrayList<ImageIcon> thumbnails = new ArrayList<>(); //crea una lista de thumbnails y miniaturas
                    ArrayList<ImageIcon> previews = new ArrayList<>(); 
                    for (Game game : recommendedGames) {
                        System.out.println(game.getPictures().get(0));
                        thumbnails.add(AssetLoader.loadIcon(game.getPictures().get(0), 640, 360));
                        previews.add(AssetLoader.loadIcon(game.getPictures().get(0), 160, 90));
                    }

                    JButton mainThumbnail = new JButton();
                    mainThumbnail.setPreferredSize(new Dimension(640, 360+40));
                    mainThumbnail.setHorizontalAlignment(JLabel.CENTER);
                    mainThumbnail.setVerticalAlignment(JLabel.TOP);
                    mainThumbnail.setBorder(null);
                    mainThumbnail.setFocusable(false);
                    mainThumbnail.setBackground(TOP_BAR_COLOR);
                    mainThumbnail.setIcon(thumbnails.get(0));
                    galleryPanel.add(mainThumbnail);

                    //right arrow button
                    JButton nextButton = new JButton();
                    nextButton.setPreferredSize(new Dimension(30, 30));
                    nextButton.setBorder(null);
                    nextButton.setFocusable(false);
                    nextButton.setBackground(PANEL_COLOR);
                    nextButton.setIcon(AssetLoader.loadIcon("/images/rightArrow.png", 30, 30));
                    galleryPanel.add(nextButton);
                }
                centralPanel.add(galleryPanel, BorderLayout.CENTER);

                //temporal bottom spacer
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(20, 250));
                spacer.setOpaque(false);
                centralPanel.add(spacer, BorderLayout.WEST);
            }
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getContentPane()) {
            this.dispose();
        }
    }
}
