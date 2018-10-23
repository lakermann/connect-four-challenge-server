package ch.akermann.connectfourchallenge.server;

import ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell;

public class TestData {

    public static Cell[][] EMPTY_BOARD = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
    };
    public static Cell[][] FULL_BOARD_DRAW = {
            {Cell.RED, Cell.RED, Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED},
            {Cell.RED, Cell.YELLOW, Cell.RED, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED},
            {Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW}
    };
    public static Cell[][] FULL_BOARD_RED_WINNER = {
            {Cell.RED, Cell.RED, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED},
            {Cell.RED, Cell.YELLOW, Cell.RED, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED},
            {Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.RED, Cell.YELLOW}
    };
    public static Cell[][] HORIZONTAL_RED_WINNER = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.RED, Cell.RED, Cell.RED, Cell.RED, Cell.EMPTY, Cell.EMPTY}
    };
    public static Cell[][] VERTICAL_RED_WINNER = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
    };
    public static Cell[][] ASCENDING_DIAGONAL_RED_WINNER = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.YELLOW, Cell.YELLOW},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.RED, Cell.YELLOW, Cell.YELLOW, Cell.YELLOW}
    };
    public static Cell[][] DESCENDING_DIAGONAL_RED_WINNER = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.YELLOW, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.YELLOW, Cell.YELLOW, Cell.YELLOW, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
    };

    private TestData() {
        //only static variables
    }

}
