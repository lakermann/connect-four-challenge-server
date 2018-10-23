package ch.akermann.connectfourchallenge.server.game.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScheduledGame {

    private final UUID id;
    private final int numberOfPlayers;
    private final List<String> playerIds = new ArrayList<>();

    public ScheduledGame(UUID id, int numberOfPlayers) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
    }

    ScheduledGame(UUID id, int numberOfPlayers, List<String> playerIds) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
        this.playerIds.addAll(playerIds);
    }

    public UUID getId() {
        return id;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    boolean containsPlayerId(String playerId) {
        return playerIds.contains(playerId);
    }

    public void addPlayerId(String playerId) {
        if (containsPlayerId(playerId)) {
            throw new RuntimeException("The same player can't be added multiple times to the same game: " + playerId);
        }
        if (playerIds.size() + 1 > numberOfPlayers) {
            throw new RuntimeException("Number of players is limited to: " + numberOfPlayers);
        }
        playerIds.add(playerId);
    }

    public boolean hasEnoughPlayers() {
        return numberOfPlayers == playerIds.size();
    }

}
