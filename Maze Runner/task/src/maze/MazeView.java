package maze;

public class MazeView {
    public void update(int[][] data, boolean showPath) {
//        System.out.println("maze:" + data.length + "x" + data[0].length);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0 || data[i][j] == 2) {
                    if (showPath && data[i][j] == 2) {
                        System.out.print("//");
                    } else {
                        System.out.print("  ");
                    }
                } else {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println();
        }
    }
}
