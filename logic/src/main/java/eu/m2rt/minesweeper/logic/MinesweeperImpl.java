package eu.m2rt.minesweeper.logic;

import static eu.m2rt.minesweeper.logic.MinesweeperState.State.LOSS;
import static eu.m2rt.minesweeper.logic.MinesweeperState.State.PLAY;
import static eu.m2rt.minesweeper.logic.MinesweeperState.State.WIN;

public class MinesweeperImpl implements Minesweeper {
    private MinesweeperState state;

    MinesweeperImpl(MinesweeperState state) {
        this.state = state;
    }

    public MinesweeperImpl(Grid grid) {
        this(new MinesweeperState(grid));
    }

    /**
     * Opens cell if possible (cell exists).
     * @throws GameOverException if open is called after game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     */
    public Minesweeper open(int row, int col) {
        Cell cell = getCell(row, col);
        Grid grid = state.getGrid();

        if (cell.open().isBomb()) {
            openAllBombs();
            state = new MinesweeperState(grid, LOSS);
        } else {
            propagate(cell, row, col);

            if (grid.getClosedCells().size() == grid.getBombs()) {
                state = new MinesweeperState(grid, WIN);
            } else {
                state = new MinesweeperState(grid);
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

    private Cell getCell(int row, int col) {
        if (state.getState() != PLAY) {
            throw new GameOverException();
        }

        return state.getGrid()
                .get(row, col)
                .orElseThrow(GameIndexOutOfBoundsException::new);
    }

    private void openAllBombs() {
        state.getGrid().getCellsWithBombs().forEach(Cell::open);
    }

    private void propagate(Cell cell, int row, int col) {
        cell.open();
        propagateIfPossible(row, col + 1);
        propagateIfPossible(row, col - 1);
        propagateIfPossible(row + 1, col);
        propagateIfPossible(row - 1, col);
    }

    private void propagateIfPossible(int row, int col) {
        state.getGrid().get(row, col)
                .filter(Cell::isClosed)
                .filter(Cell::hasNoBomb)
                .ifPresent(c -> propagate(c, row, col));
    }
}
