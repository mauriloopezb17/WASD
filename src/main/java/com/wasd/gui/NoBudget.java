package com.wasd.gui;

import com.wasd.models.Player;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.concurrent.Flow;

import java.awt.geom.RoundRectangle2D;
public class NoBudget extends SecondaryWindow implements StyleConfig {

    public NoBudget() {
        super(":(", null, new Player("Pancake99"));

        //content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            //temporary no notifications label
            JLabel noNotificationsLabel = new JLabel("NO HAY PRESUPUESTO :(");
            noNotificationsLabel.setFont(DESCRPTION_FONT);
            noNotificationsLabel.setForeground(TEXT_COLOR);
            noNotificationsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noNotificationsLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
            contentPanel.add(noNotificationsLabel);

        this.add(contentPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocation((1980-this.getWidth())/2, (1080-this.getHeight())/2);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight()+25, 10, 10));
        this.setVisible(true);
    }
}
