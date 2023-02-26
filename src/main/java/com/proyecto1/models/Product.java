package com.proyecto1.models;

/**
 * Productos de un almacen
 * @author sebas
 */
public class Product {
    public String name;
    public int stock;

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }
}
