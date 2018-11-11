package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.interfaces.AbstractBombSupplier;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomBombSupplier extends AbstractBombSupplier {

    private final Random random;
    private final int noOfBombs;

    public RandomBombSupplier(int height, int width, int noOfBombs, Random random) {
        super(height, width);
        this.noOfBombs = noOfBombs;
        this.random = random;
    }

    public RandomBombSupplier(int height, int width, int noOfBombs) {
        this(height, width, noOfBombs, new Random());
    }

    @Override
    public Set<Point> get() {
        return IntStream
                .generate(new RandomExclusiveIntSupplier(width * height))
                .limit(noOfBombs)
                .boxed()
                .map(this::toPoint)
                .collect(Collectors.toSet());
    }

    private Point toPoint(int index) {
        return Point.of((index - index % width) / width, index % width);
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
}
