package ch.akermann.connectfourchallenge.server.game.connectFour.ranking;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;

import java.util.Optional;

public enum State {
    WIN,
    LOSS,
    DRAW;

    public static State fromConnectFour(ConnectFour connectFour, String playerId) {
        if (connectFour.getPlayers().stream().noneMatch(p -> p.getPlayerId().equals(playerId))){
            throw new RuntimeException("Player did not play the game: "+connectFour);
        }
        Optional<String> winnerPlayerId = connectFour.getWinnerPlayerId();
        if (!winnerPlayerId.isPresent()) {
            return DRAW;
        }
        if (winnerPlayerId.get().equals(playerId)) {
            return WIN;
        }
        return LOSS;
    }

}
