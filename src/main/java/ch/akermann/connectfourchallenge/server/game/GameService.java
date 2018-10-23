package ch.akermann.connectfourchallenge.server.game;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;
import ch.akermann.connectfourchallenge.server.game.connectFour.ranking.Count;
import ch.akermann.connectfourchallenge.server.game.connectFour.ranking.StatisticsRepository;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduleRepository;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduledGame;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;

public class GameService {

    private static final int NUMBER_OF_BOARD_ROWS = 6;
    private static final int NUMBER_OF_BOARD_COLUMNS = 7;
    private final ScheduleRepository scheduleRepository;
    private final boolean autoRegister;
    private final long waitIfGameIsFinishedInMs;
    private final StatisticsRepository statisticsRepository;
    private final GameRepository gameRepository;

    public GameService(ScheduleRepository scheduleRepository, boolean autoRegister, long waitIfGameIsFinishedInMs) {
        this.scheduleRepository = scheduleRepository;
        this.autoRegister = autoRegister;
        this.waitIfGameIsFinishedInMs = waitIfGameIsFinishedInMs;
        this.statisticsRepository = new StatisticsRepository();
        this.gameRepository = new GameRepository();
    }

    public Optional<UUID> join(String playerId) {
        if (autoRegister) {
            register(playerId);
        }
        return getOrCreateGameId(playerId);
    }

    void register(String playerId) {
        scheduleRepository.registerPlayer(playerId);

    }

    Optional<UUID> getOrCreateGameId(String playerId) {
        Optional<ScheduledGame> scheduledGame = scheduleRepository.getNextScheduledGame(playerId);
        if (!scheduledGame.isPresent()) {
            return Optional.empty();
        }

        if (!scheduledGame.get().hasEnoughPlayers()) {
            return Optional.empty();
        } else if (!gameRepository.hasGameWithId(scheduledGame.get().getId())) {
            ConnectFour connectFour = new ConnectFour(LocalDateTime.now(), UUID.randomUUID(), new Board(NUMBER_OF_BOARD_ROWS, NUMBER_OF_BOARD_COLUMNS),
                    asList(
                            new Player(scheduledGame.get().getPlayerIds().get(0), Disc.RED),
                            new Player(scheduledGame.get().getPlayerIds().get(1), Disc.YELLOW)
                    )
            );
            gameRepository.addGame(scheduledGame.get().getId(), connectFour);
        }
        return Optional.of(scheduledGame.get().getId());
    }

    public ConnectFour getGame(UUID gameId) {
        return gameRepository.getGame(gameId);
    }

    public ConnectFour dropDisc(UUID gameId, String playerId, int column) throws InterruptedException {
        ConnectFour connectFour = getGame(gameId);
        connectFour.dropDisc(playerId, column);
        if (connectFour.isFinished()) {
            Thread.sleep(waitIfGameIsFinishedInMs);
            scheduleRepository.removeGame(gameId);
            connectFour.getPlayers().forEach(p -> statisticsRepository.updateValue(p.getPlayerId(), connectFour));
        }

        return connectFour;
    }

    public List<ScheduledGame> getScheduledGames() {
        return scheduleRepository.getScheduledGames();
    }

    public Map<UUID, ConnectFour> getGames() {
        return gameRepository.getGames();
    }

    public Map<String, Count> getStats() {
        return statisticsRepository.getEntries();
    }

    public Optional<UUID> getCurrentGame() {
        return scheduleRepository.getCurrentGame()
                .map(ScheduledGame::getId);
    }

    public void deleteScheduledGame(UUID gameId) {
        scheduleRepository.removeGame(gameId);
        deleteGame(gameId);
    }

    private void deleteGame(UUID gameId) {
        gameRepository.removeGame(gameId);
    }

    public void addScheduledGame(String playerA, String playerB, int numberOfGames) {
        for (int i = 0; i < numberOfGames; i++) {
            if (i % 2 == 0) {
                scheduleRepository.addNewScheduledGame(asList(playerA, playerB));
            } else {
                scheduleRepository.addNewScheduledGame(asList(playerB, playerA));
            }
        }
    }
}
