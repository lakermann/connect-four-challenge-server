package ch.akermann.connectfourchallenge.server.game.connectFour.ranking;

import java.util.HashMap;
import java.util.Map;

import static ch.akermann.connectfourchallenge.server.game.connectFour.ranking.State.*;

public class Count {

    private Map<State, Integer> count = new HashMap<>();

    public int getDraw() {
        return count.getOrDefault(DRAW, 0);
    }

    public int getLoss() {
        return count.getOrDefault(LOSS, 0);
    }

    public int getWin() {
        return count.getOrDefault(WIN, 0);
    }

    void updateEntry(State state) {
        int currentValue = count.getOrDefault(state, 0);
        count.put(state, ++currentValue);
    }
}
