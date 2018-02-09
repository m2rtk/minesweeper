package com.example.minesweeper_rest.logic;

import java.util.*;

public class RandomGridGenerator extends GridGenerator {
    private Random random = new Random();

    @Override
    Set<Integer> generateBombs(int possibleBombLocations, int bombs) {
        List<Integer> possibleLocations = new ArrayList<>();
        for (int i = 0; i < possibleBombLocations; i++) {
            possibleLocations.add(i);
        }

        Set<Integer> bombLocations = new HashSet<>();

        for (int i = 0; i < bombs; i++) {
            bombLocations.add(possibleLocations.remove(random.nextInt(possibleLocations.size())));
        }

        return bombLocations;
    }

    public void setSeed(long seed) {
        random = new Random(seed);
    }
}
