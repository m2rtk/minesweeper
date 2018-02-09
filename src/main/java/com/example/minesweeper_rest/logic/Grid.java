package com.example.minesweeper_rest.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Grid {
    private Cell[][] cells;
    public final int height, width, bombs;

    public Grid(Cell[][] cells) {
        this.cells = cells;

        this.height = this.cells.length;
        this.width  = this.height == 0 ? 0 : this.cells[0].length;
        this.bombs  = (int) this.getAll().stream().filter(Cell::isBomb).count();

        this.initializeNumbers();
    }

    /**
     * @return Cell object in grid at coordinates x, y. Returns null if out of bounds.
     */
    public Cell get(int x, int y) {
        if ( x < this.width && x > -1 && y < this.height && y > -1 ) {
            return this.cells[y][x];
        } else {
            return null;
        }
    }

    public List<Cell> getAdjacent(int x, int y) {
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

    @JsonIgnore
    public List<Cell> getAll() {
        List<Cell> cells = new ArrayList<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                cells.add(this.cells[y][x]);
            }
        }
        return cells;
    }

    @JsonIgnore
    public List<Cell> getClosedCells() {
        return this.getAll().stream().filter(c -> !c.isOpen()).collect(Collectors.toList());
    }

    private void initializeNumbers() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.get(x, y).isBomb()) {
                    this.getAdjacent(x, y)
                        .stream()
                        .filter(c -> ! c.isBomb())
                        .forEach(Cell::incrementNearbyBombs);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                sb.append(this.cells[y][x]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
