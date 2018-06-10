package eu.m2rt.minesweeper.logic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Grid {
    final Cell[][] cells;
    final int height, width;
    final int bombs;

    Grid(Cell[][] cells) {
        this.cells = cells;

        height = cells.length;
        width  = height == 0 ? 0 : cells[0].length;
        bombs  = getCellsWithBombs().size();
    }

    /**
     * @return Cell object in grid at coordinates x, y. Returns empty if out of bounds.
     */
    Optional<Cell> get(int x, int y) {
        if ( x < width && x > -1 && y < height && y > -1 ) {
            return Optional.of(cells[y][x]);
        } else {
            return Optional.empty();
        }
    }

    private Stream<Cell> allCells() {
        return Stream.of(cells)
                .flatMap(Stream::of);
    }

    Set<Cell> getAll() {
        return allCells()
                .collect(Collectors.toSet());
    }

    Set<Cell> getClosedCells() {
        return allCells()
                .filter(c -> ! c.isOpen())
                .collect(Collectors.toSet());
    }

    Set<Cell> getCellsWithBombs() {
        return allCells()
                .filter(c -> c.bomb)
                .collect(Collectors.toSet());
    }

    public Cell[][] getCells() {
        return cells;
    }
}
