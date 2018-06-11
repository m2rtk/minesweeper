package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
@RestController
public class MinesweeperController {

    private final AtomicLong counter;
    private final ConcurrentMap<Long, Minesweeper> games;

    public MinesweeperController(ConcurrentMap<Long, Minesweeper> games, AtomicLong counter) {
        this.games = games;
        this.counter = counter;
    }

    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    NewGameWrapper newgame(
            @RequestParam(value = "height", defaultValue = "5") int height,
            @RequestParam(value = "width", defaultValue = "10") int width,
            @RequestParam(value = "bombs", defaultValue = "17") int bombs
    ) {
        long id = counter.getAndIncrement();
        Minesweeper ms = new MinesweeperImpl(
                new RandomAbstractGridGenerator(height, width, bombs).generate()
        );
        games.put(id, ms);
        return new NewGameWrapper(ms.getState(), id);
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    MinesweeperState open (
            @RequestParam(value = "id") long id,
            @RequestParam(value = "row") int row,
            @RequestParam(value = "col") int col
    ) {
        return games.get(id).open(row, col).getState();
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST)
    MinesweeperState flag (
            @RequestParam(value = "id") long id,
            @RequestParam(value = "row") int row,
            @RequestParam(value = "col") int col
    ) {
        return games.get(id).flag(row, col).getState();
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    MinesweeperState game(
            @RequestParam(value = "id") long id
    ) {
        return games.get(id).getState();
    }

    @ExceptionHandler(GameOverException.class)
    void handleGameOverException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Game is already over.");
    }

    @ExceptionHandler(GameIndexOutOfBoundsException.class)
    void handleGameIndexOutOfBoundsException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Coordinates out of bounds.");
    }
}
