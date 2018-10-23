package ch.akermann.connectfourchallenge.server.game.connectFour.board;

public enum Disc {
    RED(Cell.RED),
    YELLOW(Cell.YELLOW);

    private final Cell cell;

    Disc(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}