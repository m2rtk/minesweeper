package com.example.minesweeper_rest.logic;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.*;

public class GridTests {

    @Test
    public void getAllIsOk() {
        Cell[][] cells = new Cell[2][2];
        cells[0][0] = new Cell(true);
        cells[0][1] = new Cell(true);
        cells[1][0] = new Cell(true);
        cells[1][1] = new Cell(true);
        Grid grid = new Grid(cells);

        Set<Cell> allCells = grid.getAll();
        assertEquals(4, allCells.size());
        assertTrue(allCells.contains(cells[0][0]));
        assertTrue(allCells.contains(cells[0][1]));
        assertTrue(allCells.contains(cells[1][0]));
        assertTrue(allCells.contains(cells[1][1]));
    }

    @Test
    public void getClosedIsOk() {
        Cell[][] cells = new Cell[2][2];
        cells[0][0] = new Cell(true);
        cells[0][1] = new Cell(true);
        cells[1][0] = new Cell(true);
        cells[1][1] = new Cell(true);
        Grid grid = new Grid(cells);

        Set<Cell> closed = grid.getClosedCells();
        assertEquals(4, closed.size());

        cells[0][0].open();
        closed = grid.getClosedCells();
        assertEquals(3, closed.size());
        assertFalse(closed.contains(cells[0][0]));

        cells[0][1].open();
        closed = grid.getClosedCells();
        assertEquals(2, closed.size());
        assertFalse(closed.contains(cells[0][1]));

        cells[1][0].open();
        closed = grid.getClosedCells();
        assertEquals(1, closed.size());
        assertFalse(closed.contains(cells[1][0]));

        cells[1][1].open();
        closed = grid.getClosedCells();
        assertEquals(0, closed.size());
        assertFalse(closed.contains(cells[1][1]));
    }

    @Test
    public void getIsOk() {
        Cell[][] cells = new Cell[2][2];
        cells[0][0] = new Cell(true);
        cells[0][1] = new Cell(true);
        cells[1][0] = new Cell(true);
        cells[1][1] = new Cell(true);
        Grid grid = new Grid(cells);

        assertEquals(grid.get(0, 0).get(), cells[0][0]);
        assertEquals(grid.get(0, 1).get(), cells[1][0]);
        assertEquals(grid.get(1, 0).get(), cells[0][1]);
        assertEquals(grid.get(1, 1).get(), cells[1][1]);

        assertEquals(Optional.empty(), grid.get(-1, -1));
        assertEquals(Optional.empty(), grid.get(2, 1));
        assertEquals(Optional.empty(), grid.get(2, 2));
        assertEquals(Optional.empty(), grid.get(-1, 1));
    }
}
