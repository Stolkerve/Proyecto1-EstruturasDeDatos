package com.proyecto1.gui;

import com.proyecto1.utils.AssetsManager;

import javax.swing.*;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    JFrame mainFrame;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        //GraphFile.loadFileDialog();

        var backgroundMusic = AssetsManager.getInstance().getMusic("background-music");
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(0.1f);
            backgroundMusic.play(true);
        }

        initComponents();
    }

    private void initComponents() {
        this.add(new JLabel("Hola mundo"));
        var catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            JLabel label = new JLabel(catKiss.image);
            this.add(label);
        }
    }
}
