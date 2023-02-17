package com.proyecto1.gui;

import javax.swing.JPanel;
import java.awt.*;

public class CustomComponent extends JPanel {
    MainPanel mainMenuPanel;

    protected CustomComponent(MainPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;

        Dimension d = this.mainMenuPanel.mainFrame.getSize();
        this.setPreferredSize(new Dimension(d.width, d.height - 42));
        this.initComponent();
    }

    protected void initComponent() {}

    protected void backToMainMenu() {
        this.mainMenuPanel.removeAll();
        this.mainMenuPanel.initComponents();
        this.mainMenuPanel.repaint();
        this.mainMenuPanel.validate();
    }
}
