package ch.akermann.connectfourchallenge.server.game.connectFour;


import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell;

import java.time.LocalDateTime;
import java.util.*;

public class ConnectFour {

    private final Board board;
    private final LocalDateTime startTime;
    private final UUID gameId;
    private final List<Player> players;
    private final List<Player> nextPlayers;


    public ConnectFour(LocalDateTime startTime, UUID gameId, Board board, List<Player> players) {
        this.startTime = startTime;
        this.gameId = gameId;
        this.board = board;
        this.players = new ArrayList<>(players);
        this.nextPlayers = new ArrayList<>(players);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getRedPlayerid(){
        return players.get(0).getPlayerId();
    }

    public String getYellowPlayerid(){
        return players.get(1).getPlayerId();
    }

    public void dropDisc(String playerId, int column) {
        Player currentPlayer = getCurrentPlayer();
        if (!currentPlayer.getPlayerId().equals(playerId)) {
            throw new ConnectFourInvalidMoveException("Current player is: " + currentPlayer.getPlayerId());
        }
        Collections.rotate(nextPlayers, 1);
        board.dropDisc(currentPlayer.getDisc(), column);
    }

    public boolean isFinished() {
        if (board.isFull()) {
            return true;
        }
        return getWinner().isPresent();
    }

    boolean isDraw() {
        return isFinished() && !getWinner().isPresent();
    }

    public Optional<Player> getWinner() {
        return players.stream()
                .filter(this::areFourConnected)
                .findFirst();
    }

    public Optional<String> getWinnerPlayerId() {
        return players.stream()
                .filter(this::areFourConnected)
                .map(Player::getPlayerId)
                .findFirst();

    }

    public Player getCurrentPlayer() {
        return nextPlayers.stream()
                .findFirst()
                .orElseThrow(() -> new ConnectFourInvalidMoveException("No player found"));
    }

    public String getCurrentPlayerID() {
        return nextPlayers.stream()
                .findFirst()
                .map(Player::getPlayerId)
                .orElseThrow(() -> new ConnectFourInvalidMoveException("No player found"));
    }

    // https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
    // https://stackoverflow.com/users/6381975/ferdelolmo
    private boolean areFourConnected(Player player) {
        Cell playerColor = player.getDisc().getCell();
        // horizontalCheck
        for (int row = 0; row < board.getRows() - 3; row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                if (board.getCell(column, row) == playerColor
                        && board.getCell(column, row + 1) == playerColor
                        && board.getCell(column, row + 2) == playerColor
                        && board.getCell(column, row + 3) == playerColor) {
                    return true;
                }
            }
        }
        // verticalCheck
        for (int column = 0; column < board.getColumns() - 3; column++) {
            for (int row = 0; row < board.getRows(); row++) {
                if (board.getCell(column, row) == playerColor
                        && board.getCell(column + 1, row) == playerColor
                        && board.getCell(column + 2, row) == playerColor
                        && board.getCell(column + 3, row) == playerColor) {
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int column = 3; column < board.getColumns(); column++) {
            for (int row = 0; row < board.getRows() - 3; row++) {
                if (board.getCell(column, row) == playerColor
                        && board.getCell(column - 1, row + 1) == playerColor
                        && board.getCell(column - 2, row + 2) == playerColor
                        && board.getCell(column - 3, row + 3) == playerColor)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int column = 3; column < board.getColumns(); column++) {
            for (int row = 3; row < board.getRows(); row++) {
                if (board.getCell(column, row) == playerColor
                        && board.getCell(column - 1, row - 1) == playerColor
                        && board.getCell(column - 2, row - 2) == playerColor
                        && board.getCell(column - 3, row - 3) == playerColor)
                    return true;
            }
        }
        return false;
    }
}