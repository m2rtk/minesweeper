package eu.m2rt.minesweeper.logic.interfaces;

import eu.m2rt.minesweeper.logic.Point;

import java.util.Set;
import java.util.function.Supplier;

public interface BombSupplier extends Supplier<Set<Point>> {
    Set<Point> get();
    int getHeight();
    int getWidth();
}
