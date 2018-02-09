package com.example.minesweeper_rest;

import com.example.minesweeper_rest.logic.Cell;
import com.example.minesweeper_rest.logic.Grid;
import com.example.minesweeper_rest.logic.MinesweeperLogic;
import com.example.minesweeper_rest.logic.RandomGridGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MinesweeperController {

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, MinesweeperLogic> games = new ConcurrentHashMap<>();

    @RequestMapping(value = "/startgame", method = RequestMethod.POST)
    StartGameResult startGame(
            @RequestParam(value = "height", defaultValue = "5") int height,
            @RequestParam(value = "width", defaultValue = "10") int width,
            @RequestParam(value = "bombs", defaultValue = "17") int bombs) {
        MinesweeperLogic ms = new MinesweeperLogic(new RandomGridGenerator().generate(height, width, bombs));
        games.put(counter.incrementAndGet(), ms);
        return new StartGameResult(counter.get(), ms.getGrid());
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    OpenResult open(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y) {
        MinesweeperLogic ms = games.get(id);
        boolean gameOver = ms.open(x, y);
        String msg = "";
        if (gameOver) {
            games.remove(id);
            msg = "You lose... ";
        } else if (ms.getClosedCells() == ms.getBombs()) {
            games.remove(id);
            gameOver = true;
            msg = "You win! ";
        }

        return new OpenResult(ms.getGrid().get(x, y), gameOver, msg);
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST)
    FlagResult flag(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y) {
        MinesweeperLogic ms = games.get(id);
        ms.flag(x, y);

        return new FlagResult(ms.getGrid().get(x, y));
    }

    @RequestMapping(value = "/cell", method = RequestMethod.GET)
    Cell getCell(
            @RequestParam(value = "id") long id,
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y) {
        return games.get(id).getGrid().get(x, y);
    }

    @RequestMapping(value = "/grid", method = RequestMethod.GET)
    Grid getGrid(
            @RequestParam(value = "id") long id
    ) {
        return games.get(id).getGrid();
    }
}
