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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.swing.mxGraphComponent;
import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

public class RequestOrder extends CustomComponent {
    Vector<Wearhouse> wearhouses; 
    JList<String> wearhouseProductsList;
    DefaultListModel<String> wearhouseProductsListModel;
    JList<String> orderProductsList;
    DefaultListModel<String> orderProductsListModel;
    JComboBox<String> wearhousesComboBox;
    JButton addProductBtn;
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
        this.addProductBtn = new JButton("->");
        this.addProductBtn.addActionListener(e -> this.onAddProductToOrderList());
        this.addProductBtn.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        this.addProductBtn.setEnabled(false);
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

    private void resetWearhouseProductsList() {
        this.wearhouseProductsListModel = new DefaultListModel<>();
        String wearhouseName = (String) this.wearhousesComboBox.getSelectedItem();
        for (Wearhouse w : this.wearhouses)
            if (w.name.equals(wearhouseName)) {
                for (Product p : w.products)
                    this.wearhouseProductsListModel.addElement(String.format("%s(%d)", p.name, p.stock));
                break;
            }
        this.wearhouseProductsList.setModel(this.wearhouseProductsListModel);
    }

    private void onWearhousesSelectionComboBox() {
        this.addProductBtn.setEnabled(true);
        this.resetWearhouseProductsList();

        this.orderProductsListModel = new DefaultListModel<>();
        this.orderProductsList.setModel(this.orderProductsListModel);
    }

    private void onAddProductToOrderList() {
        int index = this.wearhouseProductsList.getSelectedIndex();
        if (index < 0) return;

        int amount = 0;
        String amountString = JOptionPane.showInputDialog(this, "Cantidad");
        if (amountString == null) return;
        try {
            amount = Integer.parseInt(amountString);
        } catch (Exception e) {}
        while (amount <= 0) {
            amountString = JOptionPane.showInputDialog(this, "Ingrese una cantidad valida");
            if (amountString == null) return;
            try {
                amount = Integer.parseInt(amountString);
            } catch (Exception e) {}
        }

        String selectedProduct = this.wearhouseProductsList.getSelectedValue();
        Matcher match = this.productListPattern.matcher(selectedProduct); match.matches();
        String selectedProductName = match.group(1);
        int selectedProductStock = Integer.parseInt(match.group(2));

        int diff = selectedProductStock - amount;
        if (diff > 0)
            this.wearhouseProductsListModel.setElementAt(String.format("%s(%d)", selectedProductName, diff), index);
        else
            this.wearhouseProductsListModel.removeElementAt(index);
        
        for (int i = 0; i < this.orderProductsListModel.size(); i++) {
            String listElement = this.orderProductsListModel.get(i);
            Matcher m = this.productListPattern.matcher(listElement); m.matches();
            String name = m.group(1);
            int stock = Integer.parseInt(m.group(2));
            if (name.equals(selectedProductName)) {
                this.orderProductsListModel.setElementAt(String.format("%s(%d)", selectedProductName, amount + stock), i);
                return;
            }
        }

        this.orderProductsListModel.addElement(String.format("%s(%d)", selectedProductName, amount));
    }

    private void onFinishOrder() {
        if (this.orderProductsListModel.size() == 0) return;

        int wearhouseIndex = (int) this.wearhousesComboBox.getSelectedIndex();
        Wearhouse wearhouse = this.wearhouses.get(wearhouseIndex);
        Vector<String> pendingOrderProductsToDelete = new Vector<>();
        Vector<Integer> pendingWearhouseProductsToDelete = new Vector<>();

        for (int i = 0; i < this.orderProductsListModel.size(); i++) {
            String listElement = this.orderProductsListModel.get(i);
            Matcher m = this.productListPattern.matcher(listElement); m.matches();
            String orderProductname = m.group(1);
            int orderProductstock = Integer.parseInt(m.group(2));
            int pIndex = 0;
            for (Product p : wearhouse.products) {
                if (orderProductname.equals(p.name)) {
                    int diff = p.stock - orderProductstock;
                    if (diff < 0) { // Se necesita el stock de otros almacenes
                        // Notificar
                        int res = JOptionPane.showConfirmDialog(this,
                            String.format("Se esta solicitando mas stock que el disponible del producto %s en el almacen %s. Deseas buscar en otro almacen?", orderProductname, wearhouse.name),
                            "Error", JOptionPane.OK_CANCEL_OPTION
                        );
                        if (res != JOptionPane.OK_OPTION) {
                            pendingOrderProductsToDelete.pushBack(listElement);
                            continue;
                        }
                        Vector<Wearhouse> wearhousesPath = Grafo.getInstance().dijkstra(wearhouse);
                        search: {
                            for (Wearhouse wClosed : wearhousesPath) {
                                for (Product pClosed : wClosed.products) {
                                    if (pClosed.name.equals(orderProductname)) {
                                        diff = pClosed.stock - Math.abs(diff);
                                        if (diff >= 0) {
                                            pendingOrderProductsToDelete.pushBack(listElement);
                                            JDialog dialog = new JDialog(this.mainMenuPanel.mainFrame, "Resultado");
                                            dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
                                            dialog.add(new JLabel("Las rutas mas cortas a almacenes relativamente al almacen " + wearhouse.name));

                                            ListenableGraph<String, MyWeightedEdge> g = new DefaultListenableGraph<>(
                                                    new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class));
                                            JGraphXAdapter<String, MyWeightedEdge> jgxAdapter = new JGraphXAdapter<>(g);

                                            for(Wearhouse graph : wearhousesPath) {
                                                g.addVertex(graph.name);
                                            }
                                            // for (int j = 0; j < wearhousesPath.size(); j++) {
                                            //     if ((j + 1) < wearhousesPath.size()) {
                                            //         MyWeightedEdge gEdge = g.addEdge(wearhousesPath.get(j).name, wearhousesPath.get(j + 1).name);
                                            //         // g.setEdgeWeight(gEdge, e.distancia);
                                            //     }
                                            // }


                                            mxGraphComponent component = new mxGraphComponent(jgxAdapter);
                                            component.setConnectable(false);
                                            component.setEnabled(false);
                                            component.getGraph().setAllowDanglingEdges(false);
                                            dialog.add(component);

                                            JButton continueBtn = new JButton();
                                            continueBtn.addActionListener(e -> {
                                                dialog.dispose();
                                            });
                                            dialog.add(continueBtn);

                                            dialog.pack();
                                            dialog.setVisible(true);
                                            break search;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        pendingOrderProductsToDelete.pushBack(listElement);
                        if (diff == 0) {
                            pendingWearhouseProductsToDelete.pushBack(pIndex);
                            continue;
                        }
                        p.stock = diff;

                        Grafo.getInstance().needsSave = true;
                    }
                }
                pIndex++;
            }
        }

        for (String i : pendingOrderProductsToDelete)
            this.orderProductsListModel.removeElement(i);

        for (int i = 0; i < pendingWearhouseProductsToDelete.size(); i++)
            wearhouse.products.remove(pendingWearhouseProductsToDelete.get(i) - i);

        this.resetWearhouseProductsList();
    }
}
