package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.game.schedule.ScheduledGame;

import java.util.List;
import java.util.UUID;

public class ScheduledGameResponse {

    private final UUID id;
    private final List<String> playerIds;

    private ScheduledGameResponse(UUID id, List<String> playerIds) {
        this.id = id;
        this.playerIds = playerIds;
    }

    static ScheduledGameResponse toScheduledGameResponse(ScheduledGame scheduledGame) {
        return new ScheduledGameResponse(
                scheduledGame.getId(),
                scheduledGame.getPlayerIds()
        );
    }

    @SuppressWarnings("unused")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public List<String> getPlayerIds() {
        return playerIds;
    }

}
