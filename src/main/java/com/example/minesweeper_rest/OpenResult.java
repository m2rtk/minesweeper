package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.Cell;

public class OpenResult {
    private final Cell cell;
    private final boolean gameOver;
    private final String message;

    public OpenResult(Cell cell, boolean gameOver, String message) {
        this.cell = cell;
        this.gameOver = gameOver;
        this.message = message;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getMessage() {
        return message;
    }
}
