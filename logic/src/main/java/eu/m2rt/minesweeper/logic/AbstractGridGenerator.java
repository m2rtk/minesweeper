package eu.m2rt.minesweeper.logic;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractGridGenerator implements GridGenerator {

    protected final int height;
    protected final int width;
    protected final int bombs;

    protected Cell[][] cells;
    protected boolean[][] bombLocations;

    public AbstractGridGenerator(int height, int width, int bombs) {
        this.height = height;
        this.width = width;
        this.bombs = bombs;
    }

    public Grid generate() {
        cells = new Cell[height][width];
        bombLocations = new boolean[height][width];

        generateBombs();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = new Cell(bombLocations[row][col], countAdjacentBombs(col, row));
            }
        }

        return new Grid(cells);
    }

    /**
     * Turns n amount of values in bombLocations to true indicating the presence of a bomb.
     */
    protected abstract void generateBombs();

    private int countAdjacentBombs(int col, int row) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (hasBomb(col + i, row + j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean hasBomb(int col, int row) {
        if ( col < width && col > -1 && row < height && row > -1 ) {
            return bombLocations[row][col];
        }

        return false;
    }
}
