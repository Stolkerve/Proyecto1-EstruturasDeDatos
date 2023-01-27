package com.proyecto1;

import javax.swing.*;

import com.proyecto1.gui.MainPanel;

/**
 * @author sebas
 */
public class MainFrame extends javax.swing.JFrame {
    final int MIN_WIDTH = 960;
    final int MIN_HEIGHT = 720;

    JPanel mainPanel;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        this.mainPanel = new MainPanel(this);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        setPreferredSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        setSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));

        this.add(mainPanel);

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            var frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
