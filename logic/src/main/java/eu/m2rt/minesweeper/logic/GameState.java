package eu.m2rt.minesweeper.logic;

// TODO 100% immutable, atm grid is mutable
public class GameState {
    private final Grid grid;
    private final boolean gameOver;
    private final boolean isWin;

    public GameState(Grid grid, boolean gameOver, boolean isWin) {
        this.grid = grid;
        this.gameOver = gameOver;
        this.isWin = isWin;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return isWin;
    }
}
