package eu.m2rt.minesweeper.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinesweeperImplTests {

    private Grid grid;
    private MinesweeperState state;
    private Minesweeper game;

    @BeforeEach
    void setUp() {
        grid = mock(Grid.class);
        state = new MinesweeperState(grid);
        game = new MinesweeperImpl(state);
    }

    @Test
    void openThrowsExceptionIfGridGetReturnsEmpty() {
        setGetCell(0, 0, null);
        assertThrows(GameIndexOutOfBoundsException.class, () -> game.open(0, 0));
    }

    @Test
    void flagThrowsExceptionIfGridGetReturnsEmpty() {
        setGetCell(0, 0, null);
        assertThrows(GameIndexOutOfBoundsException.class, () -> game.flag(0, 0));
    }

    @Test
    void openThrowsExceptionIfGameWon() {
        setGameState(MinesweeperState.State.WIN);
        assertThrows(GameOverException.class, () -> game.open(0, 0));
    }

    @Test
    void openThrowsExceptionIfGameLost() {
        setGameState(MinesweeperState.State.LOSS);
        assertThrows(GameOverException.class, () -> game.open(0, 0));
    }

    @Test
    void flagThrowsExceptionIfGameWon() {
        setGameState(MinesweeperState.State.WIN);
        assertThrows(GameOverException.class, () -> game.flag(0, 0));
    }

    @Test
    void flagThrowsExceptionIfGameLost() {
        setGameState(MinesweeperState.State.LOSS);
        assertThrows(GameOverException.class, () -> game.flag(0, 0));
    }

    private void setGameState(MinesweeperState.State state) {
        game = new MinesweeperImpl(new MinesweeperState(grid, state));
    }

    private void setGetCell(int row, int col, Cell cell) {
        when(grid.get(eq(row), eq(col))).thenReturn(Optional.ofNullable(cell));
    }
}
