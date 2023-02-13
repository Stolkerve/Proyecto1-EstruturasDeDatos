package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class RequestOrder extends JPanel {
    MainPanel mainPanel;

    RequestOrder(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        this.initComponents();
        this.setPreferredSize(this.mainPanel.mainFrame.getSize());
    }

    void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());

        c.ipady = 5;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("asd"));
        panel.add(topPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        panel.add(separator, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        JPanel centralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centralPanel.add(new JLabel("asd"));
        panel.add(centralPanel, c);

        this.add(panel);
    }

    void backMainPanel() {

    }
}
