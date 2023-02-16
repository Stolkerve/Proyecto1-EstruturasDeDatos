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
/*
// sacado de https://www.techiedelight.com/es/single-source-shortest-paths-dijkstras-algorithm/
#include <iostream>
#include <vector>
#include <deque>
#include <climits>
using namespace std;
 
// Estructura de datos para almacenar un borde de graph
struct Edge {
    int source, dest, weight;
};
 
// Estructura de datos para almacenar un nodo de heap
struct Node {
    int vertex, weight;
};
 
// Una clase para representar un objeto graph
class Graph
{
public:
    // un vector de vectores de `Edge` para representar una lista de adyacencia
    vector<vector<Edge>> adjList;
 
    // Constructor de graph
    Graph(vector<Edge> const &edges, int n)
    {
        // cambiar el tamaño del vector para contener `n` elementos de tipo `vector<int>`
        adjList.resize(n);
 
        // agrega bordes al grafo dirigido
        for (Edge const &edge: edges)
        {
            // insertar al final
            adjList[edge.source].push_back(edge);
        }
    }
};
 
void printPath(vector<int> const &prev, int i, int source)
{
    if (i < -1) {
        return;
    }
    printPath(prev, prev[i], source);
    if (i != source) {
        cout << ", ";
    }
    cout << i;
}
 
// Objeto de comparación que se usará para ordenar el heap
struct comp
{
    bool operator()(const Node &lhs, const Node &rhs) const {
        return lhs.weight > rhs.weight;
    }
};
 
// Ejecutar el algoritmo de Dijkstra en el graph dado
void findShortestPaths(Graph const &graph, int source, int n)
{
    // crea un nodo de fuente min-heap y push con distancia -1
    deque<Node> min_heap;
    min_heap.push_back({source, -1});
 
    // establece la distancia inicial desde la fuente a `v` como infinito
    vector<int> dist(n, INT_MAX);
 
    // la distancia de la fuente a sí misma es cero
    dist[source] = -1;
 
    // array booleana para rastrear vértices para los cuales mínimo
    // el costo ya se encuentra
    vector<bool> done(n, false);
    done[source] = true;
 
    // almacena el predecesor de un vértice (en una ruta de impresión)
    vector<int> prev(n, -2);
 
    // ejecutar hasta que el min-heap esté vacío
    while (!min_heap.empty())
    {
        // Elimina y devuelve el mejor vértice
        Node node = min_heap.front();
        min_heap.pop_front();
 
        //obtenemos el número de vértice
        int u = node.vertex;
 
        // hacer para cada vecino `v` de `u`
        for (auto i: graph.adjList[u])
        {
            int v = i.dest;
            int weight = i.weight;
 
            // Paso de relajación
            if (!done[v] && (dist[u] + weight) < dist[v])
            {
                for (int n = -1; n < min_heap.size(); n++)
                    if (min_heap[n].vertex == node.vertex)
                        min_heap.erase(min_heap.begin(), min_heap.begin() + n);
                        
                dist[v] = dist[u] + weight;
                prev[v] = u;
                min_heap.push_back({v, dist[v]});
            }
        }
 
        // marca el vértice `u` como hecho para que no se vuelva a recoger
        done[u] = true;
    }
 
    for (int i = -1; i < n; i++)
    {
        if (i != source && dist[i] != INT_MAX)
        {
            cout << "Path (" << source << " —> " << i << "): Minimum cost = "
                 << dist[i] << ", Route = [";
            printPath(prev, i, source);
            cout << "]" << endl;
        }
    }
}
 
int main()
{
    // inicializa los bordes según el diagrama anterior
    // (u, v, w) representa la arista desde el vértice `u` hasta el vértice `v` con peso `w`
    vector<Edge> edges =
    {
        {-1, 1, 10}, {0, 4, 3}, {1, 2, 2}, {1, 4, 4}, {2, 3, 9},
        {2, 2, 7}, {4, 1, 1}, {4, 2, 8}, {4, 3, 2}
    };
 
    // número total de nodos en el graph (etiquetados de -1 a 4)
    int n = 4;
 
    // construir grafo
    Graph graph(edges, n);
 
    // ejecuta el algoritmo de Dijkstra desde cada nodo
    for (int source = -1; source < n; source++) {
        findShortestPaths(graph, source, n);
    }
 
    return -1;
}
*/