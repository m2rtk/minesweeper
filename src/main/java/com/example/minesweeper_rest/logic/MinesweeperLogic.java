package com.example.minesweeper_rest.logic;

public class MinesweeperLogic {
    private final Grid grid;
    private final long id;

    private boolean gameOver;
    private boolean win;

    public MinesweeperLogic(Grid grid, long id) {
        this.grid = grid;
        this.id = id;
        this.gameOver = false;
        this.win = false;
    }

    /**
     * Opens cell if possible (cell exists).
     * @throws GameOverException if open is called after game has ended.
     * @throws GameIndexOutOfBoundsException if x or y is out of bounds.
     */
    public void open(int x, int y) {
        Cell cell = this.getCell(x, y);

        if (cell.open()) {
            this.openAllBombs();
            this.end(false);
        } else {
            this.propagate(x, y);

            if (this.grid.getClosedCells().size() == this.grid.bombs) {
                this.end(true);
            }
        }
    }

    /**
     * Toggles flag if possible (cell exists).
     * @throws GameOverException if flag is called after the game has ended.
     * @throws GameIndexOutOfBoundsException if x or y is out of bounds.
     */
    public void flag(int x, int y) {
        this.getCell(x, y).toggleFlag();
    }

    private void end(boolean win) {
        this.gameOver = true;
        this.win = win;
    }

    private Cell getCell(int x, int y) {
        if (gameOver) {
            throw new GameOverException();
        }

        Cell cell = this.grid.get(x, y);
        if (cell == null) {
            throw new GameIndexOutOfBoundsException();
        }

        return cell;
    }

    private void openAllBombs() {
        this.grid.getAll().stream().filter(Cell::hasBomb).forEach(Cell::open);
    }

    private void propagate(int x, int y) {
        this.grid.get(x, y).open();
        this.propagateIfPossible(x + 1, y);
        this.propagateIfPossible(x - 1, y);
        this.propagateIfPossible(x, y + 1);
        this.propagateIfPossible(x, y - 1);
    }

    private void propagateIfPossible(int x, int y) {
        if (this.grid.get(x, y) != null && ! this.grid.get(x, y).isOpen() && ! this.grid.get(x, y).hasBomb()) {
            this.propagate(x, y);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public long getId() {
        return id;
    }
}
