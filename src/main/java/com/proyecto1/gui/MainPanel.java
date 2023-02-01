package com.proyecto1.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.GraphFile;
import com.proyecto1.utils.ImageAsset;

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
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel colsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftCol = new JPanel(new GridLayout(3, 0));
        //leftCol.setBackground(new Color(0x95ffff));

        JPanel centerCol = new JPanel(new GridLayout(3, 0));

        JPanel rightCol = new JPanel(new GridLayout(3, 0));

        ImageAsset catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                leftCol.add(imgPanel);
            }

            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                rightCol.add(imgPanel);
            }
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
        centerCol.add(cargarArchivoBtn);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.ipadx = 80;
        colsPanel.add(leftCol, c);
        c.gridx = 1;
        c.ipadx = 80;
        colsPanel.add(centerCol, c);
        c.gridx = 2;
        colsPanel.add(rightCol,c);

        this.add(colsPanel);
    }
}
