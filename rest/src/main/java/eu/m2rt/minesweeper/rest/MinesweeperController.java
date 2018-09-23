package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.*;
import eu.m2rt.minesweeper.logic.exceptions.GameIndexOutOfBoundsException;
import eu.m2rt.minesweeper.logic.exceptions.GameOverException;
import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
@RestController
public class MinesweeperController {

    private final ConcurrentMap<String, Minesweeper> games;

    @Autowired
    public MinesweeperController(ConcurrentMap<String, Minesweeper> games) {
        this.games = games;
    }

    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    NewGameWrapper newgame(
            @RequestParam(value = "height", defaultValue = "5") int height,
            @RequestParam(value = "width", defaultValue = "10") int width,
            @RequestParam(value = "bombs", defaultValue = "17") int bombs
    ) {
        String id = UUID.randomUUID().toString();
        Minesweeper ms = new MinesweeperImpl(
                new RandomGridGenerator(height, width, bombs).generate()
        );
        games.put(id, ms);
        return new NewGameWrapper(ms.getState(), id);
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    MinesweeperState open (
            @RequestParam(value = "id") String id,
            @RequestParam(value = "row") int row,
            @RequestParam(value = "col") int col
    ) {
        return games.get(id).open(row, col).getState();
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST)
    MinesweeperState flag (
            @RequestParam(value = "id") String id,
            @RequestParam(value = "row") int row,
            @RequestParam(value = "col") int col
    ) {
        return games.get(id).flag(row, col).getState();
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    MinesweeperState game(
            @RequestParam(value = "id") String id
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
