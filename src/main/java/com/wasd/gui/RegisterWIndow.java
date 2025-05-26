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
import com.wasd.models.User;
import com.wasd.database.PlayerDAO;
import com.wasd.database.UserDAO;
import com.wasd.models.Role;
import com.wasd.services.UserService;

import org.elasticsearch.common.recycler.Recycler.C;

import javax.swing.border.AbstractBorder;

public class RegisterWindow extends SecondaryWindow implements StyleConfig {

    public RegisterWindow(ArrayList<Game> games, ArrayList<Game> recommendedGames) {
        super("WASD - Register");
        this.setSize(new Dimension(300, 900));
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 10, 10));

        //big Container
        JPanel bigContainer = new JPanel();
        bigContainer.setOpaque(false);
        bigContainer.setLayout(new BorderLayout(4,4));
        bigContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //logo container
            JPanel logoContainer = new JPanel();
            logoContainer.setOpaque(false);
            logoContainer.setPreferredSize(new Dimension(150, 150));
            logoContainer.setAlignmentX(CENTER_ALIGNMENT);

            JLabel logoLabel = new JLabel();
            logoLabel.setIcon(AssetLoader.loadIcon("/images/logo.png", 150, 150));
            logoLabel.setPreferredSize(new Dimension(150, 150));
            logoLabel.setOpaque(false);
            logoContainer.add(logoLabel);

            bigContainer.add(logoContainer, BorderLayout.NORTH);


            //Register panel container
            PanelRound registerPanelContainer = new PanelRound();
            int rad = 15;
            registerPanelContainer.setRoundTopLeft(rad);
            registerPanelContainer.setRoundTopRight(rad);
            registerPanelContainer.setRoundBottomRight(rad);
            registerPanelContainer.setRoundBottomLeft(rad);

            registerPanelContainer.setBackground(PANEL_COLOR);
            registerPanelContainer.setLayout(new BorderLayout(0,4));
            registerPanelContainer.setAlignmentX(CENTER_ALIGNMENT);
            registerPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel registerLabel = new JLabel("Create a WASD account");
            registerLabel.setFont(SUBTITLE_FONT);
            registerLabel.setForeground(TEXT_COLOR);
            registerLabel.setAlignmentX(CENTER_ALIGNMENT);
            registerPanelContainer.add(registerLabel, BorderLayout.NORTH);

            //Register panel
            JPanel registerPanel = new JPanel();

            registerPanel.setBackground(PANEL_COLOR);
            registerPanel.setLayout(new GridLayout(0, 1, 0, 10));
            registerPanel.setPreferredSize(new Dimension(300,300));
            registerPanel.setAlignmentX(LEFT_ALIGNMENT);
            registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                //email container
                JPanel emailContainer = new JPanel();
                emailContainer.setOpaque(false);
                emailContainer.setLayout(new BorderLayout(0,4));
                emailContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //email
                JLabel emailLabel = new JLabel("Email");
                emailLabel.setFont(DESCRPTION_FONT);
                emailLabel.setForeground(TEXT_COLOR);
                emailLabel.setAlignmentX(CENTER_ALIGNMENT);
                emailLabel.setPreferredSize(new Dimension(200, 30));
                emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
                emailContainer.add(emailLabel, BorderLayout.NORTH);

                JTextField emailTextField = new JTextField();
                emailTextField.setFont(DESCRPTION_FONT);
                emailTextField.setForeground(TEXT_COLOR);
                emailTextField.setBackground(BG_COLOR);
                emailTextField.setCaretColor(TEXT_COLOR);
                emailTextField.setSelectionColor(DETAILS_COLOR);
                emailTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
                emailTextField.setPreferredSize(new Dimension(200, 35));
                emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
                //emailFieldContainer.add(emailTextField, BorderLayout.CENTER);

                emailContainer.add(emailTextField);
                registerPanel.add(emailContainer);

                //username container
                JPanel usernameContainer = new JPanel();
                usernameContainer.setOpaque(false);
                usernameContainer.setLayout(new BorderLayout(0,4));
                usernameContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //username
                JLabel usernameLabel = new JLabel("Username");
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
                registerPanel.add(usernameContainer);

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
                registerPanel.add(passwordContainer);

                //confirm password container
                JPanel confirmPasswordContainer = new JPanel();
                confirmPasswordContainer.setOpaque(false);
                confirmPasswordContainer.setLayout(new BorderLayout(0,4));
                confirmPasswordContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //confirm password
                JLabel confirmPasswordLabel = new JLabel("Confirm Password");
                confirmPasswordLabel.setFont(DESCRPTION_FONT);
                confirmPasswordLabel.setForeground(TEXT_COLOR);
                confirmPasswordLabel.setAlignmentX(CENTER_ALIGNMENT);
                confirmPasswordLabel.setPreferredSize(new Dimension(200, 30));
                confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
                confirmPasswordContainer.add(confirmPasswordLabel, BorderLayout.NORTH);

                JPasswordField confirmPasswordTextField = new JPasswordField();
                confirmPasswordTextField.setFont(DESCRPTION_FONT);
                confirmPasswordTextField.setForeground(TEXT_COLOR);
                confirmPasswordTextField.setBackground(BG_COLOR);
                confirmPasswordTextField.setCaretColor(TEXT_COLOR);
                confirmPasswordTextField.setSelectionColor(DETAILS_COLOR);
                confirmPasswordTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
                confirmPasswordTextField.setPreferredSize(new Dimension(200, 35));
                confirmPasswordTextField.setHorizontalAlignment(SwingConstants.LEFT);
                //confirmPasswordFieldContainer.add(confirmPasswordTextField, BorderLayout.CENTER);
                confirmPasswordContainer.add(confirmPasswordTextField);
                registerPanel.add(confirmPasswordContainer);

                //name container
                JPanel nameContainer = new JPanel();
                nameContainer.setOpaque(false);
                nameContainer.setLayout(new BorderLayout(0,4));
                nameContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //name
                JLabel nameLabel = new JLabel("Name");
                nameLabel.setFont(DESCRPTION_FONT);
                nameLabel.setForeground(TEXT_COLOR);
                nameLabel.setAlignmentX(CENTER_ALIGNMENT);
                nameLabel.setPreferredSize(new Dimension(200, 30));
                nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                nameContainer.add(nameLabel, BorderLayout.NORTH);

                JTextField nameTextField = new JTextField();
                nameTextField.setFont(DESCRPTION_FONT);
                nameTextField.setForeground(TEXT_COLOR);
                nameTextField.setBackground(BG_COLOR);
                nameTextField.setCaretColor(TEXT_COLOR);
                nameTextField.setSelectionColor(DETAILS_COLOR);
                nameTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
                nameTextField.setPreferredSize(new Dimension(200, 35));
                nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
                //nameFieldContainer.add(nameTextField, BorderLayout.CENTER);
                nameContainer.add(nameTextField);
                registerPanel.add(nameContainer);

                //last name container
                JPanel lastNameContainer = new JPanel();
                lastNameContainer.setOpaque(false);
                lastNameContainer.setLayout(new BorderLayout(0,4));
                lastNameContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //last name
                JLabel lastNameLabel = new JLabel("Last Name");
                lastNameLabel.setFont(DESCRPTION_FONT);
                lastNameLabel.setForeground(TEXT_COLOR);
                lastNameLabel.setAlignmentX(CENTER_ALIGNMENT);
                lastNameLabel.setPreferredSize(new Dimension(200, 30));
                lastNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                lastNameContainer.add(lastNameLabel, BorderLayout.NORTH);

                JTextField lastNameTextField = new JTextField();
                lastNameTextField.setFont(DESCRPTION_FONT);
                lastNameTextField.setForeground(TEXT_COLOR);
                lastNameTextField.setBackground(BG_COLOR);
                lastNameTextField.setCaretColor(TEXT_COLOR);
                lastNameTextField.setSelectionColor(DETAILS_COLOR);
                lastNameTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
                lastNameTextField.setPreferredSize(new Dimension(200, 35));
                lastNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
                //lastNameFieldContainer.add(lastNameTextField, BorderLayout.CENTER);                
                lastNameContainer.add(lastNameTextField);
                registerPanel.add(lastNameContainer);

                //country container
                JPanel countryContainer = new JPanel();
                countryContainer.setOpaque(false);
                countryContainer.setLayout(new BorderLayout(0,4));
                countryContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
                //country
                JLabel countryLabel = new JLabel("Country");
                countryLabel.setFont(DESCRPTION_FONT);
                countryLabel.setForeground(TEXT_COLOR);
                countryLabel.setAlignmentX(CENTER_ALIGNMENT);
                countryLabel.setPreferredSize(new Dimension(200, 30));
                countryLabel.setHorizontalAlignment(SwingConstants.LEFT);
                countryContainer.add(countryLabel, BorderLayout.NORTH);

                JTextField countryTextField = new JTextField();
                countryTextField.setFont(DESCRPTION_FONT);
                countryTextField.setForeground(TEXT_COLOR);
                countryTextField.setBackground(BG_COLOR);
                countryTextField.setCaretColor(TEXT_COLOR);
                countryTextField.setSelectionColor(DETAILS_COLOR);
                countryTextField.setBorder(new RoundedBorder(10, DETAILS_COLOR));
                countryTextField.setPreferredSize(new Dimension(200, 35));
                countryTextField.setHorizontalAlignment(SwingConstants.LEFT);
                //countryFieldContainer.add(countryTextField, BorderLayout.CENTER);
                countryContainer.add(countryTextField);
                registerPanel.add(countryContainer);
                
                //button panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
                buttonPanel.setPreferredSize(new Dimension(200, 30));

                    PanelRound LoginButton = new PanelRound();
                        int br = 5;
                        LoginButton.setRoundTopLeft(br);
                        LoginButton.setRoundTopRight(br);
                        LoginButton.setRoundBottomRight(br);
                        LoginButton.setRoundBottomLeft(br);
                        LoginButton.setBorderColor(DETAILS_COLOR);
                        LoginButton.setBorderStroke(2);
                        LoginButton.setBackground(PANEL_COLOR);
                        LoginButton.setPreferredSize(new Dimension(150, 30));
                        LoginButton.setLayout(new BorderLayout());
                        LoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        LoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        LoginButton.setBorder(null);
                        LoginButton.setOpaque(false);

                        JLabel LoginLabel = new JLabel("Have an account?");
                        LoginLabel.setFont(SUBTITLE2_FONT);
                        LoginLabel.setForeground(TEXT_COLOR);
                        LoginLabel.setPreferredSize(new Dimension(180, 30));
                        LoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        LoginLabel.setVerticalAlignment(SwingConstants.CENTER); 
                        LoginButton.add(LoginLabel, BorderLayout.CENTER);
                    
                        LoginButton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                LoginButton.setBackground(TEXT_COLOR);
                                LoginLabel.setForeground(PANEL_COLOR);
                                LoginLabel.setText("Log In!");
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                LoginButton.setBackground(PANEL_COLOR);
                                LoginLabel.setForeground(TEXT_COLOR);
                                LoginLabel.setText("Have an account?");
                            }

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                new LoginWindow(games, recommendedGames);
                                RegisterWindow.this.dispose();
                            }
                        });
                    buttonPanel.add(LoginButton);

                    PanelRound createAccountButton = new PanelRound();
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

                        JLabel createAccountLabel = new JLabel("Create Account");
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
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                createAccountButton.setBackground(PANEL_COLOR);
                                createAccountLabel.setForeground(TEXT_COLOR);
                            }

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                UserDAO userDAO = new UserDAO();
                                boolean emailValid = false, usernameValid = false, passwordValid = false;
                                //verify email
                                String email = emailTextField.getText();
                                if (!(email.contains("@gmail") || email.contains("@hotmail") || email.contains("@outlook") || email.contains("@yahoo") || email.contains("@ucb"))) {
                                    emailLabel.setText("Invalid email");
                                    emailLabel.setForeground(CLOSE_COLOR);
                                }
                                else {
                                    //repeated email
                                    if (userDAO.repetitiveEmail(email)) {
                                        emailLabel.setText("Email already registered");
                                        emailLabel.setForeground(CLOSE_COLOR);
                                    }
                                    else {
                                        emailValid = true;
                                        emailLabel.setText("Email");
                                    }
                                }

                                // verify username
                                String username = usernameTextField.getText();
                                if (username.length() < 5) {
                                    usernameLabel.setText("Username must be at least 5 characters");
                                    usernameLabel.setForeground(CLOSE_COLOR);
                                }
                                else {
                                    //repeated username
                                    if (userDAO.repetitiveUserName(username)) {
                                        usernameLabel.setText("Username already registered");
                                        usernameLabel.setForeground(CLOSE_COLOR);
                                    }
                                    else {
                                        usernameValid = true;
                                    }
                                }

                                // verify password
                                String password = new String(passwordTextField.getPassword());
                                String confirmPassword = new String(confirmPasswordTextField.getPassword());

                                if (!password.equals(confirmPassword)){
                                    passwordLabel.setText("Passwords don't match");
                                    passwordLabel.setForeground(CLOSE_COLOR);
                                }
                                else {
                                    if (password.length() < 8) {
                                        passwordLabel.setText("Password must be at least 8 characters");
                                        passwordLabel.setForeground(CLOSE_COLOR);
                                    }
                                    else {
                                        passwordValid = true;
                                    }
                                }

                                if(passwordValid && emailValid && usernameValid && nameTextField.getText().length() > 0 && lastNameTextField.getText().length() > 0 && countryTextField.getText().length() > 0) {
                                    PasswordHashUtil passwordHashUtil = new PasswordHashUtil();
                                    String hashedPassword = passwordHashUtil.hashPassword(password);
                                    Player player = new Player(email, username, hashedPassword, nameTextField.getText(), lastNameTextField.getText(), countryTextField.getText());
                                    PlayerDAO playerDAO = new PlayerDAO();
                                    playerDAO.createPlayer(player);
                                }
                            }
                        });
                    buttonPanel.add(createAccountButton);

                registerPanel.add(buttonPanel);


        registerPanelContainer.add(registerPanel, BorderLayout.CENTER);
        bigContainer.add(registerPanelContainer, BorderLayout.CENTER);
        //Login panel

        this.add(bigContainer, BorderLayout.CENTER);
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