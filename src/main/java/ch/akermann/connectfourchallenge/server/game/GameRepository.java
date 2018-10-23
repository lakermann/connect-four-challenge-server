package ch.akermann.connectfourchallenge.server.game;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class GameRepository {
    private static final int NUMBER_OF_GAMES_TO_KEEP = 100_000;

    private final Map<UUID, ConnectFour> games = new ConcurrentHashMap<>(new MaxSizeHashMap<>(NUMBER_OF_GAMES_TO_KEEP));

    boolean hasGameWithId(UUID gameId){
        return games.containsKey(gameId);
    }

    void addGame(UUID uuid, ConnectFour connectFour){
        games.put(uuid, connectFour);
    }

    ConnectFour getGame(UUID gameId){
        if (games.containsKey(gameId)) {
            return games.get(gameId);
        }
        throw new GameNotFoundException("Game doesn't exist: " + gameId);
    }

    void removeGame(UUID gameId){
        games.remove(gameId);
    }


    Map<UUID, ConnectFour> getGames() {
        return games;
    }
}
