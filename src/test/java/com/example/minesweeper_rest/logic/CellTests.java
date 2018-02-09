package com.example.minesweeper_rest.logic;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class CellTests {

    @Test
    public void openHasBombIsOk() {
        Cell cell = new Cell(true);
        assertTrue(cell.open());
        assertTrue(cell.isOpen());
        assertFalse(cell.open());
        assertTrue(cell.isOpen());
    }

    @Test
    public void openNoBombIsOk() {
        Cell cell = new Cell(false);
        assertFalse(cell.open());
        assertTrue(cell.isOpen());
        assertFalse(cell.open());
        assertTrue(cell.isOpen());
    }

    @Test
    public void toggleFlagIsOk() {
        Cell cell = new Cell(false);
        for (int i = 0; i < 3; i++) {
            cell.toggleFlag();
            assertTrue(cell.isFlagged());
            cell.toggleFlag();
            assertFalse(cell.isFlagged());
        }
    }

    @Test
    public void isBombIsOk() {
        Cell cell = new Cell(false);
        assertFalse(cell.isBomb());
        assertFalse(cell.open());
        assertFalse(cell.isBomb());

        cell = new Cell(true);
        assertFalse(cell.isBomb());
        assertTrue(cell.open());
        assertTrue(cell.isBomb());
    }

    @Test
    public void getNearbyBombsIsOk() {
        Cell cell = new Cell(false);
        assertEquals(-1, cell.getNearbyBombs());
        cell.incrementNearbyBombs();
        assertEquals(-1, cell.getNearbyBombs());
        assertFalse(cell.open());
        assertEquals(1, cell.getNearbyBombs());
    }

    @Test
    public void isFlaggedIsOk() {
        Cell cell = new Cell(false);
        assertFalse(cell.isFlagged());
        cell.toggleFlag();
        assertTrue(cell.isFlagged());
        assertFalse(cell.open());
        assertFalse(cell.isFlagged());
    }
}
