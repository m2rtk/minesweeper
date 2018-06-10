package com.example.minesweeper_rest.logic;


@SuppressWarnings("WeakerAccess")
class Cell {
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

    Cell(boolean bomb) {
        this(bomb, -1);
    }

    /**
     * Opens cell.
     * @return true if cell contains bomb.
     */
    boolean open() {
        if ( ! open ) {
            open = true;
            return bomb;
        } else {
            return false;
        }
    }

    void toggleFlag() {
        flag = !flag;
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
