package ch.akermann.connectfourchallenge.server.game.connectFour.ranking;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ch.akermann.connectfourchallenge.server.TestData.FULL_BOARD_DRAW;
import static ch.akermann.connectfourchallenge.server.TestData.FULL_BOARD_RED_WINNER;
import static ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc.RED;
import static ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc.YELLOW;
import static ch.akermann.connectfourchallenge.server.game.connectFour.ranking.State.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StateTest {

    @Test(expected = RuntimeException.class)
    public void fromConnectFour_invalidPlayer_throwsException() {
        Board board = new Board(FULL_BOARD_DRAW);
        ConnectFour connectFour = createConnectFour(board, "Alice", "Bob");

        State.fromConnectFour(connectFour, "Carolß");
    }

    @Test
    public void fromConnectFour_draw() {
        Board board = new Board(FULL_BOARD_DRAW);
        ConnectFour connectFour = createConnectFour(board, "Alice", "Bob");

        State stateAlice = State.fromConnectFour(connectFour, "Alice");
        State stateBob = State.fromConnectFour(connectFour, "Bob");

        assertThat(stateAlice, is(DRAW));
        assertThat(stateBob, is(DRAW));
    }

    @Test
    public void fromConnectFour_win() {
        Board board = new Board(FULL_BOARD_RED_WINNER);
        ConnectFour connectFour = createConnectFour(board, "Alice", "Bob");

        State state = State.fromConnectFour(connectFour, "Alice");

        assertThat(state, is(WIN));
    }

    @Test
    public void fromConnectFour_loss() {
        Board board = new Board(FULL_BOARD_RED_WINNER);
        ConnectFour connectFour = createConnectFour(board, "Bob", "Alice");

        State state = State.fromConnectFour(connectFour, "Alice");

        assertThat(state, is(LOSS));
    }

    private ConnectFour createConnectFour(Board board, String redPlayerId, String yellowPlayerId) {
        List<Player> players = asList(
                new Player(redPlayerId, RED),
                new Player(yellowPlayerId, YELLOW));
        return new ConnectFour(LocalDateTime.now(), UUID.randomUUID(), board, players);
    }
}