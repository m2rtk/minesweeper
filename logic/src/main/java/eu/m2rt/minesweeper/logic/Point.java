package eu.m2rt.minesweeper.logic;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Point {
    public final int row, col;

    private static final Set<Point> surroundingPointsOffsets = Set.of(
            of(-1, -1), of(-1, 0), of(-1, 1),
            of(0, -1), of(0, 0), of(0, 1),
            of(1, -1), of(1, 0), of(1, 1)
    );

    private Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Point of(int row, int col) {
        return new Point(row, col);
    }

    Point sum(Point p) {
        return new Point(p.row + row, p.col + col);
    }

    Set<Point> surroundingPoints() {
        return surroundingPointsOffsets.stream()
                .map(this::sum)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Point{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return row == point.row &&
                col == point.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
