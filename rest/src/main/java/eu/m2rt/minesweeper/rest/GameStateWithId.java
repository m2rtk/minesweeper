package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.GameState;

public class GameStateWithId extends GameState {
    private final long id;

    public GameStateWithId(GameState game, long id) {
        super(game.getGrid(), game.isGameOver(), game.isWin());
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
