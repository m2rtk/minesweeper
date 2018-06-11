package eu.m2rt.minesweeper.logic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Grid {
    private final Cell[][] cells;
    private final int height, width, bombs;

    Grid(Cell[][] cells) {
        this.cells = cells;

        height = cells.length;
        width  = height == 0 ? 0 : cells[0].length;
        bombs  = getCellsWithBombs().size();
    }

    /**
     * @return Cell object in grid at coordinates x, y. Returns empty if out of bounds.
     */
    public Optional<Cell> get(int row, int col) {
        if ( col < width && col >= 0 && row < height && row >= 0 ) {
            return Optional.of(cells[row][col]);
        } else {
            return Optional.empty();
        }
    }

    Set<Cell> getClosedCells() {
        return allCells()
                .filter(Cell::isClosed)
                .collect(Collectors.toSet());
    }

    Set<Cell> getCellsWithBombs() {
        return allCells()
                .filter(c -> c.bomb)
                .collect(Collectors.toSet());
    }

    private Stream<Cell> allCells() {
        return Stream.of(cells)
                .flatMap(Stream::of);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBombs() {
        return bombs;
    }
}
