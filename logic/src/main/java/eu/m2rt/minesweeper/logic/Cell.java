package eu.m2rt.minesweeper.logic;


import java.util.Objects;

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

    Cell open() {
        open = true;
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

    static Cell copy(Cell cell) {
        return new Cell(cell.bomb, cell.nearbyBombs, cell.open, cell.flag);
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
