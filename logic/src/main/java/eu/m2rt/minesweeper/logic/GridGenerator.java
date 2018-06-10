package eu.m2rt.minesweeper.logic;

@SuppressWarnings("WeakerAccess")
public abstract class GridGenerator {

    protected final int height;
    protected final int width;
    protected final int bombs;
    protected final Cell[][] cells;

    private boolean[][] bombLocations;

    public GridGenerator(int height, int width, int bombs) {
        this.height = height;
        this.width = width;
        this.bombs = bombs;
        this.cells = new Cell[height][width];
    }

    public Grid generate() {
        bombLocations = generateBombs();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[row][col] = new Cell(bombLocations[row][col], countAdjacentBombs(col, row));
            }
        }

        return new Grid(cells);
    }

    abstract boolean[][] generateBombs();

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
