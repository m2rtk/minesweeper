package com.example.minesweeper_rest.logic;

public class MinesweeperLogic {
    private final Grid grid;

    private int flags;
    private int bombs;
    private int closedCells;

    public MinesweeperLogic(Grid grid) {
        this.grid = grid;
        this.bombs = grid.bombs;
        this.closedCells = grid.getAll().size();
        this.flags = 0;
    }

    /**
     * Opens cell if possible (cell exists).
     * @return true if cell contained bomb, false otherwise.
     */
    public boolean open(int x, int y) {
        Cell cell = this.grid.get(x, y);
        if (cell == null) return false;

        if ( cell.open() ) {
            this.openAllBombs();
            return true;
        } else {
            this.propagate(x, y);
            this.closedCells = this.grid.getClosedCells().size();
            return false;
        }
    }

    /**
     * Toggles flag if possible (cell exists).
     * @return true if cell existed, false otherwise.
     */
    public boolean flag(int x, int y) {
        Cell cell = this.grid.get(x, y);
        if (cell == null) return false;

        this.flags += cell.toggleFlag() ? 1 : -1;
        return true;
    }

    private void openAllBombs() {
        this.grid.getAll().stream().filter(Cell::isBomb).forEach(Cell::open);
    }

    private void propagate(int x, int y) {
        this.grid.get(x, y).open();
        this.propagateIfPossible(x + 1, y);
        this.propagateIfPossible(x - 1, y);
        this.propagateIfPossible(x, y + 1);
        this.propagateIfPossible(x, y - 1);
    }

    private void propagateIfPossible(int x, int y) {
        if ( this.grid.get(x, y) != null && ! this.grid.get(x, y).isOpen() && ! this.grid.get(x, y).isBomb() ) {
            this.propagate(x, y);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public int getFlags() {
        return flags;
    }

    public int getBombs() {
        return bombs;
    }

    public int getClosedCells() {
        return closedCells;
    }
}
