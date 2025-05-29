package com.wasd.gui;
import com.wasd.Main;
import com.wasd.models.*;

import javax.swing.*;
import javax.swing.border.Border;

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

    boolean liked;
    boolean disliked;

    int requirement = 0; //0 windows, 1 linux, 2 mac

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
        this.setMinimumSize(new Dimension(1366, 768));
        
        topBar = new TopNavigationBar(this, player);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 53));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                applyWindowShape();
            }
        });

        this.add(topBar, BorderLayout.NORTH);

        this.goHome();
        //this.goGame(allGames.get(0));

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
        Showcase galleryPanel = new Showcase(recommendedGames, this);
        galleryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centralPanel.add(Box.createVerticalStrut(10));

        centralPanel.add(galleryPanel);

        //strut
        centralPanel.add(Box.createVerticalStrut(14));

        //detail line
        PanelRound detailLine = new PanelRound();
        int dlr = 1;
        detailLine.setRoundTopLeft(dlr);
        detailLine.setRoundTopRight(dlr);
        detailLine.setRoundBottomRight(dlr);
        detailLine.setRoundBottomLeft(dlr);
        
        detailLine.setBackground(DETAILS_COLOR);
        detailLine.setPreferredSize(new Dimension(galleryPanel.getWidth(), 2));
        detailLine.setAlignmentX(CENTER_ALIGNMENT);
        detailLine.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));
        centralPanel.add(detailLine);

        // ComboBox Filter UI
        Set<String> allTags = new HashSet<>();
        for (Game game : allGames) {
            for (Tag tag : game.getTags()) {
                allTags.add(tag.getNameTag());
            }
        }

        //for (Game g : allGames) allTags.addAll(g.getTags());

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

        //TEMPORAL TEST LIBRARY
        for (Game game : allGames) {
            if (player.getLibrary().size()<3) player.getLibrary().add(game);
        }

        Container contentPane = this.getContentPane();
        //Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        this.setLayout(new BorderLayout(20, 0));
        this.add(topBar, BorderLayout.NORTH);

        //temporarily fill the other borders with empty space
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(20, 250));
        spacer.setOpaque(false);
        this.add(spacer, BorderLayout.WEST);
        this.add(spacer, BorderLayout.EAST);
        this.add(spacer, BorderLayout.SOUTH);
        this.add(spacer, BorderLayout.CENTER);

        
        JPanel centerContainer = new JPanel();
        centerContainer.setAlignmentY(TOP_ALIGNMENT);
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.setBackground(DETAILS2_COLOR);
        centerContainer.setOpaque(false);

        centerContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        
            PanelRound actualContent = new PanelRound();

            actualContent.setRoundTopLeft(15);
            actualContent.setRoundTopRight(15);
            actualContent.setRoundBottomRight(15);
            actualContent.setRoundBottomLeft(15);

            actualContent.setBackground(PANEL_COLOR);
            actualContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            actualContent.setLayout(new BoxLayout(actualContent, BoxLayout.Y_AXIS));
            actualContent.setAlignmentX(Component.LEFT_ALIGNMENT);
            //actualContent.setAlignmentY(Component.TOP_ALIGNMENT);
            //actualContent.setPreferredSize(new Dimension((int) (this.getWidth()*0.8), 900));

                //library label
                JLabel libraryLabel = new JLabel("LIBRARY" + " (" + player.getLibrary().size() + ")");
                libraryLabel.setFont(TITLE_FONT);
                libraryLabel.setForeground(TEXT_COLOR);
                libraryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                actualContent.add(libraryLabel);

                actualContent.add(Box.createRigidArea(new Dimension(0, 7)));

                //detail line
                PanelRound detailLine = new PanelRound();
                int dlr = 1;
                detailLine.setRoundTopLeft(dlr);
                detailLine.setRoundTopRight(dlr);
                detailLine.setRoundBottomRight(dlr);
                detailLine.setRoundBottomLeft(dlr);
                
                detailLine.setBackground(DETAILS_COLOR);
                detailLine.setPreferredSize(new Dimension(actualContent.getWidth(), 2));
                detailLine.setAlignmentX(CENTER_ALIGNMENT);
                actualContent.add(detailLine);

                actualContent.add(Box.createRigidArea(new Dimension(0, 7)));

                //library container
                JPanel libraryContainer = new JPanel();
                libraryContainer.setOpaque(false);
                libraryContainer.setLayout(new BorderLayout(0,4));
                libraryContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                libraryContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

                    //library
                    JPanel libraryPanel = new JPanel();
                    libraryPanel.setOpaque(false);
                    libraryPanel.setLayout(new GridLayout(0, 4, 10, 10));
                    libraryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    libraryPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                    
                    if(player.getLibrary().size() == 0) {
                        //no games label
                        JLabel noGamesLabel = new JLabel("No games yet...");
                        noGamesLabel.setFont(DESCRPTION_FONT);
                        noGamesLabel.setForeground(TEXT_COLOR);
                        libraryPanel.add(noGamesLabel);
                    }
                    else {
                        //build rows that each contain 4 games
                        for(Game game : player.getLibrary()) {
                            GameBigContainer mediumContainer = new GameBigContainer(game);
                            mediumContainer.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    MainWindow.this.goGame(game);
                                }
                            });
                            libraryPanel.add(mediumContainer);
                        }
                    }

                    libraryContainer.add(libraryPanel, BorderLayout.CENTER);

                actualContent.add(libraryContainer);

                actualContent.add(Box.createRigidArea(new Dimension(0, 500)));
            
            //scrollpane
            ScrollPaneRound scrollPane = new ScrollPaneRound(actualContent);
            int rad = 15;
            scrollPane.setRoundTopLeft(rad);
            scrollPane.setRoundTopRight(rad);
            scrollPane.setRoundBottomRight(rad);
            scrollPane.setRoundBottomLeft(rad);

            scrollPane.setBackground(PANEL_COLOR);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(null);

        centerContainer.add(scrollPane);

        this.add(centerContainer, BorderLayout.CENTER);
    }

    public void goGame(Game game) {
        Container contentPane = this.getContentPane();
        //Component topbar = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        System.out.println("removeAll");
        contentPane.removeAll();
        topBar.tabsToInactive();
        this.setLayout(new BorderLayout(20, 20));
        this.add(topBar, BorderLayout.NORTH);

        JTextArea requirementsTextArea;

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
                titleLabel.setBorder(BorderFactory.createEmptyBorder(6,0,2,0));
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
                    descriptionTextArea.setRows(3); //important?
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

                    int prefH = scrollPane.getPreferredSize().height;  //3 rows
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
                    row.setBorder(BorderFactory.createEmptyBorder(7,0,7,0));
                    row.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // publisher
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

                        //dev
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

                        // rating
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

                        //release date
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

                    for(Tag tag : game.getTags()) {
                        String tagName = tag.getNameTag();
                        TagContainer tagContainer = new TagContainer(tagName);
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

                //recommended container
                JPanel recommendedContainer = new JPanel();
                recommendedContainer.setOpaque(false);
                recommendedContainer.setLayout(new FlowLayout(FlowLayout.CENTER,2,4));
                recommendedContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                recommendedContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

                    //recommended label
                    JLabel recommendedLabel = new JLabel("Do you recommend this game?");
                    recommendedLabel.setFont(DESCRPTION_FONT_PLAIN);
                    recommendedLabel.setForeground(TEXT_COLOR);
                    recommendedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    recommendedContainer.add(recommendedLabel);

                    //recommended button container
                    JPanel recommendedButtonContainer = new JPanel();
                    recommendedButtonContainer.setOpaque(false);
                    recommendedButtonContainer.setLayout(new FlowLayout(FlowLayout.CENTER,4,0));
                    recommendedButtonContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                    recommendedButtonContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

                            //recommended button
                            PanelRound recommendedButton = new PanelRound();
                            int br = 15;
                            recommendedButton.setRoundTopLeft(br);
                            recommendedButton.setRoundTopRight(br);
                            recommendedButton.setRoundBottomRight(br);
                            recommendedButton.setRoundBottomLeft(br);

                            recommendedButton.setBackground(PANEL_COLOR);
                            recommendedButton.setPreferredSize(new Dimension(25, 25));
                            recommendedButton.setLayout(new BorderLayout());
                            recommendedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            recommendedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                            recommendedButton.setBorder(null);
                            recommendedButton.setOpaque(false);

                            JLabel recommendedIcon = new JLabel();
                            recommendedIcon.setIcon(AssetLoader.loadIcon("/images/like_inactive.png", 25, 25));
                            recommendedIcon.setPreferredSize(new Dimension(15, 15));
                            recommendedIcon.setOpaque(false);
                            recommendedIcon.setHorizontalAlignment(SwingConstants.CENTER);
                            recommendedIcon.setVerticalAlignment(SwingConstants.CENTER);
                            recommendedIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                            recommendedButton.add(recommendedIcon, BorderLayout.CENTER);
                            

                        //dislike button
                        PanelRound dislikeButton = new PanelRound();
                        dislikeButton.setRoundTopLeft(br);
                        dislikeButton.setRoundTopRight(br);
                        dislikeButton.setRoundBottomRight(br);
                        dislikeButton.setRoundBottomLeft(br);

                            dislikeButton.setBackground(PANEL_COLOR);
                            dislikeButton.setPreferredSize(new Dimension(25, 25));
                            dislikeButton.setLayout(new BorderLayout());
                            dislikeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            dislikeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                            dislikeButton.setBorder(null);
                            dislikeButton.setOpaque(false);

                            JLabel dislikeIcon = new JLabel();
                            dislikeIcon.setIcon(AssetLoader.loadIcon("/images/dislike_inactive.png", 25, 25));
                            dislikeIcon.setPreferredSize(new Dimension(15, 15));
                            dislikeIcon.setOpaque(false);
                            dislikeIcon.setHorizontalAlignment(SwingConstants.CENTER);
                            dislikeIcon.setVerticalAlignment(SwingConstants.CENTER);
                            dislikeIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                            dislikeButton.add(dislikeIcon, BorderLayout.CENTER);

                            recommendedButton.addMouseListener(new MouseAdapter() {
                                @Override public void mouseEntered(MouseEvent e) {
                                    recommendedIcon.setIcon(AssetLoader.loadIcon("/images/like_active.png", 25, 25));
                                }
                                @Override public void mouseExited(MouseEvent e) {
                                    if (!liked) {
                                        recommendedIcon.setIcon(AssetLoader.loadIcon("/images/like_inactive.png", 25, 25));
                                    }
                                    else {
                                        recommendedIcon.setIcon(AssetLoader.loadIcon("/images/like_active.png", 25, 25));
                                    }
                                }
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    recommendedIcon.setIcon(AssetLoader.loadIcon("/images/like_active.png", 25, 25));
                                    liked = true;
                                    dislikeButton.setVisible(false);
                                }
                            });
                            recommendedButtonContainer.add(recommendedButton);
                        
                            dislikeButton.addMouseListener(new MouseAdapter() {
                                @Override public void mouseEntered(MouseEvent e) {
                                    dislikeIcon.setIcon(AssetLoader.loadIcon("/images/dislike_active.png", 25, 25));
                                }
                                @Override public void mouseExited(MouseEvent e) {
                                    dislikeIcon.setIcon(AssetLoader.loadIcon("/images/dislike_inactive.png", 25, 25));
                                }
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    dislikeIcon.setIcon(AssetLoader.loadIcon("/images/dislike_active.png", 25, 25));
                                    disliked = true;
                                    recommendedButton.setVisible(false);
                                }
                            });
                            recommendedButtonContainer.add(dislikeButton);
                    recommendedContainer.add(recommendedButtonContainer);
                rightContainer.add(recommendedContainer);

                //price container
                JPanel priceContainer = new JPanel();
                priceContainer.setOpaque(false);
                priceContainer.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                priceContainer.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                priceContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

                    //price label
                    JLabel priceLabel = new JLabel();
                    if (game.getPrice() != 0) {
                        priceLabel.setText("$" + game.getPrice());
                    }
                    else {
                        priceLabel.setText("FREE");
                    }
                    priceLabel.setFont(SUBTITLE_FONT);
                    priceLabel.setForeground(TEXT_COLOR);
                    priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    priceContainer.add(priceLabel);

                    //spacer
                    priceContainer.add(Box.createRigidArea(new Dimension(25,0)));

                    //buy button
                    PanelRound buyButton = new PanelRound();
                    buyButton.setRoundTopLeft(br);
                    buyButton.setRoundTopRight(br);
                    buyButton.setRoundBottomRight(br);
                    buyButton.setRoundBottomLeft(br);

                        buyButton.setBackground(DETAILS_COLOR);
                        buyButton.setPreferredSize(new Dimension(100, 40));
                        buyButton.setLayout(new BorderLayout());
                        buyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        buyButton.setBorder(null);
                        buyButton.setOpaque(false);

                        JLabel buyLabel = new JLabel();
                        if(game.getPrice() != 0) {
                            buyLabel.setText("BUY");
                        }
                        else {
                            buyLabel.setText("GET");
                        }
                        buyLabel.setFont(SUBTITLE_FONT);
                        buyLabel.setForeground(TOP_BAR_COLOR);
                        buyLabel.setPreferredSize(new Dimension(150, 50));
                        buyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        buyLabel.setVerticalAlignment(SwingConstants.CENTER); 
                        buyButton.add(buyLabel, BorderLayout.CENTER);
                    
                        buyButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                //TODO
                            }
                        });
                    priceContainer.add(buyButton);

                rightContainer.add(priceContainer);

                //spacer
                rightContainer.add(Box.createRigidArea(new Dimension(0, 40)));

                //wishlist container
                JPanel wishlistContainer = new JPanel();
                wishlistContainer.setOpaque(false);
                wishlistContainer.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
                wishlistContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                wishlistContainer.setPreferredSize(new Dimension(100, 25));

                        //wishlist label
                        JLabel wishlistLabel = new JLabel("WISHLIST");
                        wishlistLabel.setFont(SUBTITLE_FONT);
                        wishlistLabel.setForeground(TEXT_COLOR);
                        wishlistLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        wishlistContainer.add(wishlistLabel);

                        //spacer
                        wishlistContainer.add(Box.createRigidArea(new Dimension(140,0)));
                    
                        //wishlist button
                        PanelRound wishlistButton = new PanelRound();
                        wishlistButton.setRoundTopLeft(7);
                        wishlistButton.setRoundTopRight(7);
                        wishlistButton.setRoundBottomRight(7);
                        wishlistButton.setRoundBottomLeft(7);

                        wishlistButton.setBackground(DETAILS_COLOR);
                        wishlistButton.setPreferredSize(new Dimension(32,32));
                        wishlistButton.setLayout(new BorderLayout());
                        wishlistButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        wishlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        wishlistButton.setBorder(null);
                        //wishlistButton.setOpaque(false);
                        
                            JLabel wishlistIcon = new JLabel();
                            wishlistIcon.setIcon(AssetLoader.loadIcon("/images/add.png", 32,32));
                            wishlistIcon.setPreferredSize(new Dimension(25, 25));
                            wishlistIcon.setOpaque(false);
                            wishlistIcon.setHorizontalAlignment(SwingConstants.CENTER);
                            wishlistIcon.setVerticalAlignment(SwingConstants.CENTER);
                            wishlistIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                            wishlistButton.add(wishlistIcon, BorderLayout.CENTER);
                        
                        wishlistButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                //TODO
                            }
                        });
                        wishlistContainer.add(wishlistButton);
                rightContainer.add(wishlistContainer);

                //System Requirements Container
                JPanel systemRequirementsContainer = new JPanel();
                systemRequirementsContainer.setOpaque(false);
                systemRequirementsContainer.setLayout(new BorderLayout(0,0));
                systemRequirementsContainer.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

                    //Label and buttons row
                    JPanel labelAndButtonsRow = new JPanel();
                    labelAndButtonsRow.setOpaque(false);
                    labelAndButtonsRow.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
                    labelAndButtonsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

                        //gear icon
                        JLabel gearIcon = new JLabel();
                        gearIcon.setIcon(AssetLoader.loadIcon("/images/gear.png", 25, 25));
                        gearIcon.setPreferredSize(new Dimension(18, 18));
                        gearIcon.setOpaque(false);
                        gearIcon.setHorizontalAlignment(SwingConstants.CENTER);
                        gearIcon.setVerticalAlignment(SwingConstants.CENTER);
                        gearIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                        gearIcon.setBackground(PANEL_COLOR);
                        labelAndButtonsRow.add(gearIcon);

                        //requirement label
                        JLabel requirementLabel = new JLabel("  SYSTEM REQUIREMENTS      ");
                        requirementLabel.setFont(DESCRPTION_FONT);
                        requirementLabel.setForeground(TEXT_COLOR);
                        requirementLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        labelAndButtonsRow.add(requirementLabel);

                        //actual system requirements NOT ADDED TO PANEL YET

                        requirementsTextArea = new JTextArea(game.getWindowsRequirement().toString());
                        requirementsTextArea.setFont(DESCRPTION_FONT_PLAIN);
                        requirementsTextArea.setForeground(TEXT_COLOR);
                        requirementsTextArea.setLineWrap(true);
                        requirementsTextArea.setWrapStyleWord(true);
                        requirementsTextArea.setEditable(false);
                        //requirementsTextArea.setRows(4); //important?
                        requirementsTextArea.setBackground(PANEL_COLOR);
                        requirementsTextArea.setOpaque(false);
                        requirementsTextArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                        //button panel
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setOpaque(false);
                        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,4,0));

                            //windows button
                            PanelRound windowsButton = new PanelRound();
                            windowsButton.setRoundTopLeft(br);
                            windowsButton.setRoundTopRight(br);
                            windowsButton.setRoundBottomRight(br);
                            windowsButton.setRoundBottomLeft(br);

                            windowsButton.setBackground(PANEL_COLOR);
                            windowsButton.setPreferredSize(new Dimension(32,32));
                            windowsButton.setLayout(new BorderLayout());
                            windowsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            windowsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                            windowsButton.setBorder(null);
                            //windowsButton.setOpaque(false);
                            
                                JLabel windowsIcon = new JLabel();
                                windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_active.png", 25,25)); //windows is selected by default
                                windowsIcon.setPreferredSize(new Dimension(25, 25));
                                windowsIcon.setOpaque(false);
                                windowsIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                windowsIcon.setVerticalAlignment(SwingConstants.CENTER);
                                windowsIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                                windowsButton.add(windowsIcon, BorderLayout.CENTER);

                                //linux button
                                PanelRound linuxButton = new PanelRound();
                                linuxButton.setRoundTopLeft(br);
                                linuxButton.setRoundTopRight(br);
                                linuxButton.setRoundBottomRight(br);
                                linuxButton.setRoundBottomLeft(br);

                                linuxButton.setBackground(PANEL_COLOR);
                                linuxButton.setPreferredSize(new Dimension(25,25));
                                linuxButton.setLayout(new BorderLayout());
                                linuxButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                linuxButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                                linuxButton.setBorder(null);
                                //linuxButton.setOpaque(false);
                                
                                    JLabel linuxIcon = new JLabel();
                                    linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_inactive.png", 25,25));
                                    linuxIcon.setPreferredSize(new Dimension(25, 25));
                                    linuxIcon.setOpaque(false);
                                    linuxIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                    linuxIcon.setVerticalAlignment(SwingConstants.CENTER);
                                    linuxIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                                    linuxButton.add(linuxIcon, BorderLayout.CENTER);

                                //mac button
                                PanelRound macButton = new PanelRound();
                                macButton.setRoundTopLeft(br);
                                macButton.setRoundTopRight(br);
                                macButton.setRoundBottomRight(br);
                                macButton.setRoundBottomLeft(br);

                                macButton.setBackground(PANEL_COLOR);
                                macButton.setPreferredSize(new Dimension(25,25));
                                macButton.setLayout(new BorderLayout());
                                macButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                macButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                                macButton.setBorder(null);
                                //macButton.setOpaque(false);
                                
                                    JLabel macIcon = new JLabel();
                                    macIcon.setIcon(AssetLoader.loadIcon("/images/mac.png", 25,25));
                                    macIcon.setPreferredSize(new Dimension(25, 25));
                                    macIcon.setOpaque(false);
                                    macIcon.setHorizontalAlignment(SwingConstants.CENTER);
                                    macIcon.setVerticalAlignment(SwingConstants.CENTER);
                                    macIcon.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                                    macButton.add(macIcon, BorderLayout.CENTER);

                                    windowsButton.addMouseListener(new MouseAdapter() {
                                    @Override public void mouseEntered(MouseEvent e) {
                                        windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_active.png", 25,25));
                                    }
                                    @Override public void mouseExited(MouseEvent e) {
                                        if (requirement == 0) {
                                            windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_active.png", 25,25));
                                        }
                                        else {
                                            windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_inactive.png", 25,25));
                                        }
                                    }

                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_active.png", 25,25));
                                        linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_inactive.png", 25,25));
                                        macIcon.setIcon(AssetLoader.loadIcon("/images/mac_inactive.png", 25,25));
                                        requirement = 0;
                                        requirementsTextArea.setText(game.getWindowsRequirement().toString());
                                    }
                                });
                                buttonPanel.add(windowsButton);
                                
                                    linuxButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_active.png", 25,25));
                                        }
                                        @Override public void mouseExited(MouseEvent e) {
                                            if (requirement == 1) {
                                                linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_active.png", 25,25));
                                            }
                                            else {
                                                linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_inactive.png", 25,25));
                                            }
                                        }
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_active.png", 25,25));
                                            windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_inactive.png", 25,25));
                                            macIcon.setIcon(AssetLoader.loadIcon("/images/mac_inactive.png", 25,25));
                                            requirement = 1;
                                            requirementsTextArea.setText(game.getLinuxRequirement().toString());
                                        }
                                    });
                                buttonPanel.add(linuxButton);
                                
                                    macButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseEntered(MouseEvent e) {
                                            macIcon.setIcon(AssetLoader.loadIcon("/images/mac_active.png", 25,25));
                                        }
                                        @Override
                                        public void mouseExited(MouseEvent e) {
                                            if (requirement == 2) {
                                                macIcon.setIcon(AssetLoader.loadIcon("/images/mac_active.png", 25,25));
                                            }
                                            else {
                                                macIcon.setIcon(AssetLoader.loadIcon("/images/mac_inactive.png", 25,25));
                                            }
                                        }
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            macIcon.setIcon(AssetLoader.loadIcon("/images/mac_active.png", 25,25));
                                            windowsIcon.setIcon(AssetLoader.loadIcon("/images/windows_inactive.png", 25,25));
                                            linuxIcon.setIcon(AssetLoader.loadIcon("/images/linux_inactive.png", 25,25));
                                            requirement = 2;
                                            requirementsTextArea.setText(game.getMacRequirement().toString());
                                        }
                                    });
                                buttonPanel.add(macButton);
                            
                            labelAndButtonsRow.add(buttonPanel);
                    systemRequirementsContainer.add(labelAndButtonsRow, BorderLayout.NORTH);

                    ScrollPaneRound requirementsScrollPane = new ScrollPaneRound(requirementsTextArea);
                    requirementsScrollPane.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
                    requirementsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                    requirementsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    requirementsScrollPane.setBackground(PANEL_COLOR);
                    requirementsScrollPane.setOpaque(false);
                    requirementsScrollPane.getViewport().setOpaque(false);

                    requirementsScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, requirementsScrollPane.getPreferredSize().height));

                    systemRequirementsContainer.add(requirementsScrollPane, BorderLayout.SOUTH);

                    System.out.println(game.getWindowsRequirement());

                rightContainer.add(systemRequirementsContainer);


                //temporary bottom spacer
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(0,40));
                spacer.setOpaque(false);
                rightContainer.add(spacer);
                //lowerPanel.add(spacer, BorderLayout.SOUTH);

            centerContainer.add(rightContainer);



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
