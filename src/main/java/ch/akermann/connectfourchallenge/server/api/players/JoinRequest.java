package ch.akermann.connectfourchallenge.server.api.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinRequest {

    private final String playerId;

    @JsonCreator
    public JoinRequest(@JsonProperty(value = "playerId", required = true) String playerId) {
        this.playerId = playerId;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPlayerId() {
        return playerId;
    }

}
