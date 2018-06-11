package eu.m2rt.minesweeper.logic;


@SuppressWarnings({"WeakerAccess", "unused"})
public class Cell {
    final boolean bomb;
    private final int nearbyBombs;

    private boolean open;
    private boolean flag;

    Cell(boolean bomb, int nearbyBombs) {
        this.bomb = bomb;
        this.nearbyBombs = nearbyBombs;
        open = false;
        flag = false;
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
}
