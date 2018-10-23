package ch.akermann.connectfourchallenge.server.game.schedule;

import org.junit.Test;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ScheduledGameTest {

    @Test
    public void scheduledGame_noPlayers() {
        UUID gameId = fromString("00000000-0000-0000-0000-000000000000");
        ScheduledGame scheduledGame = new ScheduledGame(gameId, 1);

        assertThat(scheduledGame.getId(), is(fromString("00000000-0000-0000-0000-000000000000")));
        assertThat(scheduledGame.getPlayerIds(), is(empty()));
        assertThat(scheduledGame.containsPlayerId("Alice"), is(false));
        assertThat(scheduledGame.hasEnoughPlayers(), is(false));
    }

    @Test
    public void scheduledGame_enoughPlayers() {
        UUID gameId = fromString("00000000-0000-0000-0000-000000000000");
        ScheduledGame scheduledGame = new ScheduledGame(gameId, 1);
        scheduledGame.addPlayerId("Alice");

        assertThat(scheduledGame.getId(), is(fromString("00000000-0000-0000-0000-000000000000")));
        assertThat(scheduledGame.getPlayerIds(), is(singletonList("Alice")));
        assertThat(scheduledGame.containsPlayerId("Alice"), is(true));
        assertThat(scheduledGame.hasEnoughPlayers(), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void scheduledGame_samePlayerTwice_throwsException() {
        UUID gameId = fromString("00000000-0000-0000-0000-000000000000");
        ScheduledGame scheduledGame = new ScheduledGame(gameId, 2);
        scheduledGame.addPlayerId("Alice");
        scheduledGame.addPlayerId("Alice");
    }

    @Test(expected = RuntimeException.class)
    public void scheduledGame_tooMuchPlayers_throwsException() {
        UUID gameID = fromString("00000000-0000-0000-0000-000000000000");
        ScheduledGame scheduledGame = new ScheduledGame(gameID, 1);
        scheduledGame.addPlayerId("Alice");
        scheduledGame.addPlayerId("Bob");
    }

}