package maze;

public class Main {
    public static void main(String[] args) {
        MazeModel mazeModel = new MazeModel();
        MazeView mazeView = new MazeView();
        MazeController mazeController = new MazeController(mazeModel, mazeView);
        mazeController.updateView();
    }
}
