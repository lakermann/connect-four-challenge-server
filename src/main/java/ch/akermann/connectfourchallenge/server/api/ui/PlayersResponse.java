package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.game.connectFour.ranking.Count;

import static ch.akermann.connectfourchallenge.server.api.ui.CountResponse.toCountResponse;

public class PlayersResponse {
    private final String id;
    private final CountResponse count;

    private PlayersResponse(String id, CountResponse count) {
        this.id = id;
        this.count = count;
    }

    static PlayersResponse toPlayersResponse(String id, Count count) {
        return new PlayersResponse(
                id,
                toCountResponse(count));
    }

    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public CountResponse getCount() {
        return count;
    }
}
