package ch.akermann.connectfourchallenge.server.api.players;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell;

import java.util.List;
import java.util.stream.Collectors;

import static ch.akermann.connectfourchallenge.server.api.players.PlayersResponse.toPlayerResponse;

public class GameResponse {

    private final String winner;
    private final boolean finished;
    private final String currentPlayerId;
    private final List<PlayersResponse> players;
    private final Cell[][] board;

    private GameResponse(String winner, boolean finished, String currentPlayerId, List<PlayersResponse> players, Cell[][] board) {
        this.winner = winner;
        this.finished = finished;
        this.currentPlayerId = currentPlayerId;
        this.players = players;
        this.board = board;
    }

    static GameResponse toGameResponse(ConnectFour connectFour) {

        return new GameResponse(
                connectFour.getWinner().map(ch.akermann.connectfourchallenge.server.game.connectFour.Player::getPlayerId).orElse(null),
                connectFour.isFinished(),
                connectFour.getCurrentPlayer().getPlayerId(),
                connectFour.getPlayers().stream().map(p -> toPlayerResponse(p.getPlayerId(), p.getDisc())).collect(Collectors.toList()),
                connectFour.getBoard().getCells()
        );
    }

    @SuppressWarnings("unused")
    public String getWinner() {
        return winner;
    }

    @SuppressWarnings("unused")
    public boolean isFinished() {
        return finished;
    }

    @SuppressWarnings("unused")
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    @SuppressWarnings("unused")
    public List<PlayersResponse> getPlayers() {
        return players;
    }

    @SuppressWarnings("unused")
    public Cell[][] getBoard() {
        return board;
    }
}
