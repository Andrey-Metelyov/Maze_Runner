package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final int vertices;
    private int edges;
    private final Map<Integer, List<Edge>> adjacency;

    static class Edge {
        private final int vertex;
        private final double weight;

        public Edge(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public int getVertex() {
            return vertex;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "{" + vertex + ", " + weight + "}";
        }
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacency = new HashMap<>(vertices);
    }

    public int vertices() {
        return vertices;
    }

    public List<Edge> edges(int vertex) {
        return adjacency.getOrDefault(vertex, new ArrayList<>());
    }

    public boolean isAdjacent(int v, int w) {
        List<Edge> adj = edges(v);
        for (Edge edge : adj) {
            if (edge.vertex == w) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(int v, int w, double weight) {
        if (adjacency.containsKey(v)) {
            List<Edge> adj = adjacency.get(v);
            adj.add(new Edge(w, weight));
        } else {
            List<Edge> adj = new ArrayList<>();
            adj.add(new Edge(w, weight));
            adjacency.put(v, adj);
        }
        edges++;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                ", adjacency=" + adjacency +
                '}';
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(2, 3, 2);
        g.addEdge(2, 4, 3);
        g.addEdge(2, 5, 1);
        g.addEdge(2, 6, 7);
        g.addEdge(1, 2, 6);
        System.out.println(g);
    }
}
