package ch.akermann.connectfourchallenge.server.api.players;

import ch.akermann.connectfourchallenge.server.game.GameService;
import ch.akermann.connectfourchallenge.server.game.connectFour.ConnectFour;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static ch.akermann.connectfourchallenge.server.api.players.GameResponse.toGameResponse;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/api/v1/players/")
@RestController
public class PlayerEndpoint {

    private final GameService gameService;

    public PlayerEndpoint(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "Join a game")
    @RequestMapping(value = "/join", method = POST)
    @ResponseStatus(HttpStatus.OK)
    public synchronized JoinResponse join(@RequestBody JoinRequest joinRequest) {
        Optional<UUID> game = gameService.join(joinRequest.getPlayerId());
        return game
                .map(JoinResponse::toJoinResponse)
                .orElseGet(JoinResponse::createEmptyJointResponse);
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

    @ApiOperation(value = "Drop disc",
            notes = "The first column is column 0"
    )
    @RequestMapping(value = "/games/{gameId}", method = POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Game doesn't exist"),
            @ApiResponse(code = 400, message = "Column is out of bound, already filled or wrong player")})
    public synchronized GameResponse dropDisc(@PathVariable("gameId") UUID gameId,
                                              @ApiParam(required = true, name = "dropDisc") @RequestBody DropDiscRequest dropDiscRequest) throws InterruptedException {
        ConnectFour connectFour = gameService.dropDisc(gameId, dropDiscRequest.getPlayerId(), dropDiscRequest.getColumn());
        return toGameResponse(connectFour);
    }
}

