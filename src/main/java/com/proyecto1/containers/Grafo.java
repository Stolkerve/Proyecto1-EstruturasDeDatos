package com.proyecto1.containers;

import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;

public class Grafo {
    public boolean iniciado = false;
    public boolean necesitaGuardar = false;
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

    public void agregarAlmacen(Wearhouse nuevo){
        
        almacenes.pushBack(nuevo);
    }

    public DistanciaDijsktra dijkstra(int almacenOrigen, Wearhouse almacenDestino) {
        // almacenOrigen.distanciaMin = 0;

        Vector<Wearhouse> cola = new Vector<>();
        cola.pushBack(almacenes.get(almacenOrigen));
        Vector<Integer> dist=new  Vector<>();
        Vector<Wearhouse> prev=new Vector<>();

        for (int i = 0; i < this.almacenes.size(); i++) {
            dist.pushBack(Integer.MAX_VALUE); 
        }
        Vector<Boolean> done=new  Vector<>();
        
        for (int i = 0; i < this.almacenes.size(); i++) {
            done.pushBack(false);
        }


        while (!cola.empty()) {
            Wearhouse node = cola.popFront();
            for (Edge edge : node.edges) {
                Wearhouse n = edge.almacenVecino;
                int weight = edge.distancia;
                // int minDistance = node.distanciaMin + weight;

                // if (minDistance < n.distanciaMin) {
                //     for (int i = 0; i < cola.size(); i++) {
                //         if (cola.Get(i).id == node.id) {
                //             cola.remove(i);
                //         }
                //     }
                //     almacenesRecorridos.pushBack(node); // !! Nodos recorridos
                //     n.distanciaMin = minDistance; // !!!
                //     cola.pushBack(n);
                // }
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
