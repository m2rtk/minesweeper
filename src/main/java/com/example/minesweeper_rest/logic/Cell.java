package com.example.minesweeper_rest.logic;

public class Cell {
    private boolean open;
    private boolean bomb;
    private boolean flag;
    private int nearbyBombs;

    Cell(boolean bomb) {
        this.bomb = bomb;
        this.open = false;
        this.flag = false;
        this.nearbyBombs = 0;
    }

    /**
     * Opens cell.
     * @return true if cell contains bomb.
     */
    boolean open() {
        if ( ! this.isOpen() ) {
            this.open = true;
            return this.hasBomb();
        } else {
            return false;
        }
    }

    public void toggleFlag() {
        this.flag = !this.flag;
    }

    public boolean isOpen() {
        return open;
    }

    // Not exposed to player
    boolean hasBomb() {
        return bomb;
    }

    // Exposed to player
    public boolean isBomb() {
        return open && bomb;
    }

    public boolean isFlagged() {
        return !open && flag;
    }

    public int getNearbyBombs() {
        return open ? nearbyBombs : -1;
    }

    void incrementNearbyBombs() {
        this.nearbyBombs++;
    }
}
