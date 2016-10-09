package game;

import game.tetromino.Tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameState {

    // Board dimensions
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;

    // Rating multipliers for game state factors
    private static final float SMOOTHNESS_RATING_MULTIPLIER = 1.0f;
    private static final float ROWS_RATING_MULTIPLIER = 1.0f;
    private static final float HOLES_RATING_MULTIPLIER = -1.0f;

    private class GameBoard {

        private boolean[][] board;

        public GameBoard() {
            board = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        }

        public boolean getBlock(int x, int y) {
            try {
                return board[x][y];
            } catch(ArrayIndexOutOfBoundsException ex) {
                return true;
            }
        }

        public void setBlock(int x, int y, boolean val) {
            board[x][y] = val;
        }
    }

    private GameBoard board;

    private Tetromino currentTetromino;
    private Tetromino nextTetromino;

    public TetrisBotGameState(){

    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public void setCurrentTetromino(Tetromino currentTetromino) {
        this.currentTetromino = currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    public void setNextTetromino(Tetromino nextTetromino) {
        this.nextTetromino = nextTetromino;
    }

    public float calculateBoardRating(){
        // Count how many empty blocks are covered by at least one filled block
        int holes = 0;
        for(int x = 0; x < BOARD_WIDTH; x++){
            boolean covered = false;
            for(int y = BOARD_HEIGHT - 1; y >= 0; y--){
                if(board.getBlock(x, y) == true){
                    covered = true;
                }else if(covered){
                    holes++;
                }
            }
        }

        // Count the number of filled rows
        int rows = 0;
        for(int y = 0; y < BOARD_HEIGHT; y++){
            rows++;
            for(int x = 0; x < BOARD_WIDTH; x++){
                if(!board.getBlock(x, y)){
                    rows--;
                    break;
                }
            }
        }

        // Generate a histogram for column heights
        int[] histogram = new int[BOARD_WIDTH];
        for(int x = 0; x < BOARD_WIDTH; x++){
            int height = 0;
            for(int y = 0; y < BOARD_HEIGHT; y++){
                if(board.getBlock(x, y){
                    height = y;
                }
            }
            histogram[x] = height;
        }

        // Calculate the smoothness of the histogram
        float smoothness = 0;
        for(int x = 0; x < BOARD_WIDTH; x++){
            float sum = 0;
            int n = 1;
            int height = histogram[x];
            if(x > 0){
                n++;
                sum += Math.abs(height - histogram[x-1]);
            }
            if(x < BOARD_WIDTH - 1){
                n++;
                sum += Math.abs(height - histogram[x+1]);
            }
            smoothness += sum / n;
        }

        return smoothness * SMOOTHNESS_RATING_MULTIPLIER + rows * ROWS_RATING_MULTIPLIER + holes * HOLES_RATING_MULTIPLIER;
    }
}
