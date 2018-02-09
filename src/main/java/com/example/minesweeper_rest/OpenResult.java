package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.Cell;
import com.example.minesweeper_rest.logic.Grid;

public class OpenResult {
    private final Grid grid;
    private final boolean gameOver;
    private final String message;

    public OpenResult(Grid grid, boolean gameOver, String message) {
        this.grid = grid;
        this.gameOver = gameOver;
        this.message = message;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getMessage() {
        return message;
    }
}
