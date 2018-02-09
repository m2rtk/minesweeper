package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.Cell;

public class FlagResult {
    private final Cell cell;

    public FlagResult(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
