package com.proyecto1.models;

import com.proyecto1.containers.Vector;

/**
 * @author sebas
 */
public class Wearhouse {
    public String name;
    public int id;
    public Vector<Product> products;
    public Vector<Edge> edges;

    public Wearhouse(int id, String name) {
        this.id = id;
        this.name = name;
        this.products = new Vector<>();
        this.edges = new Vector<>();
    }
}
