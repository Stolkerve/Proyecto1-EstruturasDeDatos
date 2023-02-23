package com.proyecto1.gui;

import com.proyecto1.MainFrame;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;
import com.proyecto1.containers.Graph;
import com.proyecto1.containers.Vector;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    MainFrame mainFrame;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * 
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    public void initComponents() {
        this.setLayout(new GridLayout());
        Graph graph = Graph.getInstance();

        final Vector<JButton> menuBtns = new Vector<JButton>();

        JButton loadGraphBtn = new JButton("Cargar archivo de almacenes");
        loadGraphBtn.addActionListener(e -> {
            GraphFileDialog.loadFileDialog();

            if (graph.init) {
                for (JButton btn : menuBtns)
                    btn.setEnabled(true);
            }
        });

        JButton saveGraphBtn = new JButton("Guardar archivo de almacenes");
        saveGraphBtn.addActionListener(e -> {
            GraphFileDialog.saveFileDialog();
        });

        JButton producsStockBtn = new JButton("Reporte de stock de productos");
        producsStockBtn.addActionListener(e -> {
            this.addMenuComponent(new ShowStockMenu(this));
        });

        JButton requestBtn = new JButton("Realizar pedido de producto");
        requestBtn.addActionListener(e -> {
            this.addMenuComponent(new RequestOrderMenu(this));
        });

        JButton addWearhouseBtn = new JButton("Agregar almacen");
        addWearhouseBtn.addActionListener(e -> {
            this.addMenuComponent(new AgregarAlmacen());
        });

        JButton addPathBtn = new JButton("Agregar camino a almacen");
        addPathBtn.addActionListener(e -> {
            this.addMenuComponent(new AgregarRuta());
        });

        JButton manageStockBtn = new JButton("Gestionar stock de almacenes");
        manageStockBtn.addActionListener(e -> {
            this.addMenuComponent(new ModificarStock());
            
        });

        JButton addProdutsToWearhouseBtn = new JButton("Agregar producto");
        addProdutsToWearhouseBtn.addActionListener(e -> {
            this.addMenuComponent(new AgregarProducto());
        });

        JButton showGraphBtn = new JButton("Mostrar mapa de almacenes");
        showGraphBtn.addActionListener(e -> {
            MenuComponent a = new GraphTheGraphMenu(this);
            this.addMenuComponent(a);
        });

        JButton helpBtn = new JButton("???");
        helpBtn.addActionListener(e -> {
            new HelpDialog();
        });

        menuBtns.pushBack(new JButton[] {
            loadGraphBtn, saveGraphBtn, producsStockBtn,
            requestBtn, addWearhouseBtn, addPathBtn,
            manageStockBtn, addProdutsToWearhouseBtn, showGraphBtn
        });

        if (!graph.init) {
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

        if (graph.needsSave) {
            JOptionPane.showMessageDialog(this, "El repositio de almacenes se modifico, recuerda guardarlo!");
            graph.needsSave = false;
        }
    }

    void addMenuComponent(JPanel c) {
        this.removeAll();
        this.add(c);
        this.repaint();
        this.validate();
    }
}
