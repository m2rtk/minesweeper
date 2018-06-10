package com.example.minesweeper_rest.logic;

// TODO 100% immutable
public class GameState {
    private final Long id;
    private final Grid grid;
    private final boolean gameOver;
    private final boolean isWin;

    GameState(Long id, Grid grid, boolean gameOver, boolean isWin) {
        this.id = id;
        this.grid = grid;
        this.gameOver = gameOver;
        this.isWin = isWin;
    }

    public Long getId() {
        return id;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return isWin;
    }
}
