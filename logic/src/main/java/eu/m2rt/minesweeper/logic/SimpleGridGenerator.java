package eu.m2rt.minesweeper.logic;

import eu.m2rt.minesweeper.logic.interfaces.BombSupplier;
import eu.m2rt.minesweeper.logic.interfaces.GridGenerator;

/**
 * Given a BombSupplier, counts the adjacent bombs and creates a corresponding grid.
 */
public class SimpleGridGenerator implements GridGenerator {

    private final BombSupplier supplier;

    public SimpleGridGenerator(BombSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public Grid generate() {
        return new Grid
                .Builder(supplier.getHeight(), supplier.getWidth())
                .addBombs(supplier.get())
                .build();
    }
}
