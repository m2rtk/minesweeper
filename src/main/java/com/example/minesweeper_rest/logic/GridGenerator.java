package com.example.minesweeper_rest.logic;

import java.util.Set;

public abstract class GridGenerator {

    public Grid generate(int height, int width, int bombs) {
        Cell[][] cells = new Cell[height][width];
        Set<Integer> bombLocations = generateBombs(height * width, bombs);

        // Create cell array for grid
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell( bombLocations.contains(height * x + y) );
            }
        }

        return new Grid(cells);
    }

    abstract Set<Integer> generateBombs(int possibleBombLocations, int bombs);

}
