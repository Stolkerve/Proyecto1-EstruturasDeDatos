package proyecto1.estruturasdedatos.models;

import proyecto1.estruturasdedatos.containers.Vector;

/**
 * @author sebas
 */
public class Warehouse {
    public String name;
    public Vector<Product> products;
    public Vector<Edge> edges;

    public Warehouse(String name) {
        this.name = name;
        this.products = new Vector<>();
        this.edges = new Vector<>();
    }
}
