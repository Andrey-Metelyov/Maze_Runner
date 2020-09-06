package maze;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class PrimAlgorithm {
    private Graph graph;

    static class Edge implements Comparable<Edge> {
        int v;
        Graph.Edge edge;

        public Edge(int v, Graph.Edge edge) {
            this.v = v;
            this.edge = edge;
        }

        @Override
        public int compareTo(@NotNull Edge other) {
            if (this.edge.getWeight() > other.edge.getWeight()) {
                return 1;
            } else if (this.edge.getWeight() < other.edge.getWeight()) {
                return -1;
            }
            return edge.getVertex() - other.edge.getVertex();
        }

        @Override
        public String toString() {
            return v + ":(" + edge.getVertex() + ',' + edge.getWeight() + ')';
        }
    }

    PrimAlgorithm(Graph graph) {
        this.graph = graph;
    }

    Graph mst() {
        Graph result = new Graph(graph.vertices());
        Set<Integer> vertices = new HashSet<>();
        vertices.add(0);
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        List<Graph.Edge> vertexEdges = graph.edges(0);
        for (Graph.Edge vertexEdge : vertexEdges) {
            edges.add(new Edge(0, vertexEdge));
        }

        while (!edges.isEmpty()) {
            System.out.println(edges);
            Edge edge = edges.poll();
            if (vertices.contains(edge.edge.getVertex())) {
//                System.out.println("skip " + edge.v + ":(" + edge.edge.getVertex() + "," + edge.edge.getWeight() + ")");
                continue;
            }
            int newVertex = edge.edge.getVertex();
            vertices.add(newVertex);
//            System.out.println("add vertex " + newVertex);
            for (Graph.Edge newEdge : graph.edges(newVertex)) {
                if (!vertices.contains(newEdge.getVertex())) {
                    edges.add(new Edge(newVertex, newEdge));
                }
            }
            result.addEdge(edge.v, edge.edge.getVertex(), edge.edge.getWeight());
        }
        return result;
    }

    public static void main(String[] args) {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(0, 3, 2);
        g.addEdge(1, 0, 1);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 0, 1);
        g.addEdge(2, 3, 4);
        g.addEdge(2, 4, 4);
        g.addEdge(3, 0, 2);
        g.addEdge(3, 1, 2);
        g.addEdge(3, 2, 4);
        g.addEdge(3, 4, 3);
        g.addEdge(3, 5, 2);
        g.addEdge(4, 2, 4);
        g.addEdge(4, 3, 3);
        g.addEdge(4, 5, 3);
        g.addEdge(4, 6, 5);
        g.addEdge(4, 7, 1);
        g.addEdge(5, 3, 2);
        g.addEdge(5, 4, 3);
        g.addEdge(5, 7, 3);
        g.addEdge(6, 4, 5);
        g.addEdge(6, 7, 2);
        g.addEdge(7, 4, 1);
        g.addEdge(7, 5, 3);
        g.addEdge(7, 6, 2);
        System.out.println(g);
        PrimAlgorithm alg = new PrimAlgorithm(g);
        System.out.println(alg.mst());
    }
}
