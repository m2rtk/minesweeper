package eu.m2rt.minesweeper.logic;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    @ParameterizedTest
    @MethodSource("copyTestProvider")
    void copyWorks(Cell cell) {
        Cell copy = Cell.copy(cell);

        assertNotSame(cell, copy);
        assertEquals(cell.isOpen(),  copy.isOpen());
        assertEquals(cell.hasBomb(), copy.hasBomb());
        assertEquals(cell.isFlagged(), copy.isFlagged());
        assertEquals(cell.getNearbyBombs(), cell.getNearbyBombs());

        cell.open();
        copy.open();

        assertEquals(cell.getNearbyBombs(), copy.getNearbyBombs());
        assertEquals(cell.isFlagged(), copy.isFlagged());
    }

    private static Stream<Cell> copyTestProvider() {
        List<Cell> cells = new ArrayList<>();

        for (boolean bomb : new boolean[]{ true, false }) {
            for (int bombs : IntStream.range(-1, 9).toArray()) {
                for (boolean open : new boolean[]{ true, false }) {
                    for (boolean flag : new boolean[]{ true, false }) {
                        cells.add(new Cell(bomb, bombs, open, flag));
                    }
                }
            }
        }

        return cells.stream();
    }
}
