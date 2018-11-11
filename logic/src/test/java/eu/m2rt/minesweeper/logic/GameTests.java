package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        private final Iterator<String> lines;
        private String line;

        private int height, width;
        private Minesweeper ms;

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

            while (lines.hasNext()) {
                nextLine();

                if (isGameOver()) {
                    assertEquals(getExpectedGameEndState(), ms.getState().getState());
                    return;
                }

                readCommands().forEach(this::execute);
                readVisibleCellsFromGrid().forEach(this::assertVisibleCellMatchesActualCell);
            }
        }

        private List<Command> readCommands() {
            List<Command> commands = new ArrayList<>();
            while ( ! "-".equals(line) ) {
                commands.add(Command.read(line));
                line = lines.next();
            }
            return commands;
        }

        private void execute(Command c) {
            c.executeOn(ms);
        }

        private void assertVisibleCellMatchesActualCell(VisibleCell cell) {
            cell.assertMatchesActualCell(ms.getState().getGrid().getCells()[cell.row][cell.col]);
        }

        private void nextLine() {
            line = lines.next();
            if ("-".equals(line)) { nextLine(); }
        }

        private boolean isGameOver() {
            return Arrays.stream(MinesweeperState.State.values())
                    .map(Enum::toString)
                    .anyMatch(s -> s.equals(line));
        }

        private MinesweeperState.State getExpectedGameEndState() {
            return MinesweeperState.State.valueOf(line);
        }

        private void readDimensions() {
            String[] line = lines.next().split(",");
            height = Integer.parseInt(line[0]);
            width  = Integer.parseInt(line[1]);
        }

        private void constructGame() {
            Grid.Builder gb = new Grid.Builder(height, width);

            readVisibleCellsFromGrid()
                    .stream()
                    .filter(VisibleCell.Bomb.class::isInstance)
                    .forEach(bomb -> gb.addBomb(bomb.location));

            ms = new MinesweeperImpl(gb.build());
        }

        private List<VisibleCell> readVisibleCellsFromGrid() {
            List<VisibleCell> cells = new ArrayList<>();

            for (int row = 0; row < height; row++) {
                line = lines.next();
                for (int col = 0; col < width; col++) {
                    cells.add(VisibleCell.of(row, col, line.charAt(col)));
                }
            }

            return cells;
        }
    }
}
