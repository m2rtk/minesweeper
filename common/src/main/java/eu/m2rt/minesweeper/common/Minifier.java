package eu.m2rt.minesweeper.common;

import eu.m2rt.minesweeper.logic.Cell;
import eu.m2rt.minesweeper.logic.MinesweeperState;

public class Minifier {

    public static String toMinified(Object obj) {
        if (obj instanceof MinesweeperState) return minifyState(((MinesweeperState) obj));
        if (obj instanceof NewGameWrapper) return minifyNewGame(((NewGameWrapper) obj));
        if (obj instanceof String) return ((String) obj);
        throw new IllegalArgumentException("Unknown type object for minification " + obj.getClass().getSimpleName());
    }

    private static String minifyNewGame(NewGameWrapper game) {
        return game.getId() + "\n" + minifyState(game.getGame());
    }


    private static String minifyState(MinesweeperState state) {
        StringBuilder sb = new StringBuilder();
        sb.append(state.getState().toString().charAt(0));
        sb.append('\n');

        Cell[][] grid = state.getGrid().getCells();

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.getVisibleState());
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
