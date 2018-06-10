package eu.m2rt.minesweeper.logic;

public class Minesweeper {
    private GameState state;

    public Minesweeper(Grid grid) {
        state = new GameState(grid, false, false);
    }

    /**
     * Opens cell if possible (cell exists).
     * @throws GameOverException if open is called after game has ended.
     * @throws GameIndexOutOfBoundsException if x or y is out of bounds.
     */
    public GameState open(int x, int y) {
        Cell cell = getCell(x, y);

        if (cell.open()) {
            openAllBombs();
            end(false);
        } else {
            propagate(cell, x, y);

            if (grid().getClosedCells().size() == grid().bombs) {
                end(true);
            } else {
                // TODO: if immutable, create new GameState
            }
        }

        return state;
    }

    /**
     * Toggles flag if possible (cell exists).
     * @throws GameOverException if flag is called after the game has ended.
     * @throws GameIndexOutOfBoundsException if x or y is out of bounds.
     */
    public GameState flag(int x, int y) {
        getCell(x, y).toggleFlag();
        return state;
    }

    // TODO: Immutability, make copy of grid
    private Grid grid() {
        return state.getGrid();
    }

    private void end(boolean isWin) {
        state = new GameState(grid(), true, isWin);
    }

    private Cell getCell(int x, int y) {
        if (state.isGameOver()) {
            throw new GameOverException();
        }

        return grid().get(x, y).orElseThrow(GameIndexOutOfBoundsException::new);
    }

    private void openAllBombs() {
        grid().getCellsWithBombs().forEach(Cell::open);
    }

    private void propagate(Cell c, int x, int y) {
        c.open();
        propagateIfPossible(x + 1, y);
        propagateIfPossible(x - 1, y);
        propagateIfPossible(x, y + 1);
        propagateIfPossible(x, y - 1);
    }

    private void propagateIfPossible(int x, int y) {
        grid().get(x, y)
                .filter(c -> ! c.isOpen())
                .filter(c -> ! c.bomb)
                .ifPresent(c -> propagate(c, x, y));
    }

    public GameState getGameState() {
        return state;
    }
}
