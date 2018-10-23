package ch.akermann.connectfourchallenge.server.api.ui;

public class CountResponse {

    private final int draw;
    private final int loss;
    private final int win;

    private CountResponse(int draw, int loss, int win) {
        this.draw = draw;
        this.loss = loss;
        this.win = win;
    }

    @SuppressWarnings("unused")
    public int getDraw() {
        return draw;
    }

    @SuppressWarnings("unused")
    public int getLoss() {
        return loss;
    }

    @SuppressWarnings("unused")
    public int getWin() {
        return win;
    }

    static CountResponse toCountResponse(ch.akermann.connectfourchallenge.server.game.connectFour.ranking.Count count){
        return new CountResponse(
                count.getDraw(),
                count.getLoss(),
                count.getWin());

    }
}