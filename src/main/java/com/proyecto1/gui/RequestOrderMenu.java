package com.proyecto1.gui;

import java.awt.*;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.proyecto1.containers.Graph;
import com.proyecto1.containers.Pair;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Product;
import com.proyecto1.models.Warehouse;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

/**
 * @author sebas
 */
public class RequestOrderMenu extends MenuComponent {
    Vector<Warehouse> warehouses;
    JList<String> warehouseProductsList;
    DefaultListModel<String> warehouseProductsListModel;
    JList<String> orderProductsList;
    DefaultListModel<String> orderProductsListModel;
    JComboBox<String> warehousesComboBox;
    JButton addProductBtn;
    Pattern productListPattern;

    /**
     * @param mainPanel Menu principal
     */
    RequestOrderMenu(MainPanel mainPanel) {
        super(mainPanel);
        this.productListPattern = Pattern.compile("([a-zA-Z0-9]+)\\(([0-9]+)\\)"); 

        this.setBorder(BorderFactory.createEmptyBorder(-5,-5,-5,-5));

        this.orderProductsListModel = new DefaultListModel<>();
        this.warehouses = Graph.getInstance().warehouses;
        String[] warehousesNames = new String[this.warehouses.size()];
        for (int i = 0; i < this.warehouses.size(); i++)
            warehousesNames[i] = this.warehouses.get(i).name;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ImageAsset leftArrow = AssetsManager.getInstance().getImage("left-arrow");
        JButton backToMenuBtn = new JButton(leftArrow.image);
        backToMenuBtn.addActionListener(e -> this.backToMainMenu());
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(backToMenuBtn);
        topPanel.add(left);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.warehousesComboBox = new JComboBox<String>(warehousesNames);
        warehousesComboBox.setSelectedIndex(-1);
        warehousesComboBox.addActionListener(e -> {this.onWarehousesSelectionComboBox();});
        right.add(new JLabel("Almacenes"));
        right.add(warehousesComboBox);
        JButton finishOrderBtn = new JButton("Realizar pedido");
        finishOrderBtn.addActionListener(e -> this.onFinishOrder());
        right.add(finishOrderBtn);
        topPanel.add(right);

        this.add(topPanel);

        this.initMenuComponents();
    }

    /**
     * Iniciar componenetes
     */
    @Override
    protected void initMenuComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel warehouseProductsPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        warehouseProductsPanel.add(this.createList(true), c);

        c.gridx = 1;
        this.addProductBtn = new JButton(">>");
        this.addProductBtn.addActionListener(e -> this.onAddProductToOrderList());
        this.addProductBtn.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        this.addProductBtn.setEnabled(false);
        warehouseProductsPanel.add(addProductBtn);

        c.gridx = 2;
        c.fill = GridBagConstraints.BOTH;
        warehouseProductsPanel.add(this.createList(false), c);

        this.add(warehouseProductsPanel);
    }

    /**
     * @param left Si es la lista de productos del almacen
     * @return Componente JList
     */
    JComponent createList(boolean left) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel listPanel = new JPanel(new GridLayout());
        JScrollPane sp = new JScrollPane();
        if (left) {
            this.warehouseProductsList = new JList<>();
            sp.setViewportView(this.warehouseProductsList);
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

    /**
     * Reinicar la lista de productos de almacen
     */
    private void resetWarehouseProductsList() {
        this.warehouseProductsListModel = new DefaultListModel<>();
        String warehouseName = (String) this.warehousesComboBox.getSelectedItem();
        for (Warehouse w : this.warehouses)
            if (w.name.equals(warehouseName)) {
                for (Product p : w.products)
                    this.warehouseProductsListModel.addElement(String.format("%s(%d)", p.name, p.stock));
                break;
            }
        this.warehouseProductsList.setModel(this.warehouseProductsListModel);
    }

    /**
     * Seleccion del almacen
     */
    private void onWarehousesSelectionComboBox() {
        this.addProductBtn.setEnabled(true);
        this.resetWarehouseProductsList();

        this.orderProductsListModel = new DefaultListModel<>();
        this.orderProductsList.setModel(this.orderProductsListModel);
    }

    /**
     * Anadir productos de la lista de productos de almacen a la lista de pedidos
     */
    private void onAddProductToOrderList() {
        int index = this.warehouseProductsList.getSelectedIndex();
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

        String selectedProduct = this.warehouseProductsList.getSelectedValue();
        Matcher match = this.productListPattern.matcher(selectedProduct); match.matches();
        String selectedProductName = match.group(1);
        int selectedProductStock = Integer.parseInt(match.group(2));

        int diff = selectedProductStock - amount;
        if (diff > 0)
            this.warehouseProductsListModel.setElementAt(String.format("%s(%d)", selectedProductName, diff), index);
        else
            this.warehouseProductsListModel.removeElementAt(index);
        
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

    /**
     * Finalizar la orden
     */
    private void onFinishOrder() {
        if (this.orderProductsListModel.size() == 0) return;

        int warehouseIndex = (int) this.warehousesComboBox.getSelectedIndex();
        Warehouse warehouse = this.warehouses.get(warehouseIndex);
        Vector<String> pendingOrderProductsToDelete = new Vector<>();
        Vector<Integer> pendingWarehouseProductsToDelete = new Vector<>();

        for (int i = 0; i < this.orderProductsListModel.size(); i++) {
            String listElement = this.orderProductsListModel.get(i);
            Matcher m = this.productListPattern.matcher(listElement); m.matches();
            String orderProductName = m.group(1);
            int orderProductStock = Integer.parseInt(m.group(2));
            int pIndex = 0;
            for (Product p : warehouse.products) {
                if (orderProductName.equals(p.name)) {
                    int diff = p.stock - orderProductStock;
                    if (diff < 0) { // Se necesita el stock de otros almacenes
                        // Notificar
                        int res = JOptionPane.showConfirmDialog(this,
                            String.format("Se esta solicitando mas stock que el disponible del producto %s en el almacen %s. Deseas buscar en otro almacen?", orderProductName, warehouse.name),
                            "Error", JOptionPane.OK_CANCEL_OPTION
                        );
                        if (res != JOptionPane.OK_OPTION) {
                            pendingOrderProductsToDelete.pushBack(listElement);
                            continue;
                        }
                        Vector<Pair<Warehouse, Integer>> warehousesPath = Graph.getInstance().dijkstra(warehouse);
                        boolean found = false;
                        search: {
                            for (Pair<Warehouse, Integer> pair : warehousesPath) {
                                Warehouse wClosed = pair.primary;
                                int pIndex2 = 0;
                                for (Product pClosed : wClosed.products) {
                                    if (pClosed.name.equals(orderProductName)) {
                                        int diff2 =  Math.abs(diff) - pClosed.stock;
                                        if (diff2 <= 0) { // El primer almacen suple la cantidad necesaria, terminar
                                            JDialog dialog = new JDialog(this.mainMenuPanel.mainFrame, "Resultado");
                                            dialog.setLayout(new BorderLayout());
                                            dialog.setSize(320 * 2, 180 * 2);
                                            dialog.setModal(true);
                                            dialog.setLocationRelativeTo(null);
                                            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                                            dialog.setUndecorated(true);
                                            dialog.add(new JLabel("Las rutas mas cortas a almacenes relativamente al almacen " + warehouse.name));

                                            ListenableGraph<String, MyWeightedEdge> g = new DefaultListenableGraph<>(
                                                    new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class));
                                            JGraphXAdapter<String, MyWeightedEdge> jgxAdapter = new JGraphXAdapter<>(g);

                                            g.addVertex(warehouse.name);
                                            for(Pair<Warehouse, Integer> pair2 : warehousesPath) {
                                                g.addVertex(pair2.primary.name);
                                                if (pair2.primary.name.equals(wClosed.name)) break;
                                            }

                                            for (int j = 0; j < warehousesPath.size(); j++) {
                                                if (j == 0) {
                                                    MyWeightedEdge gEdge = g.addEdge(warehouse.name, warehousesPath.get(j).primary.name);
                                                    g.setEdgeWeight(gEdge, warehousesPath.get(j).second);
                                                    if (warehousesPath.get(j).primary.name.equals(wClosed.name)) break;
                                                    continue;
                                                }
                                                if ((j + 1) < warehousesPath.size()) {

                                                    if (warehousesPath.get(j).primary.name.equals(wClosed.name)) break;
                                                    MyWeightedEdge gEdge = g.addEdge(warehousesPath.get(j).primary.name, warehousesPath.get(j + 1).primary.name);
                                                    g.setEdgeWeight(gEdge, warehousesPath.get(j).second);
                                                }
                                            }


                                            mxGraphComponent component = new mxGraphComponent(jgxAdapter);
                                            component.setConnectable(false);
                                            component.setEnabled(false);
                                            component.getGraph().setAllowDanglingEdges(false);
                                            mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

                                            mxGraphModel graphModel = (mxGraphModel) component.getGraph().getModel();
                                            Collection<Object> cells = graphModel.getCells().values();
                                            
                                            for (Object c : cells) {
                                                mxCell cell = (mxCell) c;
                                                graphModel.setStyle(cell, "rounded=1;");
                                                mxGeometry geometry = cell.getGeometry();

                                                if (cell.isVertex()) {
                                                    geometry.setWidth(30);
                                                    geometry.setHeight(30);
                                                }
                                            }


                                            // center the circle
                                            int radius = 40;

                                            layout.setX0((320 * 2 / 2.0) - radius);
                                            layout.setY0(((180 * 2 - 100) / 2.0) - radius);
                                            layout.setRadius(radius);
                                            layout.setMoveCircle(true);

                                            layout.execute(jgxAdapter.getDefaultParent());

                                            JButton continueBtn = new JButton("Continuar");
                                            continueBtn.addActionListener(e -> {
                                                dialog.dispose();
                                            });

                                            JPanel panel = new JPanel(new BorderLayout());
                                            panel.add(new JLabel("Mapa de los almacenes recorridos"), BorderLayout.NORTH);
                                            panel.add(component, BorderLayout.CENTER);
                                            panel.add(continueBtn, BorderLayout.SOUTH);
                                            dialog.add(panel);

                                            dialog.setVisible(true);

                                            pendingOrderProductsToDelete.pushBack(listElement);
                                            pendingWarehouseProductsToDelete.pushBack(pIndex);

                                            if (diff2 == 0) {
                                                wClosed.products.remove(pIndex2);
                                            } else {
                                                pClosed.stock = Math.abs(diff2);
                                            }

                                            found = true;
                                            Graph.getInstance().needsSave = true;
                                            break search;
                                        }
                                    }
                                    pIndex2++;
                                }
                            }
                        }
                        if (!found) {
                            JOptionPane.showMessageDialog(this, "No se encontro ningun almacen que supla la cantidad pedida del producto", "Error", JOptionPane.ERROR_MESSAGE);
                            pendingOrderProductsToDelete.pushBack(listElement);
                        }
                    } else {
                        pendingOrderProductsToDelete.pushBack(listElement);
                        if (diff == 0) {
                            pendingWarehouseProductsToDelete.pushBack(pIndex);
                            continue;
                        }
                        p.stock = diff;

                        Graph.getInstance().needsSave = true;
                    }
                }
                pIndex++;
            }
        }

        for (String i : pendingOrderProductsToDelete)
            this.orderProductsListModel.removeElement(i);

        for (int i = 0; i < pendingWarehouseProductsToDelete.size(); i++)
            warehouse.products.remove(pendingWarehouseProductsToDelete.get(i) - i);
        
        this.resetWarehouseProductsList();
    }
}
