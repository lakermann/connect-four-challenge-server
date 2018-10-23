package ch.akermann.connectfourchallenge.server.api.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DropDiscRequest {

    private final String playerId;
    private final int column;

    @JsonCreator
    public DropDiscRequest(@JsonProperty(value = "playerId", required = true) String playerId,
                           @JsonProperty(value = "column", required = true) int column) {
        this.playerId = playerId;
        this.column = column;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPlayerId() {
        return playerId;
    }

    @SuppressWarnings("WeakerAccess")
    public int getColumn() {
        return column;
    }
}
