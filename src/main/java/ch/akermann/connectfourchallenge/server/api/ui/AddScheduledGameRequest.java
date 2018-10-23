package ch.akermann.connectfourchallenge.server.api.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddScheduledGameRequest {
    private final String playerA;
    private final String playerB;
    private final int numberOfGames;

    @JsonCreator
    public AddScheduledGameRequest(@JsonProperty(value = "playerA", required = true) String playerA,
                                   @JsonProperty(value = "playerB", required = true) String playerB,
                                   @JsonProperty(value = "numberOfGames", required = true) int numberOfGames) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.numberOfGames = numberOfGames;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPlayerA() {
        return playerA;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPlayerB() {
        return playerB;
    }

    @SuppressWarnings("WeakerAccess")
    public int getNumberOfGames() {
        return numberOfGames;
    }
}
