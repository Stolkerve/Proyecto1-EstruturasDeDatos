package com.proyecto1.models;

import com.proyecto1.containers.Vector;

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
