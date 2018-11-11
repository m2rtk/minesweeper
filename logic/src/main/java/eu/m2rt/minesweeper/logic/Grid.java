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

    private Grid(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells.length == 0 ? 0 : cells[0].length;
        this.bombs = getCellsWithBombs().size();

        if (height == 0 || width == 0) {
            throw new IllegalArgumentException("Grid can not be empty.");
        }
    }

    /**
     * @return Cell object in grid at coordinates x, y. Returns empty if out of bounds.
     */
    public Optional<Cell> getCell(int row, int col) {
        if ( col < width && col >= 0 && row < height && row >= 0 ) {
            return Optional.of(cells[row][col]);
        } else {
            return Optional.empty();
        }
    }

    Optional<Cell> getCell(Point p) {
        return getCell(p.row, p.col);
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

    Set<Cell> getSurroundingCells(Cell cell) {
        return cell.location.surroundingPoints()
                .stream()
                .map(this::getCell)
                .flatMap(Optional::stream)
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
        private final int height, width;
        private final Cell.Builder[][] builderCells;

        public Builder(int height, int width) {
            this.height = height;
            this.width = width;
            builderCells = new Cell.Builder[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    builderCells[row][col] = new Cell.Builder(Point.of(row, col));
                }
            }
        }

        @SuppressWarnings("UnusedReturnValue")
        public Builder addBomb(Point location) {
            getCell(location).ifPresent(Cell.Builder::markAsBomb);
            getSurroundingCells(location).forEach(Cell.Builder::incrementNearbyBombs);
            return this;
        }

        public Builder addBomb(int row, int col) {
            return addBomb(Point.of(row, col));
        }

        public Builder addBombs(Collection<Point> bombs) {
            bombs.forEach(this::addBomb);
            return this;
        }

        public Grid build() {
            Cell[][] cells = new Cell[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    cells[row][col] = builderCells[row][col].build();
                }
            }

            return new Grid(cells);
        }

        private Optional<Cell.Builder> getCell(Point p) {
            if (inBounds(p)) {
                return Optional.of(builderCells[p.row][p.col]);
            } else {
                return Optional.empty();
            }
        }

        private Set<Cell.Builder> getSurroundingCells(Point location) {
            return location.surroundingPoints().stream()
                    .map(this::getCell)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toSet());
        }

        private boolean inBounds(Point p) {
            return inBounds(p.row, p.col);
        }

        private boolean inBounds(int row, int col) {
            return row >= 0 && col >= 0 && row < height && col < width;
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
