package eu.m2rt.minesweeper.logic;

public class MinesweeperImpl implements Minesweeper {
    private MinesweeperState state;

    public MinesweeperImpl(Grid grid) {
        state = new MinesweeperState(grid, false, false);
    }

    /**
     * Opens cell if possible (cell exists).
     * @throws GameOverException if open is called after game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     */
    public Minesweeper open(int row, int col) {
        Cell cell = getCell(row, col);

        if (cell.open().isBomb()) {
            openAllBombs();
            end(false);
        } else {
            propagate(cell, row, col);

            if (grid().getClosedCells().size() == grid().getBombs()) {
                end(true);
            } else {
                // TODO: if immutable, create new MinesweeperState
            }
        }

        return this;
    }

    /**
     * Toggles flag if possible (cell exists).
     * @throws GameOverException if flag is called after the game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     */
    public Minesweeper flag(int row, int col) {
        getCell(row, col).toggleFlag();
        return this;
    }

    public MinesweeperState getState() {
        return state;
    }

    // TODO: Immutability, make copy of grid
    private Grid grid() {
        return state.getGrid();
    }

    private void end(boolean isWin) {
        state = new MinesweeperState(grid(), true, isWin);
    }

    private Cell getCell(int row, int col) {
        if (state.isGameOver()) {
            throw new GameOverException();
        }

        return grid().get(row, col).orElseThrow(GameIndexOutOfBoundsException::new);
    }

    private void openAllBombs() {
        grid().getCellsWithBombs().forEach(Cell::open);
    }

    private void propagate(Cell cell, int row, int col) {
        cell.open();
        propagateIfPossible(row, col + 1);
        propagateIfPossible(row, col - 1);
        propagateIfPossible(row + 1, col);
        propagateIfPossible(row - 1, col);
    }

    private void propagateIfPossible(int row, int col) {
        grid().get(row, col)
                .filter(Cell::isClosed)
                .filter(c -> ! c.bomb)
                .ifPresent(c -> propagate(c, row, col));
    }
}
