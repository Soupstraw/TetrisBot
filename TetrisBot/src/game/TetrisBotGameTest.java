package game;

import game.tetromino.Tetromino;
import game.tetromino.TetrominoBuilder;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameTest {
    public static void main(String[] args){

    	//TetrisBotGameState gameState = new TetrisBotGameState();

        for(TetrominoBuilder.TetrominoShape shape : TetrominoBuilder.TetrominoShape.values()){
	        Tetromino t = TetrominoBuilder.buildTetromino(shape);	// Change the parameter to test for different tetrominos
	        System.out.println(t);
	
	        // Test rotation
	        t.rotateClockwise();
	        System.out.println(t);
        }
        
        /*
        // Check collision
        System.out.println(gameState.getBoard().checkTetrominoCollision(t));
        
        // Print the board with tetromino
        gameState.getBoard().fastDrop(t);
        gameState.getBoard().freezeTetromino(t);
        System.out.println(gameState.getBoard());
        
        gameState.setCurrentTetromino(tb.buildTetromino(TetrominoBuilder.TetrominoShape.T_4x1));
        gameState.findBestMove().forEach((BotCommand c) -> System.out.println(c));
        
        t = tb.buildTetromino(TetrominoBuilder.TetrominoShape.T_4x1);
        t.moveLeft();
        t.moveLeft();
        t.moveLeft();
        gameState.getBoard().fastDrop(t);
        gameState.getBoard().freezeTetromino(t);
        System.out.println(gameState.getBoard());
        */
    }
}
