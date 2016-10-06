package game;

import game.tetromino.Tetromino;
import game.tetromino.TetrominoBuilder;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameTest {
    public static void main(String[] args){

        TetrominoBuilder tb = new TetrominoBuilder();

        Tetromino t = tb.getTetromino(TetrominoBuilder.TetrominoShape.T_J);
        System.out.println(t);

        // Test rotation
        t.rotateClockwise();
        System.out.println(t);
    }
}
