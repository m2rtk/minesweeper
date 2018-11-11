package eu.m2rt.minesweeper.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GridTests {

    private Grid grid;

    @Test
    void constructorSetsCorrectHeight() {
        grid = newGrid(10, 1);

        assertEquals(10, grid.getHeight());
    }

    @Test
    void constructorSetsCorrectWidth() {
        grid = newGrid(1, 10);

        assertEquals(10, grid.getWidth());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 2", "0, 2", "1, 2", "2, 2",
            "-1, 1",                 "2, 1",
            "-1, 0",                 "2, 0",
            "-1,-1", "0,-1", "1,-1", "2,-1"
    })
    void testGetOutsideBoundsIsEmpty2x2(int row, int col) {
        testGetOutsideBoundsAxB(2, 2, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1", "0, 1", "1, 1",
            "-1, 0",         "1, 0",
            "-1,-1", "0,-1", "1,-1"
    })
    void testGetOutsideBoundsIsEmpty1x1(int row, int col) {
        testGetOutsideBoundsAxB(1, 1, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0"
    })
    void testGetInsideBoundsIsPresent1x1(int row, int col) {
        testGetInsideBoundsAxB(1, 1, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0", "0, 1"
    })
    void testGetInsideBoundsIsPresent1x2(int row, int col) {
        testGetInsideBoundsAxB(1, 2, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 0"
    })
    void testGetInsideBoundsIsPresent2x1(int row, int col) {
        testGetInsideBoundsAxB(2, 1, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0", "0, 1",
            "1, 0", "1, 1"
    })
    void testGetInsideBoundsIsPresent2x2(int row, int col) {
        testGetInsideBoundsAxB(2, 2, row, col);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0", "0, 1", "0, 2",
            "1, 0", "1, 1", "1, 2",
            "2, 0", "2, 1", "2, 2"
    })
    void testGetInsideBoundsIsPresent3x3(int row, int col) {
        testGetInsideBoundsAxB(3, 3, row, col);
    }

    private void testGetInsideBoundsAxB(int rows, int cols, int row, int col) {
        grid = newGrid(rows, cols);

        assertTrue(grid.getCell(row, col).isPresent());
    }

    private void testGetOutsideBoundsAxB(int rows, int cols, int row, int col) {
        grid = newGrid(rows, cols);

        assertFalse(grid.getCell(row, col).isPresent());
    }

    private Grid newGrid(int height, int width) {
        return new Grid.Builder(height, width).build();
    }
}