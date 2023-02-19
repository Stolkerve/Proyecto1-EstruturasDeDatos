package com.proyecto1.models;

import com.proyecto1.containers.Vector;

/**
 * @author sebas
 */
public class Wearhouse {
    public String name;
    public Vector<Product> products;
    public Vector<Edge> edges;
    // public int minDist = 2147483647; // Para la busqueda de grafos

    public Wearhouse(String name) {
        this.name = name;
        this.products = new Vector<>();
        this.edges = new Vector<>();
    }
}
