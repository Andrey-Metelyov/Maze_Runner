package maze;

import java.util.List;
import java.util.Random;

public class MazeModel {
    int width;
    int height;
    private int[][] data;

    MazeModel() {
        getPredefined();
    }

    MazeModel(int width, int height) {
        this.width = width;
        this.height = height;
        Graph graph = new Graph((width + 1) * height);
        initGraph(graph);
        PrimAlgorithm pa = new PrimAlgorithm(graph);
        Graph mazeGraph = pa.mst();
        initMaze(mazeGraph);
//        Random random = new Random();
//        generateWalls(random);
//        generateInner(random);
    }

    private void initMaze(Graph mazeGraph) {
        System.out.println("maze graph:\n" + mazeGraph);
        data = new int[2 * height - 1][2 * width + 1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
//                List<Graph.Edge> edges = mazeGraph.edges(width * i + j);
                int current = width * i + j;
                if (mazeGraph.isAdjacent(current, current + 1)) {
                    data[i][j] = 0;
                } else {
                    data[i][j] = 1;
                }
            }
        }
    }

    private void initGraph(Graph graph) {
        Random random = new Random();
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                int currentVertex = i * (width + 1) + j;
                // to the left
                double weight = random.nextDouble();
                graph.addEdge(currentVertex, currentVertex + 1, weight);
                graph.addEdge(currentVertex + 1, currentVertex, weight);
                System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + 1));
                // to the down
                weight = random.nextDouble();
                graph.addEdge(currentVertex, currentVertex + width + 1, weight);
                graph.addEdge(currentVertex + width + 1, currentVertex, weight);
                System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + width + 1));
            }
        }
        System.out.println("last column:");
        for (int i = 0; i < height - 1; i++) {
            int currentVertex = (width + 1) * (i + 1) - 1;
            // to the down
            double weight = random.nextDouble();
            graph.addEdge(currentVertex, currentVertex + width + 1, weight);
            graph.addEdge(currentVertex + width + 1, currentVertex, weight);
            System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + width + 1));
        }
        System.out.println("last line:");
        for (int i = 0; i < width; i++) {
            int currentVertex = (width + 1) * (height - 1) + i;
            // to the left
            double weight = random.nextDouble();
            graph.addEdge(currentVertex, currentVertex + 1, weight);
            graph.addEdge(currentVertex + 1, currentVertex, weight);
            System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + 1));
        }
    }

    private void getPredefined() {
        data = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    }

    private void generateWalls(Random random) {
        for (int i = 0; i < data.length; i++) {
            data[0][i] = 1;
            data[data.length - 1][i] = 1;
        }
        generateWallWithEntrance(random, 0);
        generateWallWithEntrance(random, data.length - 1);
    }

    private void generateWallWithEntrance(Random random, int column) {
        boolean entrancePresent = false;
        for (int i = 1; i < data.length - 1; i++) {
            if (entrancePresent) {
                data[i][column] = 1;
            } else {
                data[i][column] = random.nextInt(2);
                if (data[i][column] == 0) {
                    entrancePresent = true;
                }
            }
        }
    }

    private void generateInner(Random random) {
        for (int i = 1; i < data.length - 1; i++) {
            for (int j = 1; j < data[i].length - 1; j++) {
                data[i][j] = random.nextInt(2);
            }
        }
    }

    public int[][] getData() {
        return data;
    }
}
