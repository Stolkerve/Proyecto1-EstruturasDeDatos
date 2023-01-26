package com.proyecto1.gui;

import com.proyecto1.utils.GraphFile;

import javax.swing.*;

/**
 *
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    JFrame mainFrame;

    /**
     * Creates new form MainPanel
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        GraphFile.loadFileDialog();
        initComponents();
    }

    private void initComponents() {
        this.add(new JLabel("Hola mundo"));
    }
}
