package com.wasd.gui;

import com.wasd.models.Game;
import com.wasd.models.Player;
import com.wasd.models.User;
import com.wasd.services.UserService;
import com.wasd.utils.PasswordHashUtil;
import com.wasd.models.*;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.LineBorder;
import javax.swing.border.AbstractBorder;

import com.wasd.SessionManager;

public class LoginWindow extends SecondaryWindow implements StyleConfig {

    public LoginWindow(ArrayList<Game> games, ArrayList<Game> recommendedGames) {
        super("WASD - Login");

        //logo
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(AssetLoader.loadIcon("/images/logo.png", 200, 200));
        logoLabel.setPreferredSize(new Dimension(200, 200));
        logoLabel.setOpaque(false);

        PanelRound loginPanel = new PanelRound();
        int rad = 15;
        loginPanel.setRoundTopLeft(rad);
        loginPanel.setRoundTopRight(rad);
        loginPanel.setRoundBottomRight(rad);
        loginPanel.setRoundBottomLeft(rad);


        loginPanel.setBackground(PANEL_COLOR);
        loginPanel.setLayout(new GridLayout(0, 1, 0, 10));
        loginPanel.setPreferredSize(new Dimension(300,300));
        loginPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //big label
        JLabel bigLabel = new JLabel("Login to your WASD account");
        bigLabel.setFont(SUBTITLE_FONT);
        bigLabel.setForeground(TEXT_COLOR);
        bigLabel.setAlignmentX(CENTER_ALIGNMENT);
        loginPanel.add(bigLabel);

        //username container
        JPanel usernameContainer = new JPanel();
        usernameContainer.setOpaque(false);
        usernameContainer.setLayout(new BorderLayout(0,4));
        usernameContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //username
        JLabel usernameLabel = new JLabel("Username or Email");
        usernameLabel.setFont(DESCRPTION_FONT);
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        usernameLabel.setPreferredSize(new Dimension(200, 30));
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usernameContainer.add(usernameLabel, BorderLayout.NORTH);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setFont(DESCRPTION_FONT);
        usernameTextField.setForeground(TEXT_COLOR);
        usernameTextField.setBackground(BG_COLOR);
        usernameTextField.setCaretColor(TEXT_COLOR);
        usernameTextField.setSelectionColor(DETAILS_COLOR);
        usernameTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
        usernameTextField.setPreferredSize(new Dimension(200, 35));
        usernameTextField.setHorizontalAlignment(SwingConstants.LEFT);
        //usernameFieldContainer.add(usernameTextField, BorderLayout.CENTER);

        usernameContainer.add(usernameTextField);
        loginPanel.add(usernameContainer);

        //password container
        JPanel passwordContainer = new JPanel();
        passwordContainer.setOpaque(false);
        passwordContainer.setLayout(new BorderLayout(0,4));
        passwordContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(DESCRPTION_FONT);
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setAlignmentX(CENTER_ALIGNMENT);
        passwordLabel.setPreferredSize(new Dimension(200, 30));
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordContainer.add(passwordLabel, BorderLayout.NORTH);

        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setFont(DESCRPTION_FONT);
        passwordTextField.setForeground(TEXT_COLOR);
        passwordTextField.setBackground(BG_COLOR);
        passwordTextField.setCaretColor(TEXT_COLOR);
        passwordTextField.setSelectionColor(DETAILS_COLOR);
        passwordTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
        passwordTextField.setPreferredSize(new Dimension(200, 35));
        passwordTextField.setHorizontalAlignment(SwingConstants.LEFT);
        //passwordFieldContainer.add(passwordTextField, BorderLayout.CENTER);

        passwordContainer.add(passwordTextField);
        loginPanel.add(passwordContainer);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 7));

            JLabel errorLabel = new JLabel();
            errorLabel.setFont(DESCRPTION_FONT);
            errorLabel.setForeground(CLOSE_COLOR);
            errorLabel.setPreferredSize(new Dimension(180, 30));
            errorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            errorLabel.setVerticalAlignment(SwingConstants.CENTER); 
            buttonPanel.add(errorLabel);
        
            PanelRound createAccountButton = new PanelRound();
            int br = 5;
            createAccountButton.setRoundTopLeft(br);
            createAccountButton.setRoundTopRight(br);
            createAccountButton.setRoundBottomRight(br);
            createAccountButton.setRoundBottomLeft(br);
            createAccountButton.setBorderColor(DETAILS_COLOR);
            createAccountButton.setBorderStroke(2);
            createAccountButton.setBackground(PANEL_COLOR);
            createAccountButton.setPreferredSize(new Dimension(150, 30));
            createAccountButton.setLayout(new BorderLayout());
            createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            createAccountButton.setBorder(null);
            createAccountButton.setOpaque(false);

            JLabel createAccountLabel = new JLabel("New to WASD?");
            createAccountLabel.setFont(SUBTITLE2_FONT);
            createAccountLabel.setForeground(TEXT_COLOR);
            createAccountLabel.setPreferredSize(new Dimension(150, 30));
            createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            createAccountLabel.setVerticalAlignment(SwingConstants.CENTER); 
            createAccountButton.add(createAccountLabel, BorderLayout.CENTER);
        
            createAccountButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    createAccountButton.setBackground(TEXT_COLOR);
                    createAccountLabel.setForeground(PANEL_COLOR);
                    createAccountLabel.setText("Create Account!");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    createAccountButton.setBackground(PANEL_COLOR);
                    createAccountLabel.setForeground(TEXT_COLOR);
                    createAccountLabel.setText("New to WASD?");
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    new RegisterWindow(games, recommendedGames);
                    LoginWindow.this.dispose();
                }
            });
        buttonPanel.add(createAccountButton);

        PanelRound loginButton = new PanelRound();
        loginButton.setRoundTopLeft(br);
        loginButton.setRoundTopRight(br);
        loginButton.setRoundBottomRight(br);
        loginButton.setRoundBottomLeft(br);
        loginButton.setBorderColor(DETAILS_COLOR);
        loginButton.setBorderStroke(2);
        loginButton.setBackground(PANEL_COLOR);
        loginButton.setPreferredSize(new Dimension(70, 30));
        loginButton.setLayout(new BorderLayout());
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBorder(null);
        loginButton.setOpaque(false);

        JLabel loginLabel = new JLabel("Log In");
        loginLabel.setFont(SUBTITLE2_FONT);
        loginLabel.setForeground(TEXT_COLOR);
        loginLabel.setPreferredSize(new Dimension(50, 30));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setVerticalAlignment(SwingConstants.CENTER); 
        loginButton.add(loginLabel, BorderLayout.CENTER);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(TEXT_COLOR);
                loginLabel.setForeground(PANEL_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(PANEL_COLOR);
                loginLabel.setForeground(TEXT_COLOR);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordTextField.getPassword());

                UserService userService = new UserService();
                User user = userService.login(username, password);
                if (user != null && user.getRole() == Role.PLAYER) {
                    Player player = userService.loginPlayer(username, password);
                    new MainWindow(games, recommendedGames, player);
                } else {
                    errorLabel.setText("Invalid username or password");
                }
            }
        });
        buttonPanel.add(loginButton);

        loginPanel.add(buttonPanel);

        this.add(logoLabel, BorderLayout.WEST);
        this.add(loginPanel, BorderLayout.CENTER);

        //east and south spacers
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(4,4));
        spacer.setOpaque(false);
        this.add(spacer, BorderLayout.EAST);

        JPanel spacer2 = new JPanel();
        spacer2.setPreferredSize(new Dimension(4,4));
        spacer2.setOpaque(false);
        this.add(spacer2, BorderLayout.SOUTH);

        this.pack();
        this.setSize(700, this.getHeight()+25);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight()+25, 10, 10));

        this.setLocation((1980-this.getWidth())/2, (1080-this.getHeight())/2);

        this.setVisible(true);
    }
}

class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 10, 5, 10); // Padding inside the border
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(5, 10, 5, 10);
        return insets;
    }
}