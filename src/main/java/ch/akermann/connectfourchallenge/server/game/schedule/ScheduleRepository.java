package ch.akermann.connectfourchallenge.server.game.schedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class ScheduleRepository {

    private final int numberOfPlayersPerGame;
    private List<ScheduledGame> scheduledGames = new CopyOnWriteArrayList<>();

    public ScheduleRepository(int numberOfPlayersPerGame) {
        this.numberOfPlayersPerGame = numberOfPlayersPerGame;
    }

    private static Predicate<ScheduledGame> containsPlayerId(String playerId) {
        return s -> s.containsPlayerId(playerId);
    }

    private static Predicate<ScheduledGame> hasGameId(UUID gameId) {
        return s -> s.getId().equals(gameId);
    }

    private static Predicate<ScheduledGame> doesNotContainPlayerId(String playerId) {
        return s -> !s.containsPlayerId(playerId);
    }

    private static Predicate<ScheduledGame> needsAdditionalPlayers() {
        return s -> !s.hasEnoughPlayers();
    }

    public synchronized void registerPlayer(String playerId) {
        if (scheduledGames.isEmpty()) {
            ScheduledGame scheduledGame = new ScheduledGame(UUID.randomUUID(), numberOfPlayersPerGame);
            scheduledGames.add(scheduledGame);
        }
        scheduledGames.stream()
                .findFirst()
                .filter(needsAdditionalPlayers())
                .filter(doesNotContainPlayerId(playerId))
                .ifPresent(s -> s.addPlayerId(playerId));
    }

    public Optional<ScheduledGame> getNextScheduledGame(String playerId) {
        return scheduledGames.stream()
                .findFirst()
                .filter(containsPlayerId(playerId));
    }

    public Optional<ScheduledGame> getCurrentGame() {
        return scheduledGames.stream()
                .findFirst();
    }

    public List<ScheduledGame> getScheduledGames() {
        return scheduledGames;
    }

    public void removeGame(UUID gameId) {
        scheduledGames.stream()
                .filter(hasGameId(gameId))
                .findFirst()
                .ifPresent(s -> scheduledGames.remove(s));
    }

    public void addNewScheduledGame(List<String> playerIds) {
        ScheduledGame scheduledGame = new ScheduledGame(UUID.randomUUID(), numberOfPlayersPerGame, playerIds);
        scheduledGames.add(scheduledGame);
    }
}
