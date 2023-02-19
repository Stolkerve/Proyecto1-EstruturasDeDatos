package com.proyecto1.containers;

import com.proyecto1.models.Edge;
import com.proyecto1.models.Product;
import com.proyecto1.models.Wearhouse;

public class Grafo {
    public boolean iniciado = false;
    public boolean necesitaGuardar = false;
    public Vector<Wearhouse> almacenes;
    private static Grafo instancia;

    public Grafo() {
        this.almacenes = new Vector<>();
    }

    public static Grafo getInstance() {
        if (instancia == null) {
            instancia = new Grafo();
        }
        return instancia;
    }

    public Product buscarProducto(int productoId, String almacenName) {
        for (Wearhouse almacen : this.almacenes) {
            if (almacen.name.equals(almacenName)) {
                for (Product producto : almacen.products) {
                    if (producto.id == productoId) {
                        return producto;
                    }
                }
            }
        }
        return null;
    }

    public Vector<Wearhouse> dijkstra(Wearhouse almacenOrigen) {
        // almacenOrigen.distanciaMin = 0;

        class Vertex {
            int index; int distance;
            public Vertex(int index, int distance) {
                this.index = index;
                this.distance = distance;
            }
        }

        Vector<Wearhouse> almacenesRecorridos = new Vector<>();
        Vector<Vertex> queue = new Vector<>();
        Vector<Integer> dist = new Vector<>();
        Vector<Boolean> done = new Vector<>();

        int indexOrigin = 0;
        for (int i = 0; i < this.almacenes.size(); i++) {
            if (almacenOrigen.name.equals(this.almacenes.get(i).name))
                indexOrigin = i;
            dist.pushBack(Integer.MAX_VALUE);
            done.pushBack(false);
        }
        dist.set(indexOrigin, 0);
        done.set(indexOrigin, true);

        queue.pushBack(new Vertex(indexOrigin, 0));
        
        while (!queue.empty()) {
            // sort
            for (int i = 0; i < queue.size(); i++) {
                for (int e = 0; e < queue.size(); e++) {
                    if (queue.get(i).distance < queue.get(e).distance) {
                        Vertex a = new Vertex(queue.get(i).index, queue.get(i).distance);
                        queue.set(i, queue.get(e));
                        queue.set(e, a);
                    }
                }
            }
            int u = queue.popFront().index;
            for (Edge e : this.almacenes.get(u).edges) {
                int vIndex = 0;
                Wearhouse v = e.almacenVecino;
                int weight = e.distancia;

                for (int i = 0; i < this.almacenes.size(); i++)
                    if (v.name.equals(this.almacenes.get(i).name))
                        vIndex = i;

                if (!done.get(vIndex) && (dist.get(u) + weight) < dist.get(vIndex)) {
                    dist.set(vIndex, dist.get(u) + weight);
                    queue.pushBack(new Vertex(vIndex, dist.get(vIndex)));
                }
            }

            done.set(u, true);
        }

        for (int i = 0; i < this.almacenes.size(); i++)
        {
            if (!(almacenOrigen.name.equals(this.almacenes.get(i).name)) && dist.get(i) != Integer.MAX_VALUE) {
                System.out.printf("Path (%s —> %s): Minimum cost = %d\n",
                                almacenOrigen.name, this.almacenes.get(i).name, dist.get(i));
            }
        }
        return almacenesRecorridos;
    }

    public Product buscarEnOtroAlmacen(String productoNombre, int amount, Wearhouse almacenOrigen) {
        Vector<Wearhouse> almacenesEncontrados = new Vector<>();
        // for (Wearhouse almacen : this.almacenes) {
        //     if (almacen.id != almacenOrigen.id) {
        //         for (Product producto : almacen.products) {
        //             if (producto.name.equalsIgnoreCase(productoNombre)) {
        //                 if (producto.stock > 0) {
        //                     // almacenesEncontrados.pushBack(dijkstra(almacen, almacenOrigen));
        //                 }
        //             }
        //         }
        //     }
        // }
        return null;
    }

}
