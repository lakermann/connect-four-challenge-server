package ch.akermann.connectfourchallenge.server.game.schedule;

import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ScheduleRepositoryTest {

    @Test
    public void registerPlayer() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);

        scheduleRepository.registerPlayer("Alice");

        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();
        assertThat(scheduledGames.size(), is (1));
        assertThat(scheduledGames.get(0).getId(), is (notNullValue()));
        assertThat(scheduledGames.get(0).getPlayerIds(), is (singletonList("Alice")));
    }

    @Test
    public void registerPlayer_multiplePlayers() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);

        scheduleRepository.registerPlayer("Alice");
        scheduleRepository.registerPlayer("Bob");

        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();
        assertThat(scheduledGames.size(), is (1));
        assertThat(scheduledGames.get(0).getPlayerIds(), is (asList("Alice","Bob")));
    }

    @Test
    public void registerPlayer_samePlayerTwice() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(2);

        scheduleRepository.registerPlayer("Alice");
        scheduleRepository.registerPlayer("Alice");

        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();
        assertThat(scheduledGames.size(), is (1));
        assertThat(scheduledGames.get(0).getId(), is (notNullValue()));
        assertThat(scheduledGames.get(0).getPlayerIds(), is (singletonList("Alice")));
    }

    @Test
    public void getNextScheduledGame_noGameScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);

        Optional<ScheduledGame> scheduledGame = scheduleRepository.getNextScheduledGame("Alice");

        assertThat(scheduledGame.isPresent(), is(false));
    }

    @Test
    public void getNextScheduledGame_noGameForPlayerScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);
        scheduleRepository.addNewScheduledGame(singletonList("Bob"));

        Optional<ScheduledGame> scheduledGame = scheduleRepository.getNextScheduledGame("Alice");

        assertThat(scheduledGame.isPresent(), is(false));
    }

    @Test
    public void getNextScheduledGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);
        scheduleRepository.addNewScheduledGame(singletonList("Alice"));

        Optional<ScheduledGame> scheduledGame = scheduleRepository.getNextScheduledGame("Alice");

        assertThat(scheduledGame.isPresent(), is(true));
    }

    @Test
    public void getCurrentGame_noGameScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);

        Optional<ScheduledGame> currentGame = scheduleRepository.getCurrentGame();

        assertThat(currentGame.isPresent(), is(false));
    }

    @Test
    public void getCurrentGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);
        scheduleRepository.addNewScheduledGame(singletonList("Alice"));

        Optional<ScheduledGame> currentGame = scheduleRepository.getCurrentGame();

        assertThat(currentGame.isPresent(), is(true));
    }

    @Test
    public void removeGame_noGameScheduled() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);

        scheduleRepository.removeGame(UUID.randomUUID());
        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();

        assertThat(scheduledGames, is(empty()));
    }

    @Test
    public void removeGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);
        scheduleRepository.addNewScheduledGame(singletonList("Alice"));
        Optional<ScheduledGame> currentGame = scheduleRepository.getCurrentGame();

        assertThat(currentGame.isPresent(), is(true));
        scheduleRepository.removeGame(currentGame.get().getId());
        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();

        assertThat(scheduledGames, is(empty()));
    }

    @Test
    public void addScheduledGame() {
        ScheduleRepository scheduleRepository = new ScheduleRepository(1);

        scheduleRepository.addNewScheduledGame(singletonList("Alice"));
        List<ScheduledGame> scheduledGames = scheduleRepository.getScheduledGames();

        assertThat(scheduledGames.size(), is(1));
        assertThat(scheduledGames.get(0).getId(), is(notNullValue()));
        assertThat(scheduledGames.get(0).getPlayerIds(), is(singletonList("Alice")));
    }
}