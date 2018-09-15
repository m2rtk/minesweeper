package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTests {

    private static final Path testsPath = Paths.get("src", "test", "resources", "gametests");

    @ParameterizedTest
    @MethodSource("provider")
    void test(Path testFile) {
        new TestCase(testFile).run();
    }

    private static Stream<Path> provider() throws IOException {
        return Files.list(testsPath);
    }

    private class TestCase {

        private static final int FLAGGED = 10;
        private static final int CLOSED = -1;

        private final Iterator<String> lines;

        private int height, width;
        private Minesweeper ms;

        private int[][] visibleState;

        private TestCase(Path testFile) {
            try {
                lines = Files.readAllLines(testFile).iterator();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        void run() {
            readDimensions();
            constructGame();

            String line;

            while (true) {
                line = lines.next();
                if ( ! "-".equals(line)) {
                    assertEquals(MinesweeperState.State.valueOf(line), ms.getState().getState());
                    return;
                }

                readAndExecuteCommand(lines.next());
                readState();
                verifyState();
            }
        }

        private void readDimensions() {
            String[] line = lines.next().split(",");
            height = Integer.parseInt(line[0]);
            width  = Integer.parseInt(line[1]);
        }

        private void constructGame() {
            Grid.Builder gb = new Grid.Builder(height, width);
            String line;

            for (int row = 0; row < height; row++) {
                line = lines.next();
                for (int col = 0; col < width; col++) {
                    if (line.charAt(col) == 'x') {
                        gb.addBomb(new Point(row, col));
                    }
                }
            }

            ms = new MinesweeperImpl(gb.build());
        }

        private void readAndExecuteCommand(String line) {
            String[] p = line.split(",");

            char c  = p[0].charAt(0);
            int row = Integer.parseInt(p[1]);
            int col = Integer.parseInt(p[2]);

            switch (c) {
                case 'o':
                    System.out.println("Opening cell at " + row + "," + col);
                    ms.open(row, col);
                    break;
                case 'f':
                    System.out.println("Flagging cell at " + row + "," + col);
                    ms.flag(row, col);
                    break;
            }

            String l = lines.next();

            if ( ! "-".equals(l)) {
                readAndExecuteCommand(l);
            }
        }

        private void verifyState() {
            Cell[][] cells = ms.getState().getGrid().getCells();
            Cell cell;
            int visible;

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    cell = cells[row][col];
                    visible = visibleState[row][col];

                    switch (visible) {
                        case CLOSED:
                            assertTrue(
                                    cell.isClosed(),
                                    "Cell at (" + row + ", " + col + ") should be closed. " + cell
                            );
                            break;
                        case FLAGGED:
                            assertTrue(
                                    cell.isFlagged(),
                                    "Cell at (" + row + ", " + col + ") should be flagged. " + cell
                            );
                            break;
                        default:
                            assertTrue(
                                    cell.isOpen(),
                                    "Cell at (" + row + ", " + col + ") should be open. " + cell
                            );
                            assertEquals(
                                    visible,
                                    cell.getNearbyBombs(),
                                    "Cell at (" + row + ", " + col + ") should have " + visible + " nr instead of " + cell.getNearbyBombs() + "."
                            );
                    }
                }
            }
        }

        private void readState() {
            visibleState = new int[height][width];
            String line;

            for (int row = 0; row < height; row++) {
                line = lines.next();
                for (int col = 0; col < width; col++) {
                    char c = line.charAt(col);

                    if (Character.isDigit(c)) {
                        visibleState[row][col] = Integer.parseInt(String.valueOf(c));
                    } else if (c == 'F') {
                        visibleState[row][col] = FLAGGED;
                    } else if (c == 'O') {
                        visibleState[row][col] = CLOSED;
                    }
                }
            }
        }
    }
}
