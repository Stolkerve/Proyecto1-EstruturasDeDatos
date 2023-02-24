package com.proyecto1.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.proyecto1.containers.Graph;
import com.proyecto1.containers.Vector;
import com.proyecto1.models.Edge;
import com.proyecto1.models.Warehouse;

class MyWeightedEdge extends DefaultWeightedEdge {
  @Override
  public String toString() {
    return Double.toString(getWeight());
  }
}

public class GraphTheGraphMenu extends MenuComponent {

    protected GraphTheGraphMenu(MainPanel mainMenuPanel) {
        super(mainMenuPanel, "Mapa de los almacenes");
    }

    @Override
    protected void initMenuComponents() {
        ListenableGraph<String, MyWeightedEdge> g = new DefaultListenableGraph<>(
                new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class));
        JGraphXAdapter<String, MyWeightedEdge> jgxAdapter = new JGraphXAdapter<>(g);

        Vector<Warehouse> warehouses = Graph.getInstance().warehouses;
        
        for(Warehouse w : warehouses) {
            g.addVertex(w.name);
        }

        for(Warehouse w : warehouses) {
            for(Edge e : w.edges) {
                MyWeightedEdge gEdge = g.addEdge(w.name, e.nextWarehouse.name);
                g.setEdgeWeight(gEdge, e.distance);
            }
        }

        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.setEnabled(false);
        component.getGraph().setAllowDanglingEdges(false);
        mxGraphModel graphModel = (mxGraphModel) component.getGraph().getModel();
        Collection<Object> cells = graphModel.getCells().values();
        for (Object c : cells) {
            mxCell cell = (mxCell) c;
            cell.setAttribute(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
            mxGeometry geometry = cell.getGeometry();

            graphModel.setStyle(cell, "rounded=1;endArrow=none");
            if (cell.isVertex()) {
                geometry.setWidth(40);
                geometry.setHeight(40);
            }
        }

        JPanel panel = new JPanel(new GridLayout());
        JScrollPane sp = new JScrollPane(component);
        sp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.add(sp);
        this.add(panel);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 200;

        Dimension d = this.mainMenuPanel.mainFrame.getSize();
        layout.setX0((d.width / 2.0) - radius);
        layout.setY0(((d.height - 100) / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}