package ch.akermann.connectfourchallenge.server.game.connectFour.board;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class BoardInvalidMoveException extends RuntimeException{

    BoardInvalidMoveException(String message){
        super(message);
    }

}