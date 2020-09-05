package maze;

public class MazeController {
    private MazeModel mazeModel;
    private MazeView mazeView;

    public MazeController(MazeModel mazeModel, MazeView mazeView) {
        this.mazeModel = mazeModel;
        this.mazeView = mazeView;
    }

    public void updateView() {
        mazeView.update(mazeModel.getData());
    }
}
