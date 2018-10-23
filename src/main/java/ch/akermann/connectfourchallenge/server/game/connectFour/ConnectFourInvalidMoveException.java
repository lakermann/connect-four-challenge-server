package ch.akermann.connectfourchallenge.server.game.connectFour;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ConnectFourInvalidMoveException extends RuntimeException{

    ConnectFourInvalidMoveException(String message){
        super(message);
    }

}
