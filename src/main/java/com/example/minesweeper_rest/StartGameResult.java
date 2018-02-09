package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.Grid;

public class StartGameResult {
    private final long newGameId;
    private final Grid grid;

    public StartGameResult(long newGameId, Grid grid) {
        this.newGameId = newGameId;
        this.grid = grid;
    }

    public long getNewGameId() {
        return newGameId;
    }

    public Grid getGrid() {
        return grid;
    }
}
