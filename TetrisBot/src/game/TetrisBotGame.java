package game;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGame {

    // Board dimensions
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;

    private abstract class Tetromino {

        private boolean[][] matrix;

        protected Tetromino(boolean[][] matrix){
            this.matrix = matrix;
        }

        public final void rotateClockwise(){
            boolean[][] newMatrix = new boolean[matrix[0].length][matrix.length];

            for(int y = 0; y < matrix[0].length; y++){
                for(int x = 0; x < matrix.length; x++){
                    newMatrix[newMatrix.length-y][x] = matrix[x][y];
                }
            }
        }
    }

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
