package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.proyecto1.containers.Grafo;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;

public class ShowStock extends CustomComponent {
    public ShowStock(MainPanel mainMenuPanel) {
        super(mainMenuPanel, "Stock de los almacenes");
    }

    @Override
    protected void initComponent() {
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

    private JComponent createTable(boolean type) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
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

    private void bfs(DefaultTableModel model) {
        Vector<Wearhouse> wearhouses = Grafo.getInstance().almacenes;
        Vector<Wearhouse> queue = new Vector<>();
        boolean[] visited = new boolean [wearhouses.size()];
        Wearhouse w; //vértice actual
        for (boolean v : visited)
            v = false;

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]){
                queue.pushBack(wearhouses.get(i));
                visited [i] = true;
                while (!queue.empty()) {
                    w = queue.popFront();

                    for (Product p : w.products) {
                        model.addRow(new String[] {w.name, p.name, Integer.toString(p.stock)});
                    }

                    for (int j = 0; j < visited.length; j++){
                        boolean a = false;
                        for (Edge e : w.edges)
                            a = e.almacenVecino.id == wearhouses.get(j).id;
                        if ((w.id != wearhouses.get(j).id) && (a && (!visited[j]))) {
                            queue.pushBack(wearhouses.get(j));
                            visited[j] = true;
                        }
                    }
                }
            }
        }
    }

    private void dfs(DefaultTableModel model) {
        Vector<Wearhouse> wearhouses = Grafo.getInstance().almacenes;
        boolean[] visited = new boolean [wearhouses.size()];
        for (boolean v : visited)
            v = false;
        for (int i = 0; i < wearhouses.size(); i++)
            if (!visited[i])
                recursiveDfs(i, wearhouses, visited, model);
    }

    private void recursiveDfs(int w, Vector<Wearhouse> wearhouses, boolean[] visited, DefaultTableModel model) {
        visited[w] = true;

        Wearhouse wearhouse = wearhouses.get(w);
        for (Product p : wearhouse.products) {
            model.addRow(new String[] {wearhouse.name, p.name, Integer.toString(p.stock)});
        }

        for (int i = 0; i < wearhouses.size(); i++) {
            boolean a = false;
            for (Edge e : wearhouse.edges)
                a = e.almacenVecino.id == wearhouses.get(i).id;
            if ((w != i) && (!visited[i]) && (a))
                recursiveDfs(i, wearhouses, visited, model);
        }

    }
}