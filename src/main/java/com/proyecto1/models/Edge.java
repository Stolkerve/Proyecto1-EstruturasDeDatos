package com.proyecto1.models;

public class Edge {

    public Wearhouse almacenVecino;
    public int distancia;

    public Edge(Wearhouse almacenVecino, int distancia) {
        this.almacenVecino = almacenVecino;
        this.distancia = distancia;
    }

}
