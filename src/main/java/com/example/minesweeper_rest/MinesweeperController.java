package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MinesweeperController {

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, MinesweeperLogic> games = new ConcurrentHashMap<>();

    @RequestMapping(value = "/newgame", method = RequestMethod.POST)
    GameState newgame(
            @RequestParam(value = "height", defaultValue = "5") int height,
            @RequestParam(value = "width", defaultValue = "10") int width,
            @RequestParam(value = "bombs", defaultValue = "17") int bombs
    ) {
        long id = counter.incrementAndGet();
        MinesweeperLogic ms = new MinesweeperLogic(
                new RandomGridGenerator(height, width, bombs).generate(),
                id
        );
        games.put(id, ms);
        return ms.getGameState();
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    GameState open (
            @RequestParam(value = "id") long id,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y
    ) {
        return games.get(id).open(x, y);
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST)
    GameState flag (
            @RequestParam(value = "id") long id,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y
    ) {
        return games.get(id).flag(x, y);
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    GameState game(
            @RequestParam(value = "id") long id
    ) {
        return games.get(id).getGameState();
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
