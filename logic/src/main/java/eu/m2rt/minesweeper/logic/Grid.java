package eu.m2rt.minesweeper.logic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Grid {
    private final Cell[][] cells;
    private final int height, width, bombs;

    private Grid(Cell[][] cells, int height, int width, int bombs) {
        this.cells = cells;
        this.height = height;
        this.width = width;
        this.bombs = bombs;
    }

    public Grid(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells.length == 0 ? 0 : cells[0].length;
        this.bombs = getCellsWithBombs().size();

        if (height == 0 || width == 0) {
            throw new IllegalArgumentException("Cells must not be 0 sized.");
        }
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

    public int getNoOfBombs() {
        return bombs;
    }

    Grid copy() {
        int height = getHeight();
        int width  = getWidth();
        int bombs  = getNoOfBombs();

        Cell[][] cells = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = new Cell(this.cells[row][col]);
            }
        }

        return new Grid(cells, height, width, bombs);
    }

    public static class Builder {
        private final Cell.Builder[][] builderCells;

        public Builder(int height, int width) {
            builderCells = new Cell.Builder[height][width];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    builderCells[row][col] = new Cell.Builder();
                }
            }
        }

        public Builder addBomb(Point p) {
            builderCells[p.row][p.col].bomb();
            incrementSurroundingCellsBombCount(p.row, p.col);
            return this;
        }

        public Builder addBombs(Collection<Point> bombs) {
            bombs.forEach(this::addBomb);
            return this;
        }

        public Grid build() {
            Cell[][] cells = new Cell[builderCells.length][builderCells[0].length];

            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[0].length; col++) {
                    cells[row][col] = builderCells[row][col].build();
                }
            }

            return new Grid(cells);
        }

        private void incrementSurroundingCellsBombCount(int row, int col) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (inBounds(row + i, col + j)) {
                        builderCells[row + i][col + j].incrementNearbyBombs();
                    }
                }
            }
        }

        private boolean inBounds(int row, int col) {
            return row >= 0 && col >= 0 && row < builderCells.length && col < builderCells[0].length;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return height == grid.height &&
                width == grid.width &&
                bombs == grid.bombs &&
                Arrays.equals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(height, width, bombs);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }
}
