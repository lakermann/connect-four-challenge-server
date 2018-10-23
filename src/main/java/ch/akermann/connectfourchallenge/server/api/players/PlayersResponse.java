package ch.akermann.connectfourchallenge.server.api.players;

import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;

public class PlayersResponse {

    private final String playerId;
    private final String disc;

    private PlayersResponse(String playerId, String disc) {
        this.playerId = playerId;
        this.disc = disc;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getDisc() {
        return disc;
    }

    static PlayersResponse toPlayerResponse(String playerId, Disc disc){
        return new PlayersResponse(playerId, disc.toString());
    }

}
