package com.proyecto1.gui;

import com.proyecto1.utils.GraphFile;
import com.proyecto1.utils.MusicAsset;
import com.proyecto1.utils.MusicAssets;

import javax.sound.sampled.FloatControl;
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
        var backgroundMusic = MusicAssets.getInstance().add("./assets/BetterCallSaulRemix.wav", "saul");
        backgroundMusic.play(true);
        backgroundMusic.setVolume(0.4f);

        this.mainFrame = mainFrame;
        GraphFile.loadFileDialog();
        initComponents();
    }

    private void initComponents() {
        this.add(new JLabel("Hola mundo"));
        Icon img = new ImageIcon("./assets/cat-kiss.gif");
        JLabel label = new JLabel(img);
        this.add(label);
    }
}
