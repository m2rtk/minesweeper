package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import eu.m2rt.minesweeper.logic.MinesweeperState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperControllerTests {

    private ConcurrentMap<Long, Minesweeper> games;
    private AtomicLong counter;
    private MinesweeperController controller;

    @BeforeEach
    void setUp() {
        games = new ConcurrentHashMap<>();
        counter = new AtomicLong();
        controller = new MinesweeperController(games, counter);
    }

    @Test
    void newGameIncrementsCounter() {
        long old;
        do {
            old = counter.get();
            controller.newgame(1, 1, 1);
            assertEquals(old + 1, counter.get());
        } while (old < 10);
    }

    @Test
    void newGameAddsNewGameToMap() {
        long id = controller.newgame(1, 1, 1).getId();

        assertEquals(0L, id);
        assertNotEquals(null, games.get(id));
    }

    @Test
    void flagCallsUnderlyingGame() {
        Minesweeper game = createAndGetGame();

        assertFalse(game.getState().getGrid().getCells()[0][0].isFlagged());

        MinesweeperState state = controller.flag(0, 0, 0);

        assertTrue(game.getState().getGrid().getCells()[0][0].isFlagged());

        assertSame(game.getState(), state);
    }

    @Test
    void openCallsUnderlyingGame() {
        Minesweeper game = createAndGetGame();

        assertFalse(game.getState().getGrid().getCells()[0][0].isOpen());

        MinesweeperState state = controller.open(0, 0, 0);

        assertTrue(game.getState().getGrid().getCells()[0][0].isOpen());

        assertSame(game.getState(), state);
    }

    @Test
    void gameReturnsCorrectGame() {
        Minesweeper game = createAndGetGame();

        assertSame(game.getState(), controller.game(0L));
    }

    private Minesweeper createAndGetGame() {
        MinesweeperState state = controller.newgame(10, 10, 10).getGame();
        Minesweeper game = games.get(0L);
        assertSame(state.getGrid(), game.getState().getGrid()); // Can't compare states, because Controller uses wrapper of MinesweeperState
        assertSame(state.getState(), game.getState().getState());
        return game;
    }
}