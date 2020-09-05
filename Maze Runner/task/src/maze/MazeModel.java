package maze;

import java.util.Random;

public class MazeModel {
    final int MAZE_SIZE = 10;
    private int[][] data = new int[MAZE_SIZE][MAZE_SIZE];

    MazeModel() {
        getPredefined();
//        Random random = new Random();
//        generateWalls(random);
//        generateInner(random);
    }

    private void getPredefined() {
        data = new int[][]{
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
        for (int i = 0; i < MAZE_SIZE; i++) {
            data[0][i] = 1;
            data[MAZE_SIZE - 1][i] = 1;
        }
        generateWallWithEntrance(random, 0);
        generateWallWithEntrance(random, MAZE_SIZE - 1);
    }

    private void generateWallWithEntrance(Random random, int column) {
        boolean entrancePresent = false;
        for (int i = 1; i < MAZE_SIZE - 1; i++) {
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
