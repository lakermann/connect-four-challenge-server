package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.TestData;
import ch.akermann.connectfourchallenge.server.game.GameService;
import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;
import ch.akermann.connectfourchallenge.server.game.connectFour.ranking.Count;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduledGame;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UiEndpointTest {

    @MockBean
    private GameService gameService;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;

        Map<String, Count> stats = new HashMap<>();
        stats.put("Alice", new Count());
        Mockito.when(gameService.getStats()).thenReturn(stats);

        UUID gameId = fromString("00000000-0000-0000-0000-000000000000");
        Mockito.when(gameService.getCurrentGame()).thenReturn(Optional.of(gameId));

        LocalDateTime startTime = LocalDateTime.of(2018, 12, 31, 23, 59);
        Board board = new Board(TestData.FULL_BOARD_RED_WINNER);
        List<Player> players = asList(
                new Player("Alice", Disc.RED),
                new Player("Bob", Disc.YELLOW));
        ConnectFour finishedConnectFour = new ConnectFour(startTime, gameId, board, players);
        Mockito.when(gameService.getGame(ArgumentMatchers.any())).thenReturn(finishedConnectFour);

        ScheduledGame scheduledGame = new ScheduledGame(gameId, 2);
        scheduledGame.addPlayerId("Alice");
        scheduledGame.addPlayerId("Bob");
        List<ScheduledGame> scheduledGames = singletonList(scheduledGame);
        Mockito.when(gameService.getScheduledGames()).thenReturn(scheduledGames);

        HashMap<UUID, ConnectFour> games = new HashMap<>();
        games.put(gameId, finishedConnectFour);
        Mockito.when(gameService.getGames()).thenReturn(games);
    }

    @Test
    public void getPlayers() {
        // @formatter:off
        when()
            .get("/api/v1/ui/players/")
        .then()
            .statusCode(200)
            .body("[0]", is(notNullValue()))
            .body("[0].id", is("Alice"))
            .body("[0].count", is(notNullValue()))
            .body("[0].count.draw", is(0))
            .body("[0].count.loss", is(0))
            .body("[0].count.win", is(0));
        // @formatter:on
    }

    @Test
    public void getCurrentGame() {
        // @formatter:off
        when()
            .get("/api/v1/ui/games/current/")
        .then()
            .statusCode(200)
            .body("gameId", is("00000000-0000-0000-0000-000000000000"));
        // @formatter:on
    }

    @Test
    public void getGame() {
        // @formatter:off
        when()
            .get("/api/v1/ui/games/00000000-0000-0000-0000-000000000000")
        .then()
            .statusCode(200)
            .body("finished", is(true))
            .body("redPlayer", is("Alice"))
            .body("yellowPlayer", is("Bob"))
            .body("winner", is("Alice"))
            .body("finished", is(true))
            .body("board", is(notNullValue()))
            .body("board[0]", everyItem(is(notNullValue())))
            .body("board[1]",  everyItem(is(notNullValue())))
            .body("board[2]",  everyItem(is(notNullValue())))
            .body("board[3]",  everyItem(is(notNullValue())))
            .body("board[4]",  everyItem(is(notNullValue())))
            .body("board[5]",  everyItem(is(notNullValue())));
        // @formatter:on
    }

    @Test
    public void getScheduledGames() {
        // @formatter:off
        when()
            .get("/api/v1/ui/scheduled-games")
        .then()
            .statusCode(200)
            .body("[0]", is(notNullValue()))
            .body("[0].id", is("00000000-0000-0000-0000-000000000000"))
            .body("[0].playerIds", is(asList("Alice", "Bob")));
        // @formatter:on
    }

    @Test
    public void addScheduledGame() {
        // @formatter:off
        given()
            .header("Content-Type", "application/json")
            .body("{\"playerA\":\"Alice\"," +
                    "\"playerB\":\"Bob\"," +
                    "\"numberOfGames\":\"1\"}")
        .when()
            .post("/api/v1/ui/scheduled-games")
        .then()
            .statusCode(204);
        // @formatter:on
    }

    @Test
    public void deleteScheduledGames() {
        // @formatter:off
        when()
            .delete("/api/v1/ui/scheduled-games/00000000-0000-0000-0000-000000000000")
        .then()
            .statusCode(204);
        // @formatter:on
    }

    @Test
    public void getGames() {
        // @formatter:off
        when()
            .get("/api/v1/ui/games")
        .then()
            .statusCode(200)
            .body("[0].id", is("00000000-0000-0000-0000-000000000000"))
            .body("[0].startTime", is("2018-12-31T23:59:00"))
            .body("[0].redPlayer", is("Alice"))
            .body("[0].yellowPlayer", is("Bob"))
            .body("[0].winner", is("Alice"))
            .body("[0].finished", is(true));
        // @formatter:on
    }

}