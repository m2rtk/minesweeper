package eu.m2rt.minesweeper.spark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.m2rt.minesweeper.logic.MinesweeperImpl;
import eu.m2rt.minesweeper.logic.RandomGridGenerator;
import eu.m2rt.minesweeper.logic.exceptions.GameIndexOutOfBoundsException;
import eu.m2rt.minesweeper.logic.exceptions.GameOverException;
import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static spark.Spark.*;

public class MinesweeperSpark {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ConcurrentMap<String, Minesweeper> games = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Spark.staticFiles.location("/public");
        Spark.port(8080);

        path("/api", () -> {
            post("/newgame",     API::newGame,   MinesweeperSpark::toJson);
            post("/open",       API::open,      MinesweeperSpark::toJson);
            post("/flag",       API::flag,      MinesweeperSpark::toJson);
            post("/chord",      API::chord,     MinesweeperSpark::toJson);
            get("/game",        API::game,      MinesweeperSpark::toJson);
        });

        exception(GameOverException.class, (exception, request, response) -> {
            response.body("Game is over!");
            response.status(400);
        });

        exception(GameIndexOutOfBoundsException.class, (exception, request, response) -> {
            response.body("Selected cell is out of bounds");
            response.status(400);
        });
    }

    private static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class API {

        private static Object newGame(Request request, Response response) {
            NewGameRequest req = NewGameRequest.from(request);
            String id = UUID.randomUUID().toString();
            Minesweeper ms = new MinesweeperImpl(
                    new RandomGridGenerator(req.height, req.width, req.bombs).generate()
            );
            games.put(id, ms);
            return new NewGameWrapper(ms.getState(), id);
        }

        private static Object open(Request request, Response response) {
            CellRequest req = CellRequest.from(request);
            return req.game().open(req.row, req.col).getState();
        }
        private static Object flag(Request request, Response response) {
            CellRequest req = CellRequest.from(request);
            return req.game().flag(req.row, req.col).getState();
        }
        private static Object chord(Request request, Response response) {
            CellRequest req = CellRequest.from(request);
            return req.game().chord(req.row, req.col).getState();
        }
        private static Object game(Request request, Response response) {
            IdRequest req = IdRequest.from(request);
            return req.game().getState();
        }

    }

    private static class NewGameRequest {
        private final int width, height, bombs;

        private NewGameRequest(Request request) {
            this.width = parseInt(request, "width", 16);
            this.height = parseInt(request, "height", 16);
            this.bombs = parseInt(request, "bombs", 40);
        }

        private static NewGameRequest from(Request request) {
            return new NewGameRequest(request);
        }
    }

    private static class IdRequest {
        private final String id;

        private IdRequest(Request request) {
            this.id = id(request);
        }

        private static IdRequest from(Request request) {
            return new IdRequest(request);
        }

        Minesweeper game() {
            return games.get(id);
        }
    }

    private static class CellRequest extends IdRequest {
        private final int row, col;

        private CellRequest(Request request) {
            super(request);
            this.row = parseInt(request, "row");
            this.col = parseInt(request, "col");
        }

        private static CellRequest from(Request request) {
            return new CellRequest(request);
        }
    }

    private static String id(Request request) {
        String id = request.queryParams("id");

        if ( ! games.containsKey(id)) {
            throw halt(400, "Unknown game id!");
        }

        return id;
    }

    private static int parseInt(Request request, String name) {
        return parseInt(request, name, null);
    }

    private static int parseInt(Request request, String name, Integer defaultValue) {
        String val = request.queryParams(name);

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            if (defaultValue != null) {
                return defaultValue;
            }

            throw Spark.halt(400, "Invalid value for parameter " + name);
        }
    }
}
