package game;

import game.tetromino.Tetromino;
import game.tetromino.TetrominoBuilder;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameTest {
    public static void main(String[] args){

    	TetrisBotGameState gameState = new TetrisBotGameState();
    	
        TetrominoBuilder tb = new TetrominoBuilder();

        Tetromino t = tb.buildTetromino(TetrominoBuilder.TetrominoShape.T_J);	// Change the parameter to test for different tetrominos
        System.out.println(t);

        // Test rotation
        t.rotateClockwise();
        System.out.println(t);
        
        // Check collision
        System.out.println(gameState.checkTetrominoCollision(t));
        
        // Print the board with tetromino
        gameState.freezeTetromino(t);
        System.out.println(gameState.getBoard());
    }
}
