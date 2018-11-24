package eu.m2rt.minesweeper.logic;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Cell {
    private final boolean bomb;
    private final int nearbyBombs;
    final Point location;

    private boolean open;
    private boolean flag;

    Cell(Point location, boolean bomb, int nearbyBombs, boolean open, boolean flag) {
        this.location = location;
        this.bomb = bomb;
        this.nearbyBombs = nearbyBombs;
        this.open = open;
        this.flag = flag;
    }

    Cell(Point location, boolean bomb, int nearbyBombs) {
        this(location, bomb, nearbyBombs, false, false);
    }

    Cell(Cell that) {
        this.location = that.location;
        this.bomb = that.bomb;
        this.nearbyBombs = that.nearbyBombs;
        this.open = that.open;
        this.flag = that.flag;
    }

    // for testing
    Cell(boolean bomb, int nearbyBombs) {
        this(Point.of(0, 0), bomb, nearbyBombs);
    }

    // for testing
    Cell(boolean bomb, int nearbyBombs, boolean open, boolean flag) {
        this(Point.of(0, 0), bomb, nearbyBombs, open, flag);
    }

    Cell open() {
        open = true;
        flag = false;
        return this;
    }

    Cell toggleFlag() {
        flag = !flag;
        return this;
    }

    boolean isClosed() {
        return ! open;
    }

    boolean hasBomb() {
        return bomb;
    }

    boolean hasNoBomb() {
        return ! bomb;
    }

    static class Builder {
        private final Point location;
        private int nearbyBombs = 0;
        private boolean bomb = false;

        Builder(Point location) {
            this.location = location;
        }

        void incrementNearbyBombs() {
            nearbyBombs++;
        }

        void markAsBomb() {
            bomb = true;
        }

        Cell build() {
            return new Cell(location, bomb, nearbyBombs);
        }
    }

    public boolean isBomb() {
        return open && bomb;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isFlagged() {
        return !open && flag;
    }

    public int getNearbyBombs() {
        return open ? nearbyBombs : -1;
    }

    public char getVisibleState() {
        if (open) {
            if (bomb) {
                return 'X';
            }

            if (nearbyBombs == 0) {
                return '-';
            }

            return String.valueOf(nearbyBombs).charAt(0);
        }

        if (flag) {
            return 'F';
        }

        return '+';
    }

    @Override
    public String toString() {
        return "Cell{" +
                "bomb=" + bomb +
                ", nearbyBombs=" + nearbyBombs +
                ", location=" + location +
                ", open=" + open +
                ", flag=" + flag +
                '}';
    }
}
