package ch.akermann.connectfourchallenge.server.game.connectFour.ranking;

import ch.akermann.connectfourchallenge.server.TestData;
import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.Player;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Board;
import ch.akermann.connectfourchallenge.server.game.connectFour.board.Disc;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class StatisticsRepositoryTest {

    @Test
    public void updateValue() {
        StatisticsRepository statisticsRepository = new StatisticsRepository();
        Board board = new Board(TestData.FULL_BOARD_DRAW);
        List<Player> players = Arrays.asList(
                new Player("Alice", Disc.RED),
                new Player("Bob", Disc.YELLOW)
        );
        ConnectFour connectFour = new ConnectFour(LocalDateTime.now(), UUID.randomUUID(), board, players);

        statisticsRepository.updateValue("Alice", connectFour);

        assertThat(statisticsRepository.getEntries().isEmpty(), is(false));
        assertThat(statisticsRepository.getEntries().get("Alice"), is(notNullValue()));
        assertThat(statisticsRepository.getEntries().get("Alice").getDraw(), is(1));
        assertThat(statisticsRepository.getEntries().get("Alice").getLoss(), is(0));
        assertThat(statisticsRepository.getEntries().get("Alice").getWin(), is(0));
    }
}