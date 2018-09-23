package eu.m2rt.minesweeper.logic;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class MinesweeperState {
    private final Grid grid;
    private final State state;

    public enum State {
        WIN, LOSS, PLAY
    }

    public MinesweeperState(Grid grid, State state) {
        this.grid = grid.copy();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinesweeperState that = (MinesweeperState) o;
        return Objects.equals(grid, that.grid) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid, state);
    }
}
