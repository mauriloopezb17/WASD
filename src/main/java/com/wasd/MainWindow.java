package com.wasd;
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
        this.setLayout(new BorderLayout());
        this.add(topbar, BorderLayout.NORTH);
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
                    byTagPanel.add(new GameMediumContainer(game));
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
