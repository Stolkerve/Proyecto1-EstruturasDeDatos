package com.proyecto1.gui;

import com.proyecto1.utils.AssetsManager;

import javax.swing.*;
import java.awt.*;

enum AssetType {
    Video,
    Music,
    Image
}

class AssetInfo {
    String path;
    String name;
    AssetType type;

    AssetInfo(String path, String name, AssetType type) {
        this.path = path;
        this.name = name;
        this.type = type;
    }
}

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    JFrame mainFrame;

    /**
     * Creates new form MainPanel
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        //GraphFile.loadFileDialog();

        this.loadAssets();

        var backgroundMusic = AssetsManager.getInstance().getMusic("background-music");
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(0.1f);
            backgroundMusic.play(true);
        }

        initComponents();
    }

    private void loadAssets() {
        var dialog = new JDialog(this.mainFrame, "Cargando recursos", true);
        dialog.setSize(200, 120);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        var assetLabel = new JLabel();
        var bar = new JProgressBar();
        bar.setValue(0);
        bar.setStringPainted(true);

        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(bar, BorderLayout.CENTER);
        panel.add(assetLabel, BorderLayout.SOUTH);

        dialog.add(panel);

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                var assetsLoadInfo = new AssetInfo[]{
                        new AssetInfo("./assets/cat-kiss.gif", "cat-kiss", AssetType.Image),
                        new AssetInfo("./assets/Amazon_icon.png", "amazon-icon", AssetType.Image),
                        new AssetInfo("./assets/cat-dance.gif", "cat-dance", AssetType.Image),
                        new AssetInfo("./assets/BetterCallSaulRemix.wav", "background-music", AssetType.Music),
                };

                var i = 0;
                for (var assetInfo : assetsLoadInfo) {
                    assetLabel.setText(assetInfo.path);
                    var porc = ((float) (assetsLoadInfo.length - (assetsLoadInfo.length - i)) / assetsLoadInfo.length) * 100;
                    bar.setValue((int) porc);

                    switch (assetInfo.type) {
                        case Image -> {
                            var asset = AssetsManager.getInstance().addImage(assetInfo.path, assetInfo.name);
                            if (asset == null) {
                                JOptionPane.showMessageDialog(null, "No se encontro el recurso " + assetInfo.path, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        case Music -> {
                            if (AssetsManager.getInstance().addMusic(assetInfo.path, assetInfo.name) == null) {
                                JOptionPane.showMessageDialog(null, "No se encontro el recurso " + assetInfo.path, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        case Video -> {}
                    }

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    i++;
                }
                return null;
            }

            @Override
            protected void done() {
                dialog.dispose();//close the modal dialog
            }
        };
        sw.execute();

        dialog.setVisible(true);
    }

    private void initComponents() {
        var icon = AssetsManager.getInstance().getImage("amazon-icon");
        if (icon != null) {
            this.mainFrame.setIconImage(icon.image.getImage());
        }

        this.add(new JLabel("Hola mundo"));
        var catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            JLabel label = new JLabel(catKiss.image);
            this.add(label);
        }
    }
}
