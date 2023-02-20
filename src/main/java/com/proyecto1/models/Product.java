package com.proyecto1.models;

/**
 * @author sebas
 */
public class Product {
    public String name;
    public int stock;
    public int id;

    public Product(String name, int id, int stock) {
        this.name = name;
        this.stock = stock;
        this.id = id;
    }
}
