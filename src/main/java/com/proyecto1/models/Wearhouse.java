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
    public int distanciaMin=Integer.MAX_VALUE;
    

    Wearhouse(int id, Vector<Product> products) {
        this.id = id;
        this.products = products;
    }
}
