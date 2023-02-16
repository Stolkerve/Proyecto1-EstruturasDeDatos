package com.proyecto1.models;

public class Edge {

    public Wearhouse almacen;
    public Wearhouse almacenVecino;
    public int distancia;

    public Edge(Wearhouse almacen, Wearhouse almacenVecino, int distancia) {
        this.almacen = almacen;
        this.almacenVecino = almacenVecino;
        this.distancia = distancia;
    }

}
