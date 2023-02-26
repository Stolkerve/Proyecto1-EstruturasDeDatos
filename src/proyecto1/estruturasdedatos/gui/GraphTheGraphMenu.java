package proyecto1.estruturasdedatos.gui;

import java.awt.*;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import proyecto1.estruturasdedatos.containers.Graph;
import proyecto1.estruturasdedatos.containers.Vector;
import proyecto1.estruturasdedatos.models.Edge;
import proyecto1.estruturasdedatos.models.Warehouse;

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
        Dimension d = this.mainMenuPanel.mainFrame.getSize();
        for (Object c : cells) {
            mxCell cell = (mxCell) c;
            cell.setAttribute(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
            mxGeometry geometry = cell.getGeometry();

            graphModel.setStyle(cell, "rounded=1;spacing=20");
            if (cell.isVertex()) {
                geometry.setWidth(20);
                geometry.setHeight(20);
                geometry.setX((d.width - 300) / 2.0);
                geometry.setY(100);
                geometry.setOffset(new mxPoint(00, 00));
                geometry.grow(10);
            }
        }

        JPanel panel = new JPanel(new GridLayout());
        JScrollPane sp = new JScrollPane(component);
        sp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.add(sp);
        this.add(panel);

        // positioning via jgraphx layouts
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.setUseBoundingBox(true);
        layout.setForceConstant(200);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}