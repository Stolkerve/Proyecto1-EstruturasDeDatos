package com.proyecto1.containers;

import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;

public class Grafo {
    public boolean iniciado = false;
    public Vector<Wearhouse> almacenes;
    private static Grafo instancia;

    private class DistanciaDijsktra {
        Vector<Wearhouse> almacenes;
        int distanciaTotal;
    }

    public Grafo() {
        this.almacenes = new Vector<>();
    }

    public static Grafo getInstance() {
        if (instancia == null) {
            instancia = new Grafo();
        }
        return instancia;
    }

    public Product buscarProducto(int productoId, int almacenId) {
        for (Wearhouse almacen : this.almacenes) {
            if (almacen.id == almacenId) {
                for (Product producto : almacen.products) {
                    if (producto.id == productoId) {
                        return producto;
                    }
                }
            }
        }
        return null;
    }

    public DistanciaDijsktra dijkstra(Wearhouse almacenOrigen, Wearhouse almacenDestino) {
        almacenOrigen.distanciaMin = 0;
        Vector<Wearhouse> almacenesRecorridos = new Vector<>();
        Vector<Wearhouse> priorityQueue = new Vector<>();
        priorityQueue.pushBack(almacenOrigen);

        while (!priorityQueue.empty()) {
            Wearhouse node = priorityQueue.popFront();
            for (Edge edge : node.edges) {
                Wearhouse n = edge.almacenVecino;
                int weight = edge.distancia;
                int minDistance = node.distanciaMin + weight;

                if (minDistance < n.distanciaMin) {
                    for (int i = 0; i < priorityQueue.size(); i++) {
                        if (priorityQueue.Get(i).id == node.id) {
                            priorityQueue.remove(i);
                        }
                    }
                    almacenesRecorridos.pushBack(node); // !! Nodos recorridos
                    n.distanciaMin = minDistance; // !!!
                    priorityQueue.pushBack(n);
                }
            }
        }

        DistanciaDijsktra trayectoria = new DistanciaDijsktra();
        Wearhouse almacen = almacenDestino;
        while (almacen != null) {
            // trayectoria.add(almacen);
            // almacen = almacen.getNodoAnterior();
        }

        return null;
    }

    public Product buscarEnOtroAlmacen(String productoNombre, Wearhouse almacenOrigen) {
        Vector<Wearhouse> almacenesEncontrados = new Vector<>();
        for (Wearhouse almacen : this.almacenes) {
            if (almacen.id != almacenOrigen.id) {
                for (Product producto : almacen.products) {
                    if (producto.name.equalsIgnoreCase(productoNombre)) {
                        if (producto.stock > 0) {
                            // almacenesEncontrados.pushBack(dijkstra(almacen, almacenOrigen));
                        }
                    }
                }
            }
        }
        return null;
    }

}
