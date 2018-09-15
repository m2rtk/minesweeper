package eu.m2rt.minesweeper.logic.interfaces;

import eu.m2rt.minesweeper.logic.MinesweeperState;
import eu.m2rt.minesweeper.logic.exceptions.GameIndexOutOfBoundsException;
import eu.m2rt.minesweeper.logic.exceptions.GameOverException;

public interface Minesweeper {

    /**
     * Opens cell if cell is not already open. Otherwise does nothing.
     * @throws GameOverException if open is called after game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     * @return this object.
     */
    Minesweeper open(int row, int col);

    /**
     * Toggles flag if cell is not open. Otherwise does nothing.
     * @throws GameOverException if flag is called after the game has ended.
     * @throws GameIndexOutOfBoundsException if row or col is out of bounds.
     * @return this object.
     */
    Minesweeper flag(int row, int col);

    /**
     * Gets the current game state.
     * @return MinesweeperState object corresponding to the current game state.
     */
    MinesweeperState getState();
}
