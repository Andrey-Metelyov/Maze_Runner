package maze;

import java.util.Scanner;

public class MazeController {
    private MazeModel mazeModel;
    private MazeView mazeView;

    public enum Menu {
        EXIT,
        GENERATE,
        LOAD,
        SAVE,
        DISPLAY,
        ESCAPE
    }

    public MazeController() {
        mazeView = new MazeView();
        mazeModel = null;
    }

    public MazeController(MazeModel mazeModel, MazeView mazeView) {
        this.mazeModel = mazeModel;
        this.mazeView = mazeView;
    }

    public void loadMaze() {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        try {
            mazeModel = (MazeModel) SerializationUtils.deserialize(filename);
        } catch (Exception e) {
            System.out.println("The file " +  filename + " does not exist");
        }
    }

    public void saveMaze() {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        try {
            SerializationUtils.serialize(mazeModel, filename);
        } catch (Exception e) {
            System.out.println("Error saving maze to file " + filename);
        }
    }

    public void generateMaze() {
        System.out.println("Enter the size of a new maze");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        mazeModel = new MazeModel(size, size);
    }

    public void updateView() {
        mazeView.update(mazeModel.getData());
    }

    public void run() {
        Menu res;
        while ((res = showMenu()) != Menu.EXIT) {
            switch (res) {
                case EXIT:
                    System.out.println("Bye!");
                    return;
                case GENERATE:
                    generateMaze();
                case DISPLAY:
                    updateView();
                    break;
                case SAVE:
                    saveMaze();
                    break;
                case LOAD:
                    loadMaze();
                    break;
                case ESCAPE:
                    findEscape();
                default:
                    System.out.println("Error");
            }
        }
    }

    private void findEscape() {

    }

    private Menu showMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Menu ===");
        if (mazeModel == null) {
            while (true) {
                System.out.println("1. Generate a new maze\n" +
                        "2. Load a maze\n" +
                        "0. Exit");
                String res = scanner.nextLine();
                switch (res) {
                    case "0":
                        return Menu.EXIT;
                    case "1":
                        return Menu.GENERATE;
                    case "2":
                        return Menu.LOAD;
                    default:
                        System.out.println("Incorrect option. Please try again");
                }
            }
        } else {
            while (true) {
                System.out.println("1. Generate a new maze\n" +
                        "2. Load a maze\n" +
                        "3. Save the maze\n" +
                        "4. Display the maze\n" +
                        "5. Find the escape\n" +
                        "0. Exit");
                String res = scanner.nextLine();
                switch (res) {
                    case "0":
                        return Menu.EXIT;
                    case "1":
                        return Menu.GENERATE;
                    case "2":
                        return Menu.LOAD;
                    case "3":
                        return Menu.SAVE;
                    case "4":
                        return Menu.DISPLAY;
                    case "5":
                        return Menu.ESCAPE;
                    default:
                        System.out.println("Incorrect option. Please try again");
                }
            }
        }
    }
}
