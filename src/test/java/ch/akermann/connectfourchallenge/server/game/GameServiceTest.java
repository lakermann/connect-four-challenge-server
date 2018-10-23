package ch.akermann.connectfourchallenge.server.game;

import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduleRepository;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


public class GameServiceTest {

    @Test
    public void join_autoRegister() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        gameService.join("Alice");

        assertThat(gameService.getScheduledGames().size(), is(1));
    }

    @Test
    public void join_manualRegister() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, false, 0);

        gameService.join("Alice");

        assertThat(gameService.getScheduledGames(), empty());
    }

    @Test
    public void getOrCreateGameId_noGameScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        assertThat(gameService.getOrCreateGameId("Alice").isPresent(), is(false));

    }

    @Test
    public void getOrCreateGameId_notEnoughPlayer() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        gameService.register("Alice");

        assertThat(gameService.getOrCreateGameId("Alice").isPresent(), is(false));
    }

    @Test
    public void getOrCreateGameId() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);
        gameService.addScheduledGame("Alice", "Bob", 1);

        Optional<UUID> gameId = gameService.getOrCreateGameId("Alice");

        assertThat(gameService.getGames().size(), is(1));
        assertThat(gameId.isPresent(), is(true));
        ConnectFour connectFour = gameService.getGame(gameId.get());
        assertThat(connectFour, is(notNullValue()));
        assertThat(connectFour.getStartTime(), is(notNullValue()));
        assertThat(connectFour.getPlayers().size(), is(2));
        assertThat(connectFour.getPlayers().get(0).getPlayerId(), is("Alice"));
        assertThat(connectFour.getPlayers().get(1).getPlayerId(), is("Bob"));
    }

    @Test(expected = GameNotFoundException.class)
    public void getGame_unknownGameId() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        gameService.getGame(UUID.randomUUID());
    }

    @Test
    public void dropDisc() throws InterruptedException {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, false, 0);
        gameService.addScheduledGame("Alice", "Bob", 1);
        Optional<UUID> gameId = gameService.join("Alice");

        assertThat(gameId.isPresent(), is(true));
        ConnectFour game = gameService.dropDisc(gameId.get(), "Alice", 1);

        assertThat(game, is(notNullValue()));
        assertThat(game.getBoard(), is(notNullValue()));
        assertThat(game.getBoard().getCell(1, 5), not(Cell.EMPTY));
    }

    @Test
    public void dropDisc_wait() throws InterruptedException {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, false, 1000);
        gameService.addScheduledGame("Alice", "Bob", 1);
        Optional<UUID> gameId = gameService.join("Alice");

        assertThat(gameId.isPresent(), is(true));
        LocalDateTime startTime = LocalDateTime.now();
        gameService.dropDisc(gameId.get(), "Alice", 1);
        gameService.dropDisc(gameId.get(), "Bob", 2);
        gameService.dropDisc(gameId.get(), "Alice", 1);
        gameService.dropDisc(gameId.get(), "Bob", 2);
        gameService.dropDisc(gameId.get(), "Alice", 1);
        gameService.dropDisc(gameId.get(), "Bob", 2);
        gameService.dropDisc(gameId.get(), "Alice", 1);
        LocalDateTime endTime = LocalDateTime.now();

        long seconds = startTime.until(endTime, ChronoUnit.MILLIS);
        assertThat(seconds, greaterThan(1000L));
        assertThat(gameService.getScheduledGames(), is(empty()));
        assertThat(gameService.getStats().isEmpty(), is(false));
    }

    @Test
    public void getCurrentGame_noGameScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        Optional<UUID> currentGame = gameService.getCurrentGame();

        assertThat(currentGame.isPresent(), is(false));
    }

    @Test
    public void getCurrentGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, false, 0);
        gameService.addScheduledGame("Alice", "Bob", 1);

        Optional<UUID> currentGame = gameService.getCurrentGame();

        assertThat(currentGame.isPresent(), is(true));
    }


    @Test
    public void addScheduledGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);

        gameService.addScheduledGame("Alice", "Bob", 2);

        assertThat(gameService.getScheduledGames().size(), is(2));
        List<String> firstGamePlayers = gameService.getScheduledGames().get(0).getPlayerIds();
        assertThat(firstGamePlayers.get(0), is("Alice"));
        assertThat(firstGamePlayers.get(1), is("Bob"));
        List<String> secondGamePlayers = gameService.getScheduledGames().get(1).getPlayerIds();
        assertThat(secondGamePlayers.get(0), is("Bob"));
        assertThat(secondGamePlayers.get(1), is("Alice"));
    }

    @Test
    public void deleteScheduledGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);
        GameService gameService = new GameService(scheduleRepository, true, 0);
        gameService.addScheduledGame("Alice", "Bob", 1);
        Optional<UUID> gameId = gameService.join("Alice");

        assertThat(gameId.isPresent(), is(true));
        gameService.deleteScheduledGame(gameId.get());

        assertThat(gameService.getScheduledGames(), empty());
    }
}