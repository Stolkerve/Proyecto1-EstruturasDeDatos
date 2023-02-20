package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

public class RequestOrder extends CustomComponent {
    Vector<Wearhouse> wearhouses; 
    JList<String> wearhouseProductsList;
    DefaultListModel<String> wearhouseProductsListModel;
    JList<String> orderProductsList;
    JComboBox<String> wearhousesComboBox;
    Pattern productListPattern;

    RequestOrder(MainPanel mainPanel) {
        super(mainPanel);
        this.productListPattern = Pattern.compile("([a-zA-Z0-9]+)\\(([0-9]+)\\)"); 

        this.setBorder(BorderFactory.createEmptyBorder(-5,0,0,0));

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
        this.wearhousesComboBox = new JComboBox<String>(wearhousesNames);
        wearhousesComboBox.setSelectedIndex(-1);
        wearhousesComboBox.addActionListener(e -> {this.onWearhousesSelectionComboBox();});
        right.add(new JLabel("Almacenes"));
        right.add(wearhousesComboBox);
        JButton finishOrderBtn = new JButton("Realizar pedido");
        finishOrderBtn.addActionListener(e -> this.onFinishOrder());
        right.add(finishOrderBtn);
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
        wearhouseProductsPanel.add(this.createList(true), c);

        c.gridx = 1;
        JButton addProductBtn = new JButton("->");
        addProductBtn.addActionListener(e -> this.onAddProductToOrderList());
        addProductBtn.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        wearhouseProductsPanel.add(addProductBtn);

        c.gridx = 2;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createList(false), c);

        this.add(wearhouseProductsPanel);
    }

    JComponent createList(boolean left) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel listPanel = new JPanel(new GridLayout());
        JScrollPane sp = new JScrollPane();
        if (left) {
            this.wearhouseProductsList = new JList<>();
            sp.setViewportView(this.wearhouseProductsList);
            titlePanel.add(new JLabel("Productos del almacen"));
        }
        else {
            this.orderProductsList = new JList<>();
            sp.setViewportView(this.orderProductsList);
            titlePanel.add(new JLabel("Lista del pedido"));
        }
        listPanel.add(sp);

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridy = 0;
        c2.weightx = 1.0;
        panel.add(titlePanel, c2);

        c2.fill = GridBagConstraints.BOTH;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 1.0;

        panel.add(listPanel, c2);

        return panel;
    }

    private void onWearhousesSelectionComboBox() {
        String wearhouseName = (String) this.wearhousesComboBox.getSelectedItem();
        this.wearhouseProductsListModel = new DefaultListModel<>();
        for (Wearhouse w : this.wearhouses)
            if (w.name.equals(wearhouseName)) {
                for (Product p : w.products)
                    this.wearhouseProductsListModel.addElement(String.format("%s(%d)", p.name, p.stock));
                break;
            }
        this.wearhouseProductsList.setModel(this.wearhouseProductsListModel);
    }

    private void onAddProductToOrderList() {
        int index = this.wearhouseProductsList.getSelectedIndex();
        String product = this.wearhouseProductsList.getSelectedValue();
        Matcher match = this.productListPattern.matcher(product);
        String productName = match.group(2);
        String productStock = match.group(2);
    }

    private void onFinishOrder() {
    }
}
