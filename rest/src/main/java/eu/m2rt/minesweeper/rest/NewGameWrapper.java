package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.MinesweeperState;


@SuppressWarnings("WeakerAccess")
public class NewGameWrapper {
    private final MinesweeperState game;
    private final String id;

    public NewGameWrapper(MinesweeperState game, String id) {
        this.game = game;
        this.id = id;
    }

    public MinesweeperState getGame() {
        return game;
    }

    public String getId() {
        return id;
    }
}
