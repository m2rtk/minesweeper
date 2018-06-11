package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.MinesweeperState;


@SuppressWarnings("WeakerAccess")
public class NewGameWrapper {
    private final MinesweeperState game;
    private final Long id;

    public NewGameWrapper(MinesweeperState game, Long id) {
        this.game = game;
        this.id = id;
    }

    public MinesweeperState getGame() {
        return game;
    }

    public Long getId() {
        return id;
    }
}
