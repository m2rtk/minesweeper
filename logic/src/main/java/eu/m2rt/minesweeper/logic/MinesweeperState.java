package eu.m2rt.minesweeper.logic;

@SuppressWarnings("WeakerAccess")
public class MinesweeperState {
    private final Grid grid;
    private final State state;

    enum State {
        WIN, LOSS, PLAY
    }

    public MinesweeperState(Grid grid, State state) {
        this.grid = Grid.copy(grid);
        this.state = state;
    }

    public MinesweeperState(Grid grid) {
        this(grid, State.PLAY);
    }

    public Grid getGrid() {
        return grid;
    }

    public State getState() {
        return state;
    }
}
