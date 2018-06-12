package eu.m2rt.minesweeper.logic;

import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("WeakerAccess")
public class RandomGridGenerator extends AbstractGridGenerator {
    private final Random random;

    public RandomGridGenerator(int height, int width, int noOfBombs, Random random) {
        super(height, width, noOfBombs);
        this.random = random;
    }

    public RandomGridGenerator(int height, int width, int noOfBombs) {
        this(height, width, noOfBombs, new Random());
    }

    @Override
    protected void generateBombs() {
        IntStream
                .generate(new RandomExclusiveIntSupplier(width * height))
                .limit(bombs)
                .boxed()
                .map(Point::new)
                .forEach(this::setCoordinateToTrue);
    }

    private void setCoordinateToTrue(Point p) {
        bombLocations[p.row][p.col] = true;
    }

    private class RandomExclusiveIntSupplier implements IntSupplier {
        private final List<Integer> list;

        private RandomExclusiveIntSupplier(int max) {
            this.list = IntStream
                    .range(0, max)
                    .boxed()
                    .collect(Collectors.toList());
        }

        @Override
        public int getAsInt() {
            return list.remove(random.nextInt(list.size()));
        }
    }

    private class Point {
        private final int row;
        private final int col;

        private Point(int index) {
            this.col = index % width;
            this.row = (index - index % width) / width;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}
