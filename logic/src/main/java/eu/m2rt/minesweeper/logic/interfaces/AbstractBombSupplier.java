package eu.m2rt.minesweeper.logic.interfaces;

import eu.m2rt.minesweeper.logic.Point;
import eu.m2rt.minesweeper.logic.interfaces.BombSupplier;

import java.util.Set;

public abstract class AbstractBombSupplier implements BombSupplier {

    protected final int height, width;

    public AbstractBombSupplier(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public abstract Set<Point> get();

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
