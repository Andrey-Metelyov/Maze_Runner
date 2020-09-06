package maze;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the size of a maze");
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        MazeModel mazeModel = new MazeModel(width, height);
        MazeView mazeView = new MazeView();
        MazeController mazeController = new MazeController(mazeModel, mazeView);
        mazeController.updateView();
    }
}
