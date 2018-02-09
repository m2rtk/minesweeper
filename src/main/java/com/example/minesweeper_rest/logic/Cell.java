package com.example.minesweeper_rest.logic;

public class Cell {
    private boolean open;
    private boolean bomb;
    private boolean flag;
    private int nearbyBombs;

    public Cell(boolean bomb) {
        this.bomb = bomb;
        this.open = false;
        this.flag = false;
        this.nearbyBombs = 0;
    }

    /**
     * Opens cell.
     * @return true if cell contains bomb.
     */
    public boolean open() {
        if ( ! this.isOpen() ) {
            this.open = true;
            return this.isBomb();
        } else {
            return false;
        }
    }

    /**
     * Toggles flag.
     * @return flag value after toggle.
     */
    public boolean toggleFlag() {
        this.flag = !this.flag;
        return this.flag;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isFlagged() {
        return flag;
    }

    public int getNearbyBombs() {
        return nearbyBombs;
    }

    public void incrementNearbyBombs() {
        this.nearbyBombs++;
    }
}
