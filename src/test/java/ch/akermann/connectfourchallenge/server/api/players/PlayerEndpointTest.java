package ch.akermann.connectfourchallenge.server.api.players;

import ch.akermann.connectfourchallenge.server.TestData;
import ch.akermann.connectfourchallenge.server.game.GameService;
import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static java.util.UUID.fromString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerEndpointTest {

    @MockBean
    private GameService gameService;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws InterruptedException {
        RestAssured.port = port;
        UUID gameId = fromString("00000000-0000-0000-0000-000000000000");
        Mockito.when(gameService.join(any())).thenReturn(Optional.of(gameId));
        LocalDateTime startDateTime = LocalDateTime.now();
        Board board = new Board(TestData.FULL_BOARD_RED_WINNER);
        List<Player> players = asList(
                new Player("Alice", Disc.RED),
                new Player("Bob", Disc.YELLOW));
        ConnectFour connectFour = new ConnectFour(startDateTime, gameId, board, players);
        Mockito.when(gameService.getGame(any())).thenReturn(connectFour);
        Mockito.when(gameService.dropDisc(any(), anyString(), anyInt())).thenReturn(connectFour);
    }

    @Test
    public void join() {
        // @formatter:off
        given()
            .header("Content-Type", "application/json")
            .body("{\"playerId\":\"Alice\"}")
        .when()
            .post("/api/v1/players/join/")
        .then()
            .statusCode(200)
            .body("gameId", is("00000000-0000-0000-0000-000000000000"));
        // @formatter:on
    }

    @Test
    public void getGame() {
        // @formatter:off
        when()
            .get("/api/v1/players/games/00000000-0000-0000-0000-000000000000")
        .then()
            .statusCode(200)
            .body("currentPlayerId", is("Alice"))
            .body("players", is(notNullValue()))
            .body("players[0].playerId", is("Alice"))
            .body("players[0].disc", is("RED"))
            .body("players[1].playerId", is("Bob"))
            .body("players[1].disc", is("YELLOW"))
            .body("finished", is(true))
            .body("winner", is("Alice"))
            .body("board[0]", everyItem(is(not("EMPTY"))))
            .body("board[1]", everyItem(is(not("EMPTY"))))
            .body("board[2]", everyItem(is(not("EMPTY"))))
            .body("board[3]", everyItem(is(not("EMPTY"))))
            .body("board[4]", everyItem(is(not("EMPTY"))))
            .body("board[5]", everyItem(is(not("EMPTY"))));
        // @formatter:on
    }

    @Test
    public void dropDisc() {
        // @formatter:off
        given()
            .header("Content-Type", "application/json")
            .body("{" +
                "\"playerId\": \"Alice\"," +
                "\"column\": 1" +
                "}")
        .when()
            .post("/api/v1/players/games/00000000-0000-0000-0000-000000000000")
        .then()
            .statusCode(200)
            .body("currentPlayerId", is("Alice"))
            .body("players", is(notNullValue()))
            .body("players[0].playerId", is("Alice"))
            .body("players[0].disc", is("RED"))
            .body("players[1].playerId", is("Bob"))
            .body("players[1].disc", is("YELLOW"))
            .body("finished", is(true))
            .body("winner", is("Alice"))
            .body("board[0]", everyItem(is(not("EMPTY"))))
            .body("board[1]", everyItem(is(not("EMPTY"))))
            .body("board[2]", everyItem(is(not("EMPTY"))))
            .body("board[3]", everyItem(is(not("EMPTY"))))
            .body("board[4]", everyItem(is(not("EMPTY"))))
            .body("board[5]", everyItem(is(not("EMPTY"))));
        // @formatter:on
    }

}