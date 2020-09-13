package maze;

import java.util.*;

public class DijkstraAlgorithm {
    private final Graph graph;

    DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    List<Integer> getShortestPath(int from, int to) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Double> distances = new HashMap<>();
        boolean[] used = new boolean[graph.vertices()];
        int[] prev = new int[graph.vertices()];

        distances.put(from, 0.0);
        int current = from;
        double min_dist = 0.0;
        while (min_dist < Double.MAX_VALUE) {
            used[current] = true;
            List<Graph.Edge> nei = graph.edges(current);
            for (Graph.Edge n : nei) {
                if (distances.get(current) + n.getWeight() < distances.getOrDefault(n.getVertex(), Double.MAX_VALUE)) {
                    distances.put(n.getVertex(), distances.get(current) + n.getWeight());
                    prev[n.getVertex()] = current;
                }
            }
//            System.out.println("nei = " + nei);
//            System.out.println("distances = " + distances);

            min_dist = Double.MAX_VALUE;
            for (int i = 0; i < graph.vertices(); i++) {
                if (!used[i] && distances.getOrDefault(i, Double.MAX_VALUE) < min_dist) {
                    min_dist = distances.get(i);
                    current = i;
                }
            }
//            System.out.println("current = " + current);
            if (current == to) {
                break;
            }
        }

        while (current != from) {
            result.add(current);
            current = prev[current];
        }
        result.add(from);
        Collections.reverse(result);

        return result;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(25);
        /*
        *   1 2 3 4 5
        * 1  | | | |
        *    | | | |
        * 2  | | | |
        *    | | | |
        * 3
        *    --------
        * 4
        *  --------  |
        * 5
        * */
        graph.addBothWayEdges(0,5,1);
        graph.addBothWayEdges(1,6,1);
        graph.addBothWayEdges(2,7,1);
        graph.addBothWayEdges(3,8,1);
        graph.addBothWayEdges(4,9,1);

        graph.addBothWayEdges(5,10,1);
        graph.addBothWayEdges(6,11,1);
        graph.addBothWayEdges(7,12,1);
        graph.addBothWayEdges(8,13,1);
        graph.addBothWayEdges(9,14,1);

        graph.addBothWayEdges(10,11,1);
        graph.addBothWayEdges(10,15,1);
        graph.addBothWayEdges(11,12,1);
        graph.addBothWayEdges(12,13,1);
        graph.addBothWayEdges(13,14,1);

        graph.addBothWayEdges(15,16,1);
        graph.addBothWayEdges(16,17,1);
        graph.addBothWayEdges(17,18,1);
        graph.addBothWayEdges(18,19,1);
        graph.addBothWayEdges(19,24,1);

        graph.addBothWayEdges(24,23,1);
        graph.addBothWayEdges(23,22,1);
        graph.addBothWayEdges(22,21,1);
        graph.addBothWayEdges(21,20,1);

        graph.addBothWayEdges(15,20,1);

        DijkstraAlgorithm da = new DijkstraAlgorithm(graph);

        System.out.println(da.getShortestPath(4, 20));
    }
}
