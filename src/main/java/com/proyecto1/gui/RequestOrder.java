package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Wearhouse;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

public class RequestOrder extends CustomComponent {
    Vector<Wearhouse> wearhouses; 
    RequestOrder(MainPanel mainPanel) {
        super(mainPanel);
        Border a = this.getBorder();
        System.out.println(a.toString());

        this.wearhouses = Grafo.getInstance().almacenes;
        String[] wearhousesNames = new String[this.wearhouses.size()];
        for (int i = 0; i < this.wearhouses.size(); i++)
            wearhousesNames[i] = this.wearhouses.get(i).name;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ImageAsset leftArrow = AssetsManager.getInstance().getImage("left-arrow");
        JButton backToMenuBtn = new JButton(leftArrow.image);
        backToMenuBtn.addActionListener(e -> this.backToMainMenu());
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(backToMenuBtn);
        topPanel.add(left);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> wearhousesComboBox = new JComboBox<String>(wearhousesNames);
        wearhousesComboBox.setSelectedIndex(-1);
        wearhousesComboBox.addActionListener(e -> {this.onComboBox();});
        right.add(new JLabel("Almacenes"));
        right.add(wearhousesComboBox);
        right.add(new JButton("Realizar pedido"));
        topPanel.add(right);

        this.add(topPanel);

        this.initComponent();
    }

    @Override
    protected void initComponent() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel wearhouseProductsPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createTable(), c);

        c.gridx = 1;
        JButton addProductBtn = new JButton("->");
        addProductBtn.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        wearhouseProductsPanel.add(addProductBtn);

        c.gridx = 2;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createTable(), c);

        this.add(wearhouseProductsPanel);
    }

    JComponent createTable() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        // panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints c2 = new GridBagConstraints();

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("BFS"));

        JPanel tablePanel = new JPanel(new GridLayout());
        JScrollPane sp = new JScrollPane();
        tablePanel.add(sp);

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridy = 0;
        c2.weightx = 1.0;
        panel.add(titlePanel, c2);

        c2.fill = GridBagConstraints.BOTH;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 1.0;

        panel.add(tablePanel, c2);

        return panel;
    }

    void onComboBox() {

    }
}
