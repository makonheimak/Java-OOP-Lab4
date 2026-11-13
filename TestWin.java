
public class TestWin {
    public static void main(String[] args) throws Exception {
        TicTacToeGame game = new TicTacToeGame();
        game.setSampleWinningGame();
        game.setVisible(true);
        Thread.sleep(6000);
        System.exit(0);
    }
}
