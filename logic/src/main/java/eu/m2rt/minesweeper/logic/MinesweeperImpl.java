package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.exceptions.GameIndexOutOfBoundsException;
import eu.m2rt.minesweeper.logic.exceptions.GameOverException;
import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.m2rt.minesweeper.logic.MinesweeperState.State.*;

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
        open(getCell(row, col));
        setNewState();
        return this;
    }

    private void open(Cell cell) {
        cell.open();

        if ( ! cell.isBomb() ) {
            propagate(cell);
        }
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

    /**
     * Chords on cell if possible.
     * @throws GameOverException if chord is called after the game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     */
    public Minesweeper chord(int row, int col) {
        chord(getCell(row, col));
        setNewState();
        return this;
    }

    private void chord(Cell cell) {
        if (cell.isClosed()) {
            return;
        }

        Set<Cell> surroundingCells = state.getGrid().getSurroundingCells(cell);

        long surroundingFlaggedCellsCount = surroundingCells.stream()
                .filter(Cell::isFlagged)
                .count();

        if (cell.getNearbyBombs() != surroundingFlaggedCellsCount) {
            return;
        }

        Set<Cell> surroundingNotFlaggedCells = surroundingCells.stream()
                .filter(Predicate.not(Cell::isFlagged))
                .filter(Cell::isClosed)
                .collect(Collectors.toSet());

        if (surroundingNotFlaggedCells.stream().anyMatch(Cell::hasBomb)) {
            surroundingNotFlaggedCells.stream()
                    .filter(Cell::hasBomb)
                    .forEach(this::open);
            return;
        }

        surroundingNotFlaggedCells.forEach(this::open);
    }

    public MinesweeperState getState() {
        return state;
    }

    private Cell getCell(int row, int col) {
        if (state.getState() != PLAY) {
            throw new GameOverException();
        }

        return state.getGrid()
                .getCell(row, col)
                .orElseThrow(GameIndexOutOfBoundsException::new);
    }

    private void propagate(Cell cell) {
        if (cell.isClosed()) {
            cell.open();
        }

        if (cell.getNearbyBombs() > 0) {
            return;
        }

        Grid grid = state.getGrid();
        cell.location.surroundingPoints().stream()
                .map(grid::getCell)
                .flatMap(Optional::stream)
                .filter(Cell::isClosed)
                .forEach(this::propagate);
    }

    private void setNewState() {
        if (gameIsLost()) {
            state = lose();
        } else if (gameIsWon()) {
            state = win();
        } else {
            state = play();
        }
    }

    private boolean gameIsLost() {
        return state.getGrid().getCellsWithBombs().stream().anyMatch(Cell::isOpen);
    }

    private boolean gameIsWon() {
        return state.getGrid().getClosedCells().size() == state.getGrid().getNoOfBombs();
    }

    private MinesweeperState win() {
        return state.newState(WIN);
    }

    private MinesweeperState lose() {
        openAllBombs();
        return state.newState(LOSS);
    }

    private void openAllBombs() {
        state.getGrid()
                .getCellsWithBombs()
                .forEach(Cell::open);
    }

    private MinesweeperState play() {
        return state.newState(PLAY);
    }
}
