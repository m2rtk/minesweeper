package eu.m2rt.minesweeper.logic;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class RandomGridGenerator extends SimpleGridGenerator {

    public RandomGridGenerator(int height, int width, int noOfBombs, Random random) {
        super(new RandomBombSupplier(height, width, noOfBombs, random));
    }

    public RandomGridGenerator(int height, int width, int noOfBombs) {
        super(new RandomBombSupplier(height, width, noOfBombs));
    }
}
