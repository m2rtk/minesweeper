package eu.m2rt.minesweeper.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTests {

    private Grid grid;

    @Test
    void constructorSetsCorrectHeight() {
        grid = newGrid(10, 1, 0);

        assertEquals(10, grid.getHeight());
    }

    @Test
    void constructorSetsCorrectWidth() {
        grid = newGrid(1, 10, 0);

        assertEquals(10, grid.getWidth());
    }

    @Test
    void constructorSetsCorrectAmountOfBombs() {
        grid = newGrid(5, 5, 10);

        assertEquals(10, grid.getBombs());
    }

    @Test
    void getWithinBoundsReturnsCell() {
        grid = newGrid(1, 10, 5);

        assertTrue(grid.get(0, 0).isPresent());
    }

    private Grid newGrid(int a, int b, int bombs) {
        return new Grid(populate(new Cell[a][b], bombs));
    }

    private Cell[][] populate(Cell[][] cells, int bombs) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(bombs-- > 0, 0);
            }
        }
        return cells;
    }
}