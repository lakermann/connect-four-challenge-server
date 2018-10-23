package ch.akermann.connectfourchallenge.server.game.connectFour;

import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ch.akermann.connectfourchallenge.server.TestData.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class ConnectFourTest {

    private static final Player redPlayer = new Player("Alice", Disc.RED);
    private static final Player yellowPlayer = new Player("Bob", Disc.YELLOW);
    private static final LocalDateTime startTime = LocalDateTime.now();
    private static final UUID gameId = UUID.randomUUID();

    @Test
    public void dropDisc() {
        Board board = new Board(6, 7);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));
        Player currentPlayer = connectFour.getCurrentPlayer();
        connectFour.dropDisc(currentPlayer.getPlayerId(), 6);

        assertThat(connectFour.getWinner(), is(Optional.empty()));
        assertThat(connectFour.isFinished(), is(false));
        assertThat(connectFour.isDraw(), is(false));
        assertThat(connectFour.getPlayers(), containsInAnyOrder(redPlayer, yellowPlayer));
        assertThat(connectFour.getCurrentPlayer(), is(yellowPlayer == currentPlayer ? redPlayer : yellowPlayer));
        assertConnectFour(connectFour);
    }

    @Test(expected = ConnectFourInvalidMoveException.class)
    public void dropDisc_invalidPlayer() {
        Board board = new Board(6, 7);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, singletonList(redPlayer));

        connectFour.dropDisc("C", 6);
    }

    @Test
    public void areFourConnected_emptyBoard_noWinner() {
        Board board = new Board(EMPTY_BOARD);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertThat(connectFour.getWinner(), is(Optional.empty()));
        assertThat(connectFour.isFinished(), is(false));
        assertThat(connectFour.isDraw(), is(false));
        assertConnectFour(connectFour);
    }

    @Test
    public void areFourConnected_fullBoard_draw() {
        Board board = new Board(FULL_BOARD_DRAW);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertThat(connectFour.getWinner(), is(Optional.empty()));
        assertThat(connectFour.isFinished(), is(true));
        assertThat(connectFour.isDraw(), is(true));
        assertConnectFour(connectFour);
    }

    @Test
    public void areFourConnected_fullBoard_redWinner() {
        Board board = new Board(FULL_BOARD_RED_WINNER);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertRedPlayerWins(connectFour);
    }

    @Test
    public void areFourConnected_horizonal_redWinner() {
        Board board = new Board(HORIZONTAL_RED_WINNER);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertRedPlayerWins(connectFour);
    }

    @Test
    public void areFourConnected_vertical_redWinner() {
        Board board = new Board(VERTICAL_RED_WINNER);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertRedPlayerWins(connectFour);
    }

    @Test
    public void areFourConnected_ascendingDiagonal_redWinner() {
        Board board = new Board(ASCENDING_DIAGONAL_RED_WINNER);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertRedPlayerWins(connectFour);
    }

    @Test
    public void areFourConnected_descendingDiagonal_redWinner() {
        Board board = new Board(DESCENDING_DIAGONAL_RED_WINNER);
        ConnectFour connectFour = new ConnectFour(startTime, gameId, board, asList(redPlayer, yellowPlayer));

        assertRedPlayerWins(connectFour);
    }

    private void assertRedPlayerWins(ConnectFour connectFour) {
        assertThat(connectFour.getWinner(), is(Optional.of(redPlayer)));
        assertThat(connectFour.isFinished(), is(true));
        assertThat(connectFour.isDraw(), is(false));
        assertConnectFour(connectFour);
    }

    private void assertConnectFour(ConnectFour connectFour) {
        assertThat(connectFour.getStartTime(), is(notNullValue()));
        assertThat(connectFour.getGameId(), is(notNullValue()));
        assertThat(connectFour.getBoard(), is(notNullValue()));
    }
}
