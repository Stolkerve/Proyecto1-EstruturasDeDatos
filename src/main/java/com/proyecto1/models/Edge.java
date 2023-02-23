package com.proyecto1.models;

/**
 * @author Andres
 */
public class Edge {
    public Warehouse nextWarehouse;
    public int distance;

    public Edge(Warehouse nextWarehouse, int distance) {
        this.nextWarehouse = nextWarehouse;
        this.distance = distance;
    }

}
