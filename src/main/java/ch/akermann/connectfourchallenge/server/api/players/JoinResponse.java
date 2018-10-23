package ch.akermann.connectfourchallenge.server.api.players;

import java.util.UUID;

public class JoinResponse {

    private final UUID gameId;

    private JoinResponse(UUID gameId) {
        this.gameId = gameId;
    }

    static JoinResponse toJoinResponse(UUID gameId) {
        return new JoinResponse(gameId);
    }

    static JoinResponse createEmptyJointResponse() {
        return new JoinResponse(null);
    }

    @SuppressWarnings("unused")
    public UUID getGameId() {
        return gameId;
    }
}
