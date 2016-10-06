package game;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGame {

    // Board dimensions
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;

    private class GameBoard{

        private boolean[][] board;

        public GameBoard(){
            board = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        }


    }

    private GameBoard board;

    public TetrisBotGame(){

    }
}
