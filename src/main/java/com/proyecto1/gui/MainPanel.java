package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;
import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    JFrame mainFrame;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * 
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    /**
     * 
     */
    public void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        final Vector<JButton> menuBtns = new Vector<JButton>();
        JButton loadGraphBtn = new JButton("Cargar archivo de almacenes");
        loadGraphBtn.addActionListener(e -> {
            GraphFileDialog.loadFileDialog();

            if (Grafo.getInstance().iniciado) {
                for (JButton btn : menuBtns)
                    btn.setEnabled(true);
            }
        });

        JButton saveGraphBtn = new JButton("Guardar archivo de almacenes");
        saveGraphBtn.addActionListener(e -> {
            GraphFileDialog.saveFileDialog();
        });

        JButton producsStockBtn = new JButton("Stock de productos");
        producsStockBtn.addActionListener(e -> {
            this.addCustomComponent(new ShowStock(this));
        });

        JButton requestBtn = new JButton("Realizar pedido de producto");
        requestBtn.addActionListener(e -> {
        });

        JButton addWearhouseBtn = new JButton("Agregar almacen");
        addWearhouseBtn.addActionListener(e -> {
        });

        JButton addPathBtn = new JButton("Agregar camino a almacen");
        addPathBtn.addActionListener(e -> {
        });

        JButton manageStockBtn = new JButton("Gestionar stock de almacenes");
        manageStockBtn.addActionListener(e -> {
        });

        JButton showGraphBtn = new JButton("Mostrar grafico");
        showGraphBtn.addActionListener(e -> {
        });

        JButton helpBtn = new JButton("???");
        helpBtn.addActionListener(e -> {
            new HelpDialog();
        });

        menuBtns.pushBack(loadGraphBtn);
        menuBtns.pushBack(saveGraphBtn);
        menuBtns.pushBack(producsStockBtn);
        menuBtns.pushBack(requestBtn);
        menuBtns.pushBack(addWearhouseBtn);
        menuBtns.pushBack(addPathBtn);
        menuBtns.pushBack(manageStockBtn);
        menuBtns.pushBack(showGraphBtn);

        if (!Grafo.getInstance().iniciado) {
            for (JButton btn : menuBtns)
                btn.setEnabled(false);
            loadGraphBtn.setEnabled(true);
        }

        JPanel colsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftCol = new JPanel(new GridLayout(3, 0));
        JPanel centerCol = new JPanel(new GridLayout(menuBtns.size() + 1, 0, 0, 10));
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

        for (JButton btn : menuBtns)
            centerCol.add(btn);
        centerCol.add(helpBtn);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.ipadx = 80;
        colsPanel.add(leftCol, c);
        c.gridx = 1;
        c.ipadx = 80;
        colsPanel.add(centerCol, c);
        c.gridx = 2;
        colsPanel.add(rightCol, c);

        this.add(colsPanel);
    }

    void addCustomComponent(CustomComponent c) {
        this.removeAll();
        this.add(c);
        this.repaint();
        this.validate();
    }
}
