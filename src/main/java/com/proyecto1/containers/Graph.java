package com.proyecto1.containers;

import com.proyecto1.models.Edge;
import com.proyecto1.models.Warehouse;

/**
 * Singleton para contener el grafo
 */
public class Graph {
    public boolean init = false;
    public boolean needsSave = false;
    public Vector<Warehouse> warehouses;
    private static Graph instance;

    public Graph() {
        this.warehouses = new Vector<>();
    }

    public static Graph getInstance() {
        if (instance == null) {
            instance = new Graph();
        }
        return instance;
    }

    /**
     * @param warehouseOrigen Almacen origen donde comenzar la busqueda
     * @return Vector de los alamacenes recorridos junto la distancia
     */
    public Vector<Pair<Warehouse, Integer>> dijkstra(Warehouse warehouseOrigen) {
        class Vertex {
            int index; int distance;
            public Vertex(int index, int distance) {
                this.index = index;
                this.distance = distance;
            }
        }

        Vector<Pair<Warehouse, Integer>> warehousePath = new Vector<>(this.warehouses.size());
        Vector<Vertex> queue = new Vector<>(this.warehouses.size());
        Vector<Integer> dist = new Vector<>(this.warehouses.size());
        Vector<Boolean> done = new Vector<>(this.warehouses.size());

        int indexOrigin = 0;
        for (int i = 0; i < this.warehouses.size(); i++) {
            if (warehouseOrigen.name.equals(this.warehouses.get(i).name))
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
            for (Edge e : this.warehouses.get(u).edges) {
                int vIndex = 0;
                Warehouse v = e.nextWarehouse;
                int weight = e.distance;

                for (int i = 0; i < this.warehouses.size(); i++)
                    if (v.name.equals(this.warehouses.get(i).name))
                        vIndex = i;

                if (!done.get(vIndex) && (dist.get(u) + weight) < dist.get(vIndex)) {
                    dist.set(vIndex, dist.get(u) + weight);
                    queue.pushBack(new Vertex(vIndex, dist.get(vIndex)));
                }
            }

            done.set(u, true);
        }

        for (int i = 0; i < this.warehouses.size(); i++)
        {
            if (!(warehouseOrigen.name.equals(this.warehouses.get(i).name)) && dist.get(i) != Integer.MAX_VALUE) {
                System.out.printf("Path (%s —> %s): Minimum cost = %d\n",
                                warehouseOrigen.name, this.warehouses.get(i).name, dist.get(i));
                warehousePath.pushBack(new Pair<Warehouse, Integer>(this.warehouses.get(i), dist.get(i)));
            }
        }
        return warehousePath;
    }
}
