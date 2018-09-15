package eu.m2rt.minesweeper.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class RandomBombSupplierTests {

    private Supplier<Set<Point>> supplier;

    @ParameterizedTest
    @CsvSource({
            "1, 1, 0", "1, 1, 1",
            "2, 2, 0", "2, 2, 2", "2, 2, 4",
            "3, 3, 0", "3, 3, 3", "3, 3, 6", "3, 3, 9",
            "4, 8, 0", "1, 9, 9", "6, 3, 12","55, 55, 10"
    })
    void generatesSpecifiedAmountOfBombs(int height, int width, int noOfBombs) {
        supplier = new RandomBombSupplier(height, width, noOfBombs);
        assertSame(noOfBombs, supplier.get().size());
    }

    @Test
    void generatesUniqueBombs() {
        supplier = new RandomBombSupplier(2, 2, 0);

        assertNotSame(supplier.get(), supplier.get());
    }
}