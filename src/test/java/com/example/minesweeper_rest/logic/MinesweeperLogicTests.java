package com.example.minesweeper_rest.logic;

import org.junit.Test;

import static org.junit.Assert.fail;

public class MinesweeperLogicTests {

    @Test(expected = GameOverException.class)
    public void openThrowsException() throws GameOverException {
        Cell[][] cells = new Cell[1][1];
        cells[0][0] = new Cell(true);
        MinesweeperLogic ms = new MinesweeperLogic(new Grid(cells), -1);
        try {
            ms.open(0, 0);
        } catch (GameOverException e) {
            fail("Exception shouldn't be thrown yet.");
        }
        ms.open(0, 0);
    }

    // todo more tests
}
