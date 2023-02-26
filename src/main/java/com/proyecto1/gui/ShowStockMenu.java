package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.proyecto1.containers.Graph;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Warehouse;

/**
 * @author sebas
 */
public class ShowStockMenu extends MenuComponent {
    /**
     * @param mainMenuPanel Panel principal
     */
    public ShowStockMenu(MainPanel mainMenuPanel) {
        super(mainMenuPanel, "Stock de los almacenes");
    }

    /**
     * Iniciar componentes
     */
    @Override
    protected void initMenuComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel wearhouseProductsPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createTable(true), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        wearhouseProductsPanel.add(this.createTable(false), c);

        this.add(wearhouseProductsPanel);
    }

    /**
     * @param type Si el BFS o BFS
     * @return componente JTable
     */
    private JComponent createTable(boolean type) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        // panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        GridBagConstraints c2 = new GridBagConstraints();

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel(type ? "BFS" : "DFS"));

        JPanel tablePanel = new JPanel(new GridLayout());
        DefaultTableModel model = new DefaultTableModel(); 
        model.addColumn("Almacen");
        model.addColumn("Producto");
        model.addColumn("Stock");
        if (type)
            this.bfs(model);
        else
            this.dfs(model);
        JTable table = new JTable(model);
        table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);
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

    /**
     * Búsqueda en anchura
     * @param model Modelo de JTable a modificar
     */
    private void bfs(DefaultTableModel model) {
        Vector<Warehouse> warehouses = Graph.getInstance().warehouses;
        Vector<Warehouse> queue = new Vector<>(warehouses.size());
        boolean[] visited = new boolean [warehouses.size()];
        Warehouse w; //vértice actual

        Arrays.fill(visited, false);

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]){
                queue.pushBack(warehouses.get(i));
                visited [i] = true;
                while (!queue.empty()) {
                    w = queue.popFront();

                    for (Product p : w.products) {
                        model.addRow(new String[] {w.name, p.name, Integer.toString(p.stock)});
                    }

                    for (int j = 0; j < visited.length; j++){
                        boolean a = false;
                        for (Edge e : w.edges)
                            a = e.nextWarehouse.name.equals(warehouses.get(j).name);
                        if (!(w.name.equals(warehouses.get(j).name)) && (a && (!visited[j]))) {
                            queue.pushBack(warehouses.get(j));
                            visited[j] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Búsqueda en profundidad
     * @param model Modelo de JTable a modificar
     */
    private void dfs(DefaultTableModel model) {
        Vector<Warehouse> warehouses = Graph.getInstance().warehouses;

        boolean[] visited = new boolean [warehouses.size()];
        Arrays.fill(visited, false);

        for (int i = 0; i < warehouses.size(); i++)
            if (!visited[i])
                recursiveDfs(i, warehouses, visited, model);
    }

    /**
     * Funcion auxiliar de la funcion dfs
     * @param w Indice del alamacen
     * @param warehouses Vector de alamacenes
     * @param visited Lista de almacenes visitados
     * @param model Modelo de JTable a modificar
     */
    private void recursiveDfs(int w, Vector<Warehouse> warehouses, boolean[] visited, DefaultTableModel model) {
        visited[w] = true;

        Warehouse warehouse = warehouses.get(w);
        for (Product p : warehouse.products) {
            model.addRow(new String[] {warehouse.name, p.name, Integer.toString(p.stock)});
        }

        for (int i = 0; i < warehouses.size(); i++) {
            boolean a = false;
            for (Edge e : warehouse.edges)
                a = e.nextWarehouse.name.equals(warehouses.get(i).name);
            if ((w != i) && (!visited[i]) && (a))
                recursiveDfs(i, warehouses, visited, model);
        }

    }
}