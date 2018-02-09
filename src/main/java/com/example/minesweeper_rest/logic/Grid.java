package com.example.minesweeper_rest.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Grid {
    private Cell[][] cells;
    private final int height, width;
    final int bombs;

    Grid(Cell[][] cells) {
        this.cells = cells;

        this.height = this.cells.length;
        this.width  = this.height == 0 ? 0 : this.cells[0].length;
        this.bombs  = (int) this.getAll().stream().filter(Cell::hasBomb).count();

        this.initializeNumbers();
    }

    /**
     * @return Cell object in grid at coordinates x, y. Returns null if out of bounds.
     */
    Cell get(int x, int y) {
        if ( x < this.width && x > -1 && y < this.height && y > -1 ) {
            return this.cells[y][x];
        } else {
            return null;
        }
    }

    @JsonIgnore
    Set<Cell> getAll() {
        Set<Cell> cells = new HashSet<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                cells.add(this.cells[y][x]);
            }
        }
        return cells;
    }

    @JsonIgnore
    Set<Cell> getClosedCells() {
        return this.getAll().stream().filter(c -> ! c.isOpen()).collect(Collectors.toSet());
    }

    private List<Cell> getAdjacent(int x, int y) {
        List<Cell> cells = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                Cell cell = this.get(x + i, y + j);
                if (cell != null) cells.add(cell);
            }
        }
        return cells;
    }

    private void initializeNumbers() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.get(x, y).hasBomb()) {
                    this.getAdjacent(x, y)
                        .stream()
                        .filter(c -> ! c.hasBomb())
                        .forEach(Cell::incrementNearbyBombs);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}
