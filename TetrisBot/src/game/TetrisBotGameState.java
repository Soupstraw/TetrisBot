package game;

import java.util.ArrayList;
import java.util.Arrays;

import game.tetromino.Tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameState {

    // Rating multipliers for game state factors
	// Parameters taken from https://codemyroad.wordpress.com/2013/04/14/tetris-ai-the-near-perfect-player/
    private static final float ROUGHNESS_RATING_MULTIPLIER = -0.18f;
    private static final float ROWS_RATING_MULTIPLIER = 0.76f;
    private static final float HOLES_RATING_MULTIPLIER = -0.36f;
    private static final float AGGREGATE_HEIGHT_RATING_MULTIPLIER = -0.51f;

    private GameBoard board;
    private Tetromino currentTetromino;

    public TetrisBotGameState(){
    	this.board = new GameBoard();
    }
    
    public TetrisBotGameState(GameBoard gameBoard, Tetromino tetromino){
    	this.board = gameBoard;
    	this.currentTetromino = tetromino;
    }
    
    // Try every possible move using commands left, right, rotate and fastdrop
    public ArrayList<BotCommand> findBestMove(){
    	
    	ArrayList<BotCommand> bestSequence = null;
    	float bestScore = Float.NEGATIVE_INFINITY;
    	
    	// Drop with all possible rotations
    	for(int r = 0; r < 4; r++){
    		// Drop from all possible x coordinates
    		for(int x = -getBoard().getWidth(); x < getBoard().getWidth(); x++){
    			
    			//System.out.println("Resetting board..");
    			GameBoard testBoard = board.clone();
    			Tetromino testTetromino = currentTetromino.clone();
	    		ArrayList<BotCommand> moves = new ArrayList<>();
	    		
	    		// Add r rotation commands and rotate test tetromino
	    		for(int i = 0; i < r; i++){
	    			moves.add(BotCommand.ROTATE);
	    			testTetromino.rotateClockwise();
	    		}
	    		
				// Add x move right commands and move test tetromino
	    		for(int i = 0; i < x; i++){
	    			moves.add(BotCommand.RIGHT);
	    			testTetromino.moveRight();
	    		}
	    		
	    		// Add x move left commands and move test tetromino
	    		for(int i = 0; i > x; i--){
	    			moves.add(BotCommand.LEFT);
	    			testTetromino.moveLeft();
	    		}
	    		
	    		// Test whether block collides with anything
	    		if(!testBoard.checkTetrominoCollision(testTetromino)){
	        		// Use fast drop for now
	        		moves.add(BotCommand.FAST_DROP);
	        		testBoard.fastDrop(testTetromino);
	        		testBoard.freezeTetromino(testTetromino);
	        		
	        		//System.out.println(testBoard);
	        		
	        		float score = calculateBoardRating(testBoard);
	        		
	        		//System.out.println(score);
	        		
	        		if(score > bestScore){
	        			bestScore = score;
	        			bestSequence = moves;
	        		}
	    		}
        	}
    	}
    	
    	//System.out.println("Best score: " + bestScore);
    	return bestSequence;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public void setCurrentTetromino(Tetromino currentTetromino) {
        this.currentTetromino = currentTetromino;
    }

    private float calculateBoardRating(GameBoard gb){
        // Count how many empty blocks are covered by at least one filled block
        int holes = 0;
        for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
            boolean covered = false;
            for(int y = 0; y < GameBoard.BOARD_HEIGHT; y++){
                if(gb.getBlock(x, y) == true){
                    covered = true;
                }else if(covered){
                    holes++;
                }
            }
        }
        //System.out.println("Holes score: " + holes * HOLES_RATING_MULTIPLIER);

        // Count the number of filled rows
        int rows = 0;
        for(int y = 0; y < GameBoard.BOARD_HEIGHT; y++){
            rows++;
            for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
                if(!gb.getBlock(x, y)){
                    rows--;
                    break;
                }
            }
        }

        // Generate a histogram for column heights
        int[] histogram = new int[GameBoard.BOARD_WIDTH];
        for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
            int height = 0;
            for(int y = GameBoard.BOARD_HEIGHT - 1; y >= 0; y--){
                if(gb.getBlock(x, y)){
                    height = GameBoard.BOARD_HEIGHT - y;
                }
            }
            histogram[x] = height;
        }

        // Calculate the roughness of the histogram
        float roughness = 0;
        for(int x = 1; x < GameBoard.BOARD_WIDTH; x++){
            roughness += Math.abs(histogram[x] - histogram[x-1]);
        }
        
        // Calculate aggregate height of the board
        float height = 0;
        for(int i = 0; i < histogram.length; i++){
        	height += histogram[i];
        }

        return 
        		roughness * ROUGHNESS_RATING_MULTIPLIER + 
        		rows * ROWS_RATING_MULTIPLIER + 
        		holes * HOLES_RATING_MULTIPLIER + 
        		height * AGGREGATE_HEIGHT_RATING_MULTIPLIER;
    }

	public GameBoard getBoard() {
		return board;
	}
	
	public void setBoard(GameBoard board){
		this.board = board;
	}
}
