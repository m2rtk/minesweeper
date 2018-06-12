package eu.m2rt.minesweeper.logic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Grid {
    private final Cell[][] cells;
    private final int height, width, bombs;

    Grid(Cell[][] cells, int height, int width, int bombs) {
        this.cells = cells;
        this.height = height;
        this.width = width;
        this.bombs = bombs;
    }

    Grid(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells.length == 0 ? 0 : cells[0].length;
        this.bombs = getCellsWithBombs().size();
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
                .filter(Cell::hasBomb)
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

    static Grid copy(Grid grid) {
        Cell[][] cells = new Cell[grid.height][grid.width];


        for (int row = 0; row < grid.height; row++) {
            for (int col = 0; col < grid.width; col++) {
                cells[row][col] = Cell.copy(grid.cells[row][col]);
            }
        }

        return new Grid(cells, grid.height, grid.width, grid.bombs);
    }
}
