package com.proyecto1.models;

public class Wearhouse {
    public int id;
    public Product[] products;

    Wearhouse(int id, Product[] products) {
        this.id = id;
        this.products = products;
    }
}
