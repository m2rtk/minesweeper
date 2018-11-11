package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;

abstract class Command {
    int row, col;

    private Command(int row, int col) {
        this.row = row;
        this.col = col;
    }

    static Command read(String line) {
        String[] p = line.split(",");
        char c  = p[0].charAt(0);
        int row = Integer.parseInt(p[1]);
        int col = Integer.parseInt(p[2]);

        switch (c) {
            case 'o':
                return new Open(row, col);
            case 'f':
                return new Flag(row, col);
            case 'c':
                return new Chord(row, col);
            default:
                throw new RuntimeException("Unknown command " + c);
        }
    }

    abstract void executeOn(Minesweeper ms);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    private static class Open extends Command {
        private Open(int row, int col) { super(row, col); }
        @Override void executeOn(Minesweeper ms) { ms.open(row, col); }
    }

    private static class Flag extends Command {
        private Flag(int row, int col) { super(row, col); }
        @Override void executeOn(Minesweeper ms) { ms.flag(row, col); }
    }

    private static class Chord extends Command {
        private Chord(int row, int col) { super(row, col); }
        @Override void executeOn(Minesweeper ms) { ms.chord(row, col); }
    }
}
