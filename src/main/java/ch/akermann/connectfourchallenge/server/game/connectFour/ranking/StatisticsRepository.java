package ch.akermann.connectfourchallenge.server.game.connectFour.ranking;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;

import java.util.HashMap;
import java.util.Map;

import static ch.akermann.connectfourchallenge.server.game.connectFour.ranking.State.fromConnectFour;

public class StatisticsRepository {

    private Map<String, Count> entries = new HashMap<>();

    public void updateValue(String playerId, ConnectFour connectFour) {
        entries.putIfAbsent(playerId, new Count());
        Count count = entries.get(playerId);
        State state = fromConnectFour(connectFour, playerId);
        count.updateEntry(state);
    }

    public Map<String, Count> getEntries() {
        return entries;
    }

}
