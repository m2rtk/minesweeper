package eu.m2rt.minesweeper.logic;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Cell {
    private final boolean bomb;
    private final int nearbyBombs;

    private boolean open;
    private boolean flag;

    Cell(boolean bomb, int nearbyBombs, boolean open, boolean flag) {
        this.bomb = bomb;
        this.nearbyBombs = nearbyBombs;
        this.open = open;
        this.flag = flag;
    }

    Cell(boolean bomb, int nearbyBombs) {
        this(bomb, nearbyBombs, false, false);
    }

    Cell(Cell that) {
        this.bomb = that.bomb;
        this.nearbyBombs = that.nearbyBombs;
        this.open = that.open;
        this.flag = that.flag;
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

    public static class Builder {
        private int nearbyBombs = 0;
        private boolean bomb = false;

        public void incrementNearbyBombs() {
            nearbyBombs++;
        }

        public void bomb() {
            bomb = true;
        }

        public Cell build() {
            return new Cell(bomb, nearbyBombs);
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "bomb=" + bomb +
                ", nearbyBombs=" + nearbyBombs +
                ", open=" + open +
                ", flag=" + flag +
                '}';
    }
}
