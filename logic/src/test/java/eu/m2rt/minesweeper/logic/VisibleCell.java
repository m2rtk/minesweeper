package eu.m2rt.minesweeper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class VisibleCell {
    int row, col;
    Point location;

    private VisibleCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.location = Point.of(row, col);
    }

    static VisibleCell of(int row, int col, char c) {
        if (Character.isDigit(c)) return new Open(Integer.parseInt(String.valueOf(c)), row, col);
        else if (c == 'F') return new Flagged(row, col);
        else if (c == 'O') return new Closed(row, col);
        else if (c == 'x') return new Bomb(row, col);
        throw new RuntimeException("Unknown visible cell state " + c);
    }

    abstract void assertMatchesActualCell(Cell cell);

    static class Open extends VisibleCell {
        int expected;
        private Open(int expected, int row, int col) { super(row, col); this.expected = expected; }

        @Override
        void assertMatchesActualCell(Cell cell) {
            assertTrue(
                    cell.isOpen(),
                    "Cell at (" + row + ", " + col + ") should be open. " + cell
            );
            assertEquals(
                    expected,
                    cell.getNearbyBombs(),
                    "Cell at (" + row + ", " + col + ") should have " + expected + " nr instead of " + cell.getNearbyBombs() + "."
            );
        }
    }

    static class Flagged extends VisibleCell {
        private Flagged(int row, int col) { super(row, col); }

        @Override
        void assertMatchesActualCell(Cell cell) {
            assertTrue(
                    cell.isFlagged(),
                    "Cell at (" + row + ", " + col + ") should be flagged. " + cell
            );
        }
    }

    static class Closed extends VisibleCell {
        private Closed(int row, int col) { super(row, col); }

        @Override
        void assertMatchesActualCell(Cell cell) {
            assertTrue(
                    cell.isClosed(),
                    "Cell at (" + row + ", " + col + ") should be closed. " + cell
            );
        }
    }

    static class Bomb extends VisibleCell {
        private Bomb(int row, int col) { super(row, col); }

        @Override
        void assertMatchesActualCell(Cell cell) {
            assertTrue(
                    cell.isBomb(),
                    "Cell at (" + row + ", " + col + ") should be markAsBomb. " + cell
            );
        }
    }
}
