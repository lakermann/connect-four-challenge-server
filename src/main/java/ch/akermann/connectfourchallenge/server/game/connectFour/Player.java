package ch.akermann.connectfourchallenge.server.game.connectFour;

import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;

public class Player {

    private final String playerId;
    private final Disc disc;

    public Player(String playerId, Disc disc) {
        this.playerId = playerId;
        this.disc = disc;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Disc getDisc() {
        return disc;
    }

}
