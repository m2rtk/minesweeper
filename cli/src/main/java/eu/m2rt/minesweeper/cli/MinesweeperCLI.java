package eu.m2rt.minesweeper.cli;

import eu.m2rt.minesweeper.logic.*;
import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.Color.*;

// TODO refactor
// TODO prettier IO
public class MinesweeperCLI {

    private static Minesweeper ms;

    public static void main(String[] args) {
        ms = new MinesweeperImpl(new RandomGridGenerator(6, 10, 10).generate());

        while (ms.getState().getState() == MinesweeperState.State.PLAY) {
            printState(ms.getState());
            parseInput();
        }

        printState(ms.getState());
    }

    private static void printState(MinesweeperState state) {
        Grid grid = state.getGrid();

        Cell cell;
        C c;
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                cell = grid.get(row, col).orElseThrow(IllegalStateException::new);
                c = C.from(cell);

                System.out.print(Ansi.ansi().bg(c.bg).fg(c.fg).a(c.c).reset());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void parseInput() {
        Scanner scanner = new Scanner(System.in);

        String[] line = scanner.nextLine().trim().split("[ ]+");

        char c = line[0].charAt(0);
        int row = Integer.parseInt(line[1]);
        int col = Integer.parseInt(line[2]);

        switch (c) {
            case 'o':
                ms.open(row, col);
                break;
            case 'f':
                ms.flag(row, col);
                break;
        }
    }

    private static class C {

        private static final C BOMB = new C('x', DEFAULT, RED);
        private static final C FLAG = new C('F', DEFAULT, YELLOW);
        private static final C CLOSED = new C('O', DEFAULT, WHITE);

        private char c;
        private final Ansi.Color bg;
        private final Ansi.Color fg;

        private static C from(Cell cell) {
            if (cell.isBomb()) return BOMB;
            if (cell.isOpen()) return new C(String.valueOf(cell.getNearbyBombs()).charAt(0), DEFAULT, GREEN);
            if (cell.isFlagged()) return FLAG;

            return CLOSED;
        }

        private C(char c, Ansi.Color bg, Ansi.Color fg) {
            this.c = c;
            this.bg = bg;
            this.fg = fg;
        }
    }

}
