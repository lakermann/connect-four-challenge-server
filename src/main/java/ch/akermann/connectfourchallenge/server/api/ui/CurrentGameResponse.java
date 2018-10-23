package ch.akermann.connectfourchallenge.server.api.ui;

import java.util.UUID;

public class CurrentGameResponse {

    private final UUID gameId;

    private CurrentGameResponse(UUID gameId) {
        this.gameId = gameId;
    }

    static CurrentGameResponse toCurrentGameResponse(UUID gameId) {
        return new CurrentGameResponse(gameId);
    }

    static CurrentGameResponse createEmptyCurrentGameResponse() {
        return new CurrentGameResponse(null);
    }

    @SuppressWarnings("unused")
    public UUID getGameId() {
        return gameId;
    }
}
