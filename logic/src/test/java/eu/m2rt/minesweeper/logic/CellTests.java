package eu.m2rt.minesweeper.logic;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTests {

    @Test
    void isBombIsFalseBeforeOpenIfBomb() {
        assertFalse(new Cell(true, 0).isBomb());
    }

    @Test
    void isBombIsFalseBeforeOpenIfNoBomb() {
        assertFalse(new Cell(false, 0).isBomb());
    }

    @Test
    void isBombIsTrueAfterOpenIfBomb() {
        assertTrue(new Cell(true, 0).open().isBomb());
    }

    @Test
    void isBombIsFalseAfterOpenIfNoBomb() {
        assertFalse(new Cell(false, 0).open().isBomb());
    }

    @Test
    void isOpenIsFalseBeforeOpenBomb() {
        assertFalse(new Cell(true, 0).isOpen());
    }

    @Test
    void isOpenIsFalseBeforeOpenNoBomb() {
        assertFalse(new Cell(false, 0).isOpen());
    }

    @Test
    void isOpenIsTrueAfterOpenIfBomb() {
        assertTrue(new Cell(true, 0).open().isOpen());
    }

    @Test
    void isOpenIsTrueAfterOpenIfNoBomb() {
        assertTrue(new Cell(false, 0).open().isOpen());
    }

    @Test
    void isFlaggedIsFalseBeforeToggleIfBomb() {
        assertFalse(new Cell(true, 0).isFlagged());
    }

    @Test
    void isFlaggedIsFalseBeforeToggleIfNoBomb() {
        assertFalse(new Cell(false, 0).isFlagged());
    }

    @Test
    void isFlaggedIsTrueAfterToggleIfBomb() {
        assertTrue(new Cell(true, 0).toggleFlag().isFlagged());
    }

    @Test
    void isFlaggedIsTrueAfterToggleIfNoBomb() {
        assertTrue(new Cell(false, 0).toggleFlag().isFlagged());
    }

    @Test
    void isFlaggedIsFalseAfter2TogglesIfBomb() {
        assertFalse(new Cell(true, 0).toggleFlag().toggleFlag().isFlagged());
    }

    @Test
    void isFlaggedIsFalseAfter2TogglesIfNoBomb() {
        assertFalse(new Cell(false, 0).toggleFlag().toggleFlag().isFlagged());
    }

    @Test
    void getNearbyBombsIsUnknownIfNotOpenIfBomb() {
        assertEquals(-1, new Cell(true, 0).getNearbyBombs());
    }

    @Test
    void getNearbyBombsIsUnknownIfNotOpenIfNoBomb() {
        assertEquals(-1, new Cell(false, 0).getNearbyBombs());
    }

    @Test
    void getNearbyBombsIsKnownIfOpenIfBomb() {
        assertEquals(0, new Cell(true, 0).open().getNearbyBombs());
    }

    @Test
    void getNearbyBombsIsKnownIfOpenIfNoBomb() {
        assertEquals(0, new Cell(false, 0).open().getNearbyBombs());
    }
}
