package com.wasd.gui;
import com.wasd.Main;
import com.wasd.models.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.Flow;

public class MainWindow extends JFrame implements ActionListener, StyleConfig {
    ArrayList<Game> allGames = new ArrayList<>();
    ArrayList<Game> recommendedGames = new ArrayList<>();
    Player player;
    TopNavigationBar topBar;

    JLabel wSpacer, eSpacer;

    public MainWindow(ArrayList<Game> allGames, ArrayList<Game> recommendedGames, Player player) {

        wSpacer = new JLabel();
        eSpacer = new JLabel();
        wSpacer.setOpaque(false);
        eSpacer.setOpaque(false);
        //wSpacer.setPreferredSize(new Dimension(this.getWidth() / 7, 0));
        //eSpacer.setPreferredSize(new Dimension(this.getWidth() / 7, 0));

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
        this.setIconImage(AssetLoader.loadIcon("/images/logo.png", 150, 150).getImage());
        
        topBar = new TopNavigationBar(this, player);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyWindowShape();
            }
        });

        this.add(topBar, BorderLayout.NORTH);

        //this.goHome();
        this.goGame(allGames.get(0));

        this.setVisible(true);
        applyWindowShape();
    }

    private void applyWindowShape() {
    this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
    }

    public void goHome() {
        Container contentPane = this.getContentPane();
        //Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        this.setLayout(new BorderLayout());
        this.add(topBar, BorderLayout.NORTH);
        this.getContentPane().setBackground(BG_COLOR);

        // Left spacer
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(150, 0));
        this.add(spacerLabel, BorderLayout.WEST);

        // Central layout
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setOpaque(false);

        PanelRound centralPanel = new PanelRound();
        int rad = 15;
        centralPanel.setRoundTopLeft(rad);
        centralPanel.setRoundTopRight(rad);
        centralPanel.setRoundBottomRight(rad);
        centralPanel.setRoundBottomLeft(rad);
        centralPanel.setBackground(PANEL_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // "Recommended Games" Label
        JLabel recommendedLabel = new JLabel("Recommended Games");
        recommendedLabel.setFont(TITLE_FONT);
        recommendedLabel.setForeground(TEXT_COLOR);
        recommendedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centralPanel.add(recommendedLabel);

        // Gallery Panel
        Showcase galleryPanel = new Showcase(recommendedGames);
        galleryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centralPanel.add(Box.createVerticalStrut(10));
        centralPanel.add(galleryPanel);

        // ComboBox Filter UI
        Set<String> allTags = new HashSet<>();
        for (Game g : allGames) allTags.addAll(g.getTags());

        List<String> tagOptions = new ArrayList<>(allTags);
        Collections.sort(tagOptions);
        tagOptions.add(0, "All");

        JComboBox<String> tagComboBox = new JComboBox<>(tagOptions.toArray(new String[0]));
        tagComboBox.setPreferredSize(new Dimension(200, 30));
        tagComboBox.setFont(DESCRPTION_FONT);
        tagComboBox.setBackground(PANEL_COLOR);
        tagComboBox.setForeground(TEXT_COLOR);
        tagComboBox.setFocusable(false);
        tagComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tagComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(DESCRPTION_FONT);
                label.setBackground(PANEL_COLOR);
                label.setForeground(TEXT_COLOR);

                if (isSelected) {
                    label.setBackground(HIGHLIGHT_COLOR);  // background when hovering/selected
                    label.setForeground(TEXT_COLOR);      // text color when selected
                }

                return label;
            }
        });

        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterRow.setOpaque(false);
        JLabel filterLabel = new JLabel("Filter by tag: ");
        filterLabel.setFont(SUBTITLE_FONT);
        filterLabel.setForeground(TEXT_COLOR);

        filterRow.add(filterLabel);
        filterRow.add(tagComboBox);
        filterRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        centralPanel.add(Box.createVerticalStrut(15));
        centralPanel.add(filterRow);

        JPanel byTagPanel = new JPanel();
        byTagPanel.setBackground(PANEL_COLOR);
        byTagPanel.setLayout(new GridLayout(0, 1, 0, 10));
        byTagPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Runnable updateByTagPanel = () -> {
            byTagPanel.removeAll();
            String selectedTag = (String) tagComboBox.getSelectedItem();

            for (Game game : allGames) {
                if ("All".equals(selectedTag) || game.getTags().contains(selectedTag)) {
                    GameMediumContainer mediumContainer = new GameMediumContainer(game);
                    mediumContainer.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            MainWindow.this.goGame(game);
                        }
                    });
                    byTagPanel.add(mediumContainer);
                }
            }

            byTagPanel.revalidate();
            byTagPanel.repaint();
        };

        tagComboBox.addActionListener(e -> updateByTagPanel.run());
        updateByTagPanel.run(); // Initial population

        centralPanel.add(Box.createVerticalStrut(10));
        centralPanel.add(byTagPanel);
        wrapperPanel.add(centralPanel);

        // Right spacer
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(250, 0));
        spacer.setOpaque(false);
        this.add(spacer, BorderLayout.EAST);

        // Scroll pane
        ScrollPaneRound scrollPane = new ScrollPaneRound(wrapperPanel);
        scrollPane.setRoundTopLeft(rad);
        scrollPane.setRoundTopRight(rad);
        scrollPane.setRoundBottomRight(rad);
        scrollPane.setRoundBottomLeft(rad);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        wrapperPanel.setBackground(PANEL_COLOR);
        scrollPane.setBackground(PANEL_COLOR);
        this.add(scrollPane, BorderLayout.CENTER);

        contentPane.revalidate();
        contentPane.repaint();
    }


    public void goLibrary() {
        Container contentPane = this.getContentPane();
        //Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        this.setLayout(new BorderLayout(20, 20));
        this.add(topBar, BorderLayout.NORTH);

        //temporarily fill the other borders with empty space
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(20, 250));
        spacer.setOpaque(false);
        this.add(spacer, BorderLayout.WEST);
        this.add(spacer, BorderLayout.EAST);
        this.add(spacer, BorderLayout.SOUTH);
        this.add(spacer, BorderLayout.CENTER);

        contentPane.revalidate();
        contentPane.repaint();
    }

    public void goGame(Game game) {
        Container contentPane = this.getContentPane();
        //Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        topBar.tabsToInactive();
        this.setLayout(new BorderLayout(20, 20));
        this.add(topBar, BorderLayout.NORTH);

        //center container (left images and description, right banner, price and everything else)
        JPanel centerContainer = new JPanel();
        centerContainer.setOpaque(false);
        centerContainer.setLayout(new BorderLayout(4,4));
        centerContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //left container
            PanelRound leftContainer = new PanelRound();
            int lr = 15;
            leftContainer.setRoundTopLeft(lr);
            leftContainer.setRoundTopRight(lr);
            leftContainer.setRoundBottomRight(lr);
            leftContainer.setRoundBottomLeft(lr);
            leftContainer.setBackground(PANEL_COLOR);
            leftContainer.setLayout(new BorderLayout(0,4));
            leftContainer.setBorder(BorderFactory.createEmptyBorder(0,4,0,4));
            leftContainer.setAlignmentX(CENTER_ALIGNMENT);
            
                JLabel titleLabel = new JLabel("  " + game.getNameGame().toUpperCase());
                titleLabel.setFont(TITLE_FONT);
                titleLabel.setForeground(TEXT_COLOR);
                titleLabel.setAlignmentX(LEFT_ALIGNMENT);
                leftContainer.add(titleLabel, BorderLayout.NORTH);

                JPanel content = new JPanel();
                content.setOpaque(false);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.setAlignmentX(CENTER_ALIGNMENT);
                content.setBorder(BorderFactory.createEmptyBorder(0,3,3,3));

                    MainShowcase showcase = new MainShowcase(game);
                    content.add(showcase);

                    //detail line
                    PanelRound detailLine = new PanelRound();
                    int dlr = 1;
                    detailLine.setRoundTopLeft(dlr);
                    detailLine.setRoundTopRight(dlr);
                    detailLine.setRoundBottomRight(dlr);
                    detailLine.setRoundBottomLeft(dlr);
                    
                    detailLine.setBackground(DETAILS_COLOR);
                    detailLine.setPreferredSize(new Dimension(showcase.getWidth(), 2));
                    detailLine.setAlignmentX(CENTER_ALIGNMENT);
                    content.add(detailLine);

                    //Description TextArea
                    JTextArea descriptionTextArea = new JTextArea(game.getDescription());
                    descriptionTextArea.setFont(DESCRPTION_FONT_PLAIN);
                    descriptionTextArea.setForeground(TEXT_COLOR);
                    descriptionTextArea.setLineWrap(true);
                    descriptionTextArea.setWrapStyleWord(true);
                    descriptionTextArea.setEditable(false);
                    descriptionTextArea.setRows(4); //important?
                    descriptionTextArea.setBackground(PANEL_COLOR);
                    descriptionTextArea.setOpaque(false);
                    descriptionTextArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

                    ScrollPaneRound scrollPane = new ScrollPaneRound(descriptionTextArea);
                    scrollPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
                    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPane.setBackground(PANEL_COLOR);
                    scrollPane.setOpaque(false);
                    scrollPane.getViewport().setOpaque(false);

                    int prefH = scrollPane.getPreferredSize().height;  // this comes from the 4 rows we set
                    scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefH));

                    content.add(scrollPane);

                    //another detail line (copy of the first)
                    PanelRound detailLine2 = new PanelRound();
                    detailLine2.setRoundTopLeft(dlr);
                    detailLine2.setRoundTopRight(dlr);
                    detailLine2.setRoundBottomRight(dlr);
                    detailLine2.setRoundBottomLeft(dlr);
                    
                    detailLine2.setBackground(DETAILS_COLOR);
                    detailLine2.setPreferredSize(new Dimension(showcase.getWidth(), 2));
                    detailLine2.setAlignmentX(CENTER_ALIGNMENT);
                    content.add(detailLine2);

                    JPanel row = new JPanel();
                    row.setOpaque(false);
                    row.setLayout(new GridLayout(1, 4));
                    row.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // --- Publisher Container ---
                        JPanel publisherContainer = new JPanel();
                        publisherContainer.setOpaque(false);
                        publisherContainer.setLayout(new BoxLayout(publisherContainer, BoxLayout.Y_AXIS));
                        publisherContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel publisherLabel = new JLabel("PUBLISHER");
                        publisherLabel.setFont(SUBTITLE_FONT);
                        publisherLabel.setForeground(TEXT_COLOR);
                        publisherLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        publisherContainer.add(publisherLabel);

                        JLabel publisherValue = new JLabel(game.getPublisher().getUsername());
                        publisherValue.setFont(DESCRPTION_FONT);
                        publisherValue.setForeground(TEXT_COLOR);
                        publisherValue.setAlignmentX(Component.CENTER_ALIGNMENT);
                        publisherContainer.add(publisherValue);

                        row.add(publisherContainer);

                        // --- Developer Container ---
                        JPanel developerContainer = new JPanel();
                        developerContainer.setOpaque(false);
                        developerContainer.setLayout(new BoxLayout(developerContainer, BoxLayout.Y_AXIS));
                        developerContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel developerLabel = new JLabel("DEVELOPER");
                        developerLabel.setFont(SUBTITLE_FONT);
                        developerLabel.setForeground(TEXT_COLOR);
                        developerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        developerContainer.add(developerLabel);

                        JLabel developerValue = new JLabel("TO DO"); // or game.getDeveloper().getName()
                        developerValue.setFont(DESCRPTION_FONT);
                        developerValue.setForeground(TEXT_COLOR);
                        developerValue.setAlignmentX(Component.CENTER_ALIGNMENT);
                        developerContainer.add(developerValue);

                        row.add(developerContainer);

                        // --- Rating Panel ---
                        JPanel ratingPanel = new JPanel();
                        ratingPanel.setOpaque(false);
                        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
                        ratingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel ratingLabel = new JLabel();
                        ratingLabel.setIcon(AssetLoader.loadIcon("/images/rating_default.png", 18 * 2, 28 * 2));
                        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        ratingLabel.setPreferredSize(new Dimension(18 * 2, 28 * 2));
                        ratingLabel.setOpaque(false);
                        ratingPanel.add(Box.createVerticalGlue()); // Optional: adds spacing before
                        ratingPanel.add(ratingLabel);
                        ratingPanel.add(Box.createVerticalGlue()); // Optional: adds spacing after

                        row.add(ratingPanel);

                        // --- Release Date Container ---
                        JPanel releaseDateContainer = new JPanel();
                        releaseDateContainer.setOpaque(false);
                        releaseDateContainer.setLayout(new BoxLayout(releaseDateContainer, BoxLayout.Y_AXIS));
                        releaseDateContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel releaseDateLabel = new JLabel("RELEASE DATE");
                        releaseDateLabel.setFont(SUBTITLE_FONT);
                        releaseDateLabel.setForeground(TEXT_COLOR);
                        releaseDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        releaseDateContainer.add(releaseDateLabel);

                        JLabel releaseDateValue = new JLabel(game.getReleaseDate().toString());
                        releaseDateValue.setFont(DESCRPTION_FONT);
                        releaseDateValue.setForeground(TEXT_COLOR);
                        releaseDateValue.setAlignmentX(Component.CENTER_ALIGNMENT);
                        releaseDateContainer.add(releaseDateValue);

                    row.add(releaseDateContainer);
                    content.add(row);
                leftContainer.add(content, BorderLayout.EAST);
            centerContainer.add(leftContainer, BorderLayout.WEST);

            //right container
            PanelRound rightContainer = new PanelRound();
            int rr = 15;
            rightContainer.setRoundTopLeft(rr);
            rightContainer.setRoundTopRight(rr);
            rightContainer.setRoundBottomRight(rr);
            rightContainer.setRoundBottomLeft(rr);

            rightContainer.setBackground(PANEL_COLOR);
            rightContainer.setOpaque(false);
            rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));
            rightContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            rightContainer.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

                //label row
                JPanel labelRow = new JPanel();
                labelRow.setOpaque(false);
                labelRow.setLayout(new FlowLayout( FlowLayout.RIGHT, 8, 12));

                    for (int i = 0; i < game.getTags().size(); i++) {
                        String tag = game.getTags().get(i);
                        TagContainer tagContainer = new TagContainer(tag);
                        labelRow.add(tagContainer);
                        if(labelRow.getPreferredSize().width > (int) (230*1.2)){
                            labelRow.remove(tagContainer);
                        }
                    }
                
                rightContainer.add(labelRow);

                //banner
                JPanel bannerContainer = new JPanel();
                bannerContainer.setOpaque(false);
                bannerContainer.setLayout(new BorderLayout(0,4));
                bannerContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                bannerContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
                bannerContainer.setBackground(PANEL_COLOR);
                    
                    JLabel bannerLabel = new JLabel();
                    bannerLabel.setIcon(AssetLoader.loadIconFromUrl(game.getBanner(), ((int) (230*1.2)), ((int) (107*1.35))));
                    bannerLabel.setPreferredSize(new Dimension(((int) (230*1.2)), ((int) (107*1.35))));
                    bannerContainer.add(bannerLabel, BorderLayout.CENTER);

                rightContainer.add(bannerContainer);

                //User Score Label Container
                JPanel userScoreContainer = new JPanel();
                userScoreContainer.setOpaque(false);
                userScoreContainer.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
                userScoreContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                userScoreContainer.setBackground(PANEL_COLOR);
                    
                    //user score icon
                    JLabel userScoreIcon = new JLabel();
                    userScoreIcon.setIcon(AssetLoader.loadIcon("/images/user.png", 25, 25));
                    userScoreIcon.setOpaque(false);
                    userScoreIcon.setHorizontalAlignment(SwingConstants.CENTER);
                    userScoreIcon.setVerticalAlignment(SwingConstants.CENTER);
                    userScoreIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                    userScoreIcon.setBackground(PANEL_COLOR);
                    userScoreContainer.add(userScoreIcon);

                    //user score label
                    JLabel userScoreLabel = new JLabel();
                    userScoreLabel.setText("USER SCORE:");
                    userScoreLabel.setFont(SUBTITLE_FONT);
                    userScoreLabel.setForeground(TEXT_COLOR);
                    userScoreLabel.setOpaque(false);
                    userScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    userScoreLabel.setVerticalAlignment(SwingConstants.CENTER);
                    userScoreLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                    userScoreLabel.setBackground(PANEL_COLOR);
                    userScoreContainer.add(userScoreLabel);

                    //percentage label
                    JLabel percentageLabel = new JLabel();
                    percentageLabel.setText(game.getPositiveReviews()*100/game.getReviews() + "%");
                    percentageLabel.setFont(DESCRPTION_FONT_PLAIN);
                    percentageLabel.setForeground(TEXT_COLOR);
                    percentageLabel.setOpaque(false);
                    percentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    percentageLabel.setVerticalAlignment(SwingConstants.CENTER);
                    percentageLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                    percentageLabel.setBackground(PANEL_COLOR);
                    userScoreContainer.add(percentageLabel);

                rightContainer.add(userScoreContainer);

                //user score bar (container)
                PanelRound userScoreBar = new PanelRound();
                int urbRad = 2;
                userScoreBar.setRoundTopLeft(urbRad);
                userScoreBar.setRoundTopRight(urbRad);
                userScoreBar.setRoundBottomRight(urbRad);
                userScoreBar.setRoundBottomLeft(urbRad);

                userScoreBar.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
                userScoreBar.setBackground(CLOSE_COLOR);
                userScoreBar.setPreferredSize(new Dimension(((int) (230*1.2)), 4));

                    //user score bar (progress bar)
                    
                    JLabel possitiveProgress = new JLabel("");
                    possitiveProgress.setBackground(DETAILS_COLOR);
                    possitiveProgress.setOpaque(true);
                    possitiveProgress.setPreferredSize(new Dimension((int) (((double) (game.getPositiveReviews()* 230 * 1.2) / game.getReviews())), 4));
                    userScoreBar.add(possitiveProgress);

                    System.out.println("User score: " + (int) (((double) (game.getPositiveReviews()* 230 * 1.2) / game.getReviews())));
                
                rightContainer.add(userScoreBar);



                //temporary bottom spacer
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(0,400));
                spacer.setOpaque(false);
                rightContainer.add(spacer);
                //lowerPanel.add(spacer, BorderLayout.SOUTH);

            centerContainer.add(rightContainer, BorderLayout.EAST);



        this.add(centerContainer, BorderLayout.CENTER);

        //left spacer
        wSpacer.setPreferredSize(new Dimension(168, 0));
        this.add(wSpacer, BorderLayout.WEST);

        //right spacer
        eSpacer.setPreferredSize(new Dimension(168, 0));
        this.add(eSpacer, BorderLayout.EAST);

        contentPane.revalidate();
        contentPane.repaint();


        this.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            applyWindowShape();

            int spacerWidth = getWidth() / 7;
            //if (wSpacer != null) wSpacer.setPreferredSize(new Dimension(spacerWidth, 0));
            //if (eSpacer != null) eSpacer.setPreferredSize(new Dimension(spacerWidth, 0));

            getContentPane().revalidate();
            getContentPane().repaint();
        }
    });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.getContentPane()) {
            this.dispose();
        }
    }
}
