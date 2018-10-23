package ch.akermann.connectfourchallenge.server.game.connectFour.board;

import java.util.Arrays;
import java.util.stream.IntStream;

import static ch.akermann.connectfourchallenge.server.game.connectFour.board.Cell.EMPTY;

public class Board {

    private Cell[][] cells;

    public Board(Cell[][] cells){
        this.cells = cells;
    }

    public Board(int rows, int columns) {
        cells = new Cell[rows][columns];
        for (Cell[] row : cells) {
            Arrays.fill(row, EMPTY);
        }
    }

    public Cell getCell(int column, int row) {
        return cells[row][column];

    }

    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void dropDisc(Disc disc, int column) {
        if ((column < 0 || column > getColumns()-1)) {
            throw new BoardInvalidMoveException("Column is out of bound");
        }
        if (isColumnFull(column)) {
            throw new BoardInvalidMoveException("Column is already full");
        }
        for (int i = getRows() - 1; i >= 0; i -= 1) {
            if (cells[i][column ].equals(EMPTY)) {
                cells[i][column] = disc.getCell();
                break;
            }
        }
    }

    private boolean isColumnFull(int column) {
        return !cells[0][column].equals(EMPTY);
    }

    public boolean isFull() {
        int topRow = 0;
        return IntStream.range(0, getColumns())
                .allMatch(c -> getCell(c, topRow) != EMPTY);
    }
}