package com.proyecto1.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

public class CustomComponent extends JPanel {
    MainPanel mainMenuPanel;

    protected CustomComponent(MainPanel mainMenuPanel, String title) {
        this.mainMenuPanel = mainMenuPanel;
        this.initSize();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ImageAsset leftArrow = AssetsManager.getInstance().getImage("left-arrow");
        JButton backToMenuBtn = new JButton(leftArrow.image);
        backToMenuBtn.addActionListener(e -> this.backToMainMenu());
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BoxLayout(topPanel, BoxLayout.X_AXIS) );
        topPanel.add(backToMenuBtn);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(new JLabel(title));
        topPanel.add(Box.createHorizontalGlue());

        this.add(topPanel);

        this.initComponent();
    }

    protected CustomComponent(MainPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
        this.initSize();
    }

    protected void initComponent() {}

    protected void backToMainMenu() {
        this.mainMenuPanel.removeAll();
        this.mainMenuPanel.initComponents();
        this.mainMenuPanel.repaint();
        this.mainMenuPanel.validate();
    }

    private void initSize() {
        Dimension d = this.mainMenuPanel.mainFrame.getSize();
        this.setPreferredSize(new Dimension(d.width, d.height));
        this.setSize(new Dimension(d.width, d.height));
        this.setMaximumSize(new Dimension(d.width, d.height));
        this.setMinimumSize(new Dimension(d.width, d.height));
    }
}
