package ch.akermann.connectfourchallenge.server;

import ch.akermann.connectfourchallenge.server.game.GameService;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConnectFourServerApplication {

    private final static int NUMBER_OF_PLAYERS_PER_GAME = 2;

    public static void main(String[] args) {
        SpringApplication.run(ConnectFourServerApplication.class, args);
    }

    @Bean
    public GameService createConnectFourService(@Value("${autoJoining}") boolean isAutojoiningEnabled,
                                                @Value("${waitIfGameIsFinishedInMs}") long waitIfGameIsFinishedInMs) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(NUMBER_OF_PLAYERS_PER_GAME);
        return new GameService(scheduleRepository, isAutojoiningEnabled, waitIfGameIsFinishedInMs);
    }

}
