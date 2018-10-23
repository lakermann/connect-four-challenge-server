package ch.akermann.connectfourchallenge.server.game;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class GameNotFoundException extends RuntimeException{

    GameNotFoundException(String message){
        super(message);
    }

}
