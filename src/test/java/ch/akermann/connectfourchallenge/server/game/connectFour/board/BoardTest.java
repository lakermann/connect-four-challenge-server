package ch.akermann.connectfourchallenge.server.game.connectFour.board;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardTest {

    @Test
    public void connectedFour_emptyBoard() {
        Board board = new Board(6, 7);

        assertThat(board.getRows(), is(6));
        assertThat(board.getColumns(), is(7));

        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < 6; row++) {
                assertThat(board.getCell(column, row), is(Cell.EMPTY));
            }
        }
    }

    @Test
    public void dropDisc() {
        Board board = new Board(6, 7);

        board.dropDisc(Disc.RED, 0);
        board.dropDisc(Disc.RED, 0);
        board.dropDisc(Disc.YELLOW, 6);

        assertThat(board.getCell(0, 5), is(Cell.RED));
        assertThat(board.getCell(0, 4), is(Cell.RED));
        assertThat(board.getCell(6, 5), is(Cell.YELLOW));
    }

    @Test(expected = BoardInvalidMoveException.class)
    public void dropDisc_columnToSmall() {
        Board board = new Board(6, 7);

        board.dropDisc(Disc.RED, -1);
    }

    @Test(expected = BoardInvalidMoveException.class)
    public void dropDisc_columnToLarge() {
        Board board = new Board(6, 7);

        board.dropDisc(Disc.RED, 7);
    }

    @Test(expected = BoardInvalidMoveException.class)
    public void dropDisc_columnIsFull() {
        Board board = new Board(6, 7);

        board.dropDisc(Disc.RED, 1);
        board.dropDisc(Disc.YELLOW, 1);
        board.dropDisc(Disc.RED, 1);
        board.dropDisc(Disc.YELLOW, 1);
        board.dropDisc(Disc.RED, 1);
        board.dropDisc(Disc.YELLOW, 1);
        board.dropDisc(Disc.RED, 1);
    }

    @Test
    public void getCells() {
        Board board = new Board(6, 7);

        Cell[][] cells = board.getCells();

        assertThat(cells.length, is(6));
        assertThat(cells[0].length, is(7));
    }

    @Test
    public void isFull_emptyBoard() {
        Board board = new Board(6, 7);

        boolean isFull = board.isFull();

        assertThat(isFull, is(false));
    }

    @Test
    public void isFull() {
        Board board = new Board(6, 7);
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                board.dropDisc(Disc.YELLOW, column);
            }
        }

        boolean isFull = board.isFull();

        assertThat(isFull, is(true));
    }

}