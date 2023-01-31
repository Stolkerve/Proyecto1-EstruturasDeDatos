package com.proyecto1.gui;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.GraphFile;
import com.proyecto1.utils.ImageAsset;

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

        initComponents();
    }

    private void initComponents() {
        ImageAsset catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            JLabel label = new JLabel(catKiss.image);
            this.add(label);
        }
        JButton cargarArchivoBtn = new JButton("Cargar archivo de almacenes");
        cargarArchivoBtn.addActionListener(e -> {
            cargarArchivoBtn.setEnabled(false);
            SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    GraphFile.loadFileDialog();
                    return null;
                }
                
                @Override
                protected void done() {
                    cargarArchivoBtn.setEnabled(true);
                }
            };
            sw.execute();
        });
        this.add(cargarArchivoBtn);
    }
}
