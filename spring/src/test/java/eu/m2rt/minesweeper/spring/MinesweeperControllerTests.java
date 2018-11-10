package eu.m2rt.minesweeper.spring;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import eu.m2rt.minesweeper.logic.MinesweeperState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperControllerTests {

    private ConcurrentMap<String, Minesweeper> games;
    private MinesweeperController controller;

    @BeforeEach
    void setUp() {
        games = new ConcurrentHashMap<>();
        controller = new MinesweeperController(games);
    }

    @Test
    void newGameAddsNewGameToMap() {
        String id = controller.newgame(1, 1, 1).getId();

        assertNotNull(id);
        assertNotNull(games.get(id));
    }

    @Test
    void flagCallsUnderlyingGame() {
        Minesweeper game = createAndGetGame();

        assertFalse(game.getState().getGrid().getCells()[0][0].isFlagged());

        MinesweeperState state = controller.flag(games.keySet().iterator().next(), 0, 0);

        assertTrue(game.getState().getGrid().getCells()[0][0].isFlagged());

        assertSame(game.getState(), state);
    }

    @Test
    void openCallsUnderlyingGame() {
        Minesweeper game = createAndGetGame();

        assertFalse(game.getState().getGrid().getCells()[0][0].isOpen());

        MinesweeperState state = controller.open(games.keySet().iterator().next(), 0, 0);

        assertTrue(game.getState().getGrid().getCells()[0][0].isOpen());

        assertSame(game.getState(), state);
    }

    @Test
    void gameReturnsCorrectGame() {
        Minesweeper game = createAndGetGame();

        assertSame(game.getState(), controller.game(games.keySet().iterator().next()));
    }

    private Minesweeper createAndGetGame() {
        MinesweeperState state = controller.newgame(10, 10, 10).getGame();
        Minesweeper game = games.get(games.keySet().iterator().next());
        assertSame(state.getGrid(), game.getState().getGrid()); // Can't compare states, because Controller uses wrapper of MinesweeperState
        assertSame(state.getState(), game.getState().getState());
        return game;
    }
}