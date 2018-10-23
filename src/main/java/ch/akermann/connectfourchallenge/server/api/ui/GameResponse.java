package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell;

public class GameResponse {

    private final boolean finished;
    private final String redPlayer;
    private final String yellowPlayer;
    private final String currentPlayer;
    private final String winner;
    private final Cell[][] board;

    private GameResponse(boolean finished, String redPlayer, String yellowPlayer, String currentPlayer, String winner, Cell[][] board) {
        this.finished = finished;
        this.redPlayer = redPlayer;
        this.yellowPlayer = yellowPlayer;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.board = board;

    }

    static GameResponse toGameResponse(ConnectFour connectFour) {
        return new GameResponse(
                connectFour.isFinished(),
                connectFour.getRedPlayerid(),
                connectFour.getYellowPlayerid(),
                connectFour.getCurrentPlayerID(),
                connectFour.getWinnerPlayerId().orElse(null),
                connectFour.getBoard().getCells());
    }

    @SuppressWarnings("unused")
    public boolean isFinished() {
        return finished;
    }

    @SuppressWarnings("unused")
    public String getRedPlayer() {
        return redPlayer;
    }

    @SuppressWarnings("unused")
    public String getYellowPlayer() {
        return yellowPlayer;
    }

    @SuppressWarnings("unused")
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @SuppressWarnings("unused")
    public String getWinner() {
        return winner;
    }

    @SuppressWarnings("unused")
    public Cell[][] getBoard() {
        return board;
    }
}
