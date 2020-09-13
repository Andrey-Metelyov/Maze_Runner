package maze;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class MazeModel implements Serializable {
    int width;
    int height;
    private int[][] data;
    private Graph mazeGraph;

    MazeModel() {
        getPredefined();
    }

    MazeModel(int height, int width) {
        this.width = width;
        this.height = height;
        int graphHeight = (height - 1) / 2;
//        if (height % 2 == 0 && (height / 2) % 2 == 1) {
//            graphHeight = height / 2 - (height / 2) % 2;
//        } else {
//            graphHeight = height / 2;
//        }
        int graphWidth = (width - 1) / 2;
//        System.out.println("maze: " + height + "x" + width);
//        System.out.println("graph: " + graphHeight + "x" + graphWidth);
        Graph graph = new Graph(  graphHeight * graphWidth);
        initGraph(graph, graphHeight, graphWidth);
        PrimAlgorithm pa = new PrimAlgorithm(graph);
        mazeGraph = pa.mst();
        initMaze(graphHeight, graphWidth);
//        Random random = new Random();
//        generateWalls(random);
//        generateInner(random);
    }

    private void initMaze(int height, int width) {
//        System.out.println("maze graph: " + height + "x" + width);
//        System.out.println("maze graph:\n" + mazeGraph);
//        showGraph(mazeGraph);
//        System.out.println("height: " + height);
//        System.out.println("width: " + width);
        DijkstraAlgorithm da = new DijkstraAlgorithm(mazeGraph);
        List<Integer> path = da.getShortestPath(0, height * width - 1);
//        System.out.println(path);
        data = new int[2 * height + 1][2 * width + 1];
        for (int i = 0; i < 2 * width + 1; i++) {
            data[0][i] = 1;
            data[2 * height][i] = 1;
        }
        if ((2 * height + 2) < this.height) {
            // add one more wall
            for (int i = 0; i < 2 * width + 1; i++) {
                data[2 * height + 1][i] = 1;
            }
        }
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
//                List<Graph.Edge> edges = mazeGraph.edges(width * i + j);
                int row = 2 * i + 1;
                int column = 2 * j + 1;
                int current = width * i + j;
                boolean isPath = path.contains(current);

                data[row][column] = isPath ? 2 : 0;
                data[row + 1][column + 1] = 1;

                // to the right?
                if (mazeGraph.isAdjacent(current, current + 1)) {
                    if (isPath && path.contains(current + 1)) {
                        data[row][column + 1] = 2;
                    } else {
                        data[row][column + 1] = 0;
                    }
                } else {
                    data[row][column + 1] = 1;
                }
                // to down
                if (mazeGraph.isAdjacent(current, current + width)) {
                    if (isPath && path.contains(current + width)) {
                        data[row + 1][column] = 2;
                    } else {
                        data[row + 1][column] = 0;
                    }
                } else {
                    data[row + 1][column] = 1;
                }
            }
//            for (int[] datum : data) {
//                System.out.println(Arrays.toString(datum));
//            }
//            System.out.println();
        }
        // last line
        for (int i = 0; i < width - 1; i++) {
            int row = 2 * height - 1;
            int column = 2 * i + 1;
            int current = width * (height - 1) + i;
            boolean isPath = path.contains(current);

            data[row][column] = isPath ? 2 : 0;
            if (mazeGraph.isAdjacent(current, current + 1)) {
                if (isPath && path.contains(current + 1)) {
                    data[row][column + 1] = 2;
                } else {
                    data[row][column + 1] = 0;
                }
            } else {
                data[row][column + 1] = 1;
            }
        }
//        for (int[] datum : data) {
//            System.out.println(Arrays.toString(datum));
//        }
//        System.out.println();
        // last column
        for (int i = 0; i < height - 1; i++) {
            int row = 2 * i + 1;
            int column = 2 * width - 1;
            int current = width * (i + 1) - 1;
            boolean isPath = path.contains(current);

            data[row][column] = isPath ? 2 : 0;
            if (mazeGraph.isAdjacent(current, current + width)) {
                if (isPath && path.contains(current + width)) {
                    data[row + 1][column] = 2;
                } else {
                    data[row + 1][column] = 0;
                }
            } else {
                data[row + 1][column] = 1;
            }
        }
        data[height * 2 - 1][width * 2 - 1] = 2;
//        for (int[] datum : data) {
//            System.out.println(Arrays.toString(datum));
//        }
//        System.out.println();
        addWallsWithExit(data);
    }

    private void addWallsWithExit(int[][] data) {
        boolean hasEntrance = false;
        boolean hasExit = false;
        for (int i = 1; i < data.length - 1; i++) {
            if (!hasEntrance && data[i][1] == 2) {
                hasEntrance = true;
                data[i][0] = 2;
            } else {
                data[i][0] = 1;
            }
        }
        for (int i = data.length - 2; i > 0; i--) {
            if (!hasExit && data[i][data[i].length - 2] == 2) {
                hasExit = true;
                data[i][data[i].length - 1] = 2;
            } else {
                data[i][data[i].length - 1] = 1;
            }
        }
    }

//    private void showGraph(Graph mazeGraph) {
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                int current = width * i + j;
//                boolean hasRight = mazeGraph.isAdjacent(current, current + 1);
//                boolean hasDown = mazeGraph.isAdjacent(current, current + width + 1);
//                if (hasRight && hasDown) {
//                    System.out.print("\u2192\u2193");
//                } else if (hasRight) {
//                    System.out.print("\u2192 ");
//                } else if (hasDown) {
//                    System.out.print("\u2193 ");
//                } else {
//                    System.out.print("[]");
//                }
//            }
//            System.out.println();
//        }
//    }

    private void initGraph(Graph graph, int height, int width) {
        Random random = new Random();
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width - 1; j++) {
                int currentVertex = i * width + j;
                // to the left
                double weight = random.nextDouble();
                graph.addEdge(currentVertex, currentVertex + 1, weight);
                graph.addEdge(currentVertex + 1, currentVertex, weight);
//                System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + 1));
                // to the down
                weight = random.nextDouble();
                graph.addEdge(currentVertex, currentVertex + width, weight);
                graph.addEdge(currentVertex + width, currentVertex, weight);
//                System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + width));
            }
        }
//        System.out.println("last column:");
        for (int i = 0; i < height - 1; i++) {
            int currentVertex = width * (i + 1) - 1;
            // to the down
            double weight = random.nextDouble();
            graph.addEdge(currentVertex, currentVertex + width, weight);
            graph.addEdge(currentVertex + width, currentVertex, weight);
//            System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + width));
        }
//        System.out.println("last line:");
        for (int i = 0; i < width; i++) {
            int currentVertex = width * (height - 1) + i;
            // to the left
            double weight = random.nextDouble();
            graph.addEdge(currentVertex, currentVertex + 1, weight);
            graph.addEdge(currentVertex + 1, currentVertex, weight);
//            System.out.println("add edge: " + currentVertex + "<->" + (currentVertex + 1));
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

    public static void main(String[] args) {
        int height = 17;
        int width = 17;
        MazeModel model = new MazeModel(height, width);
//        Graph mazeGraph = new Graph(height * (width + 1));
        /*
        *   0=[{4}],
            1=[{5}],
            2=[{6}],
            3=[{7}],
            4=[{0}, {8}, {5}],
            5=[{4}, {9}, {1}],
            6=[{10}, {2}],
            7=[{11}, {3}],
            8=[{4}],
            9=[{5}, {10}],
            10=[{9}, {11}, {6}],
            11=[{10}, {7}]}
        * */
//        mazeGraph.addEdge(0, 4,1);        mazeGraph.addEdge(4, 0,1);
//        mazeGraph.addEdge(1, 5,1);        mazeGraph.addEdge(5, 1,1);
//        mazeGraph.addEdge(2, 6,1);        mazeGraph.addEdge(6, 2,1);
//        mazeGraph.addEdge(2, 3,1);        mazeGraph.addEdge(3, 2,1);
//        mazeGraph.addEdge(4, 5,1);        mazeGraph.addEdge(5, 4,1);
//        mazeGraph.addEdge(4, 8,1);        mazeGraph.addEdge(8, 4,1);
//        mazeGraph.addEdge(5, 9,1);        mazeGraph.addEdge(9, 5,1);
//        mazeGraph.addEdge(6, 10,1);       mazeGraph.addEdge(10, 6,1);
//        mazeGraph.addEdge(7, 11,1);       mazeGraph.addEdge(11, 7,1);
//        mazeGraph.addEdge(9, 10,1);       mazeGraph.addEdge(10, 9,1);
//        mazeGraph.addEdge(10, 11,1);      mazeGraph.addEdge(11, 10,1);
//        model.initMaze(mazeGraph, 3, 4);*/
        MazeView view = new MazeView();
        view.update(model.getData(), true);
    }
}
