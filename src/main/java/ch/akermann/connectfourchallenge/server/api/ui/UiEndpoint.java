package ch.akermann.connectfourchallenge.server.api.ui;

import ch.akermann.connectfourchallenge.server.game.GameService;
import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import ch.akermann.connectfourchallenge.server.game.connectFour.ranking.Count;
import ch.akermann.connectfourchallenge.server.game.schedule.ScheduledGame;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ch.akermann.connectfourchallenge.server.api.ui.GameResponse.toGameResponse;
import static ch.akermann.connectfourchallenge.server.api.ui.GamesResponse.toGamesResponse;
import static ch.akermann.connectfourchallenge.server.api.ui.PlayersResponse.toPlayersResponse;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RequestMapping("/api/v1/ui/")
@RestController
public class UiEndpoint {

    private static final int MAX_GAMES_IN_GAMES_RESPONSE = 1000;
    private final GameService gameService;

    public UiEndpoint(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "Get players")
    @RequestMapping(value = "/players", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PlayersResponse> getPlayers() {
        Map<String, Count> players = gameService.getStats();
        return players.entrySet().stream()
                .map(g -> toPlayersResponse(g.getKey(), g.getValue()))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get current games")
    @RequestMapping(value = "/games/current/", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public CurrentGameResponse getCurrentGame() {
        Optional<UUID> currentGame = gameService.getCurrentGame();
        return currentGame
                .map(CurrentGameResponse::toCurrentGameResponse)
                .orElseGet(CurrentGameResponse::createEmptyCurrentGameResponse);
    }

    @ApiOperation(value = "Get game")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Game doesn't exist")})
    @RequestMapping(value = "/games/{gameId}", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public synchronized GameResponse getGame(@PathVariable("gameId") UUID gameId) {
        ConnectFour connectFour = gameService.getGame(gameId);
        return toGameResponse(connectFour);
    }

    @ApiOperation(value = "Get scheduled games")
    @RequestMapping(value = "/scheduled-games", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduledGameResponse> getScheduledGames() {
        List<ScheduledGame> schedulesGames = gameService.getScheduledGames();
        return schedulesGames.stream()
                .map(ScheduledGameResponse::toScheduledGameResponse)
                .collect(toList());
    }

    @ApiOperation(value = "Add scheduled games")
    @RequestMapping(value = "/scheduled-games", method = POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addScheduledGame(@RequestBody AddScheduledGameRequest addScheduledGameRequest) {
        gameService.addScheduledGame(
                addScheduledGameRequest.getPlayerA(),
                addScheduledGameRequest.getPlayerB(),
                addScheduledGameRequest.getNumberOfGames());
    }

    @ApiOperation(value = "Delete scheduled games")
    @RequestMapping(value = "/scheduled-games/{gameId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteScheduledGames(@PathVariable("gameId") UUID gameId) {
        gameService.deleteScheduledGame(gameId);
    }

    @ApiOperation(value = "Get games")
    @RequestMapping(value = "/games", method = GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GamesResponse> getGames() {
        Map<UUID, ConnectFour> games = gameService.getGames();
        return games.entrySet().stream()
                .map(g -> toGamesResponse(g.getKey(), g.getValue()))
                .sorted(comparing(GamesResponse::getStartTime).reversed())
                .limit(MAX_GAMES_IN_GAMES_RESPONSE)
                .collect(toList());
    }
}
