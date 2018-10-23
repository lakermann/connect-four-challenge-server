package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;

import java.time.LocalDateTime;
import java.util.UUID;

public class GamesResponse {

    private final UUID id;
    private final LocalDateTime startTime;
    private final String redPlayer;
    private final String yellowPlayer;
    private final String winner;
    private final boolean finished;


    private GamesResponse(UUID id, LocalDateTime startTime, String redPlayer, String yellowPlayer, String winner, boolean finished) {
        this.id = id;
        this.startTime = startTime;
        this.redPlayer = redPlayer;
        this.yellowPlayer = yellowPlayer;
        this.winner = winner;
        this.finished = finished;
    }

    static GamesResponse toGamesResponse(UUID gameId, ConnectFour connectFour) {
        return new GamesResponse(
                gameId,
                connectFour.getStartTime(),
                connectFour.getRedPlayerid(),
                connectFour.getYellowPlayerid(),
                connectFour.getWinner().map(Player::getPlayerId).orElse(null),
                connectFour.isFinished()
        );
    }

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("WeakerAccess")
    public LocalDateTime getStartTime() {
        return startTime;
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
    public String getWinner() {
        return winner;
    }

    @SuppressWarnings("unused")
    public boolean isFinished() {
        return finished;
    }
}
