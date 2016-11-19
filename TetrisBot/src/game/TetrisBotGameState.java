package game;

import java.util.ArrayList;

import game.tetromino.Tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameState {

    // Rating multipliers for game state factors
    private static final float SMOOTHNESS_RATING_MULTIPLIER = -1.0f;
    private static final float ROWS_RATING_MULTIPLIER = 1.0f;
    private static final float HOLES_RATING_MULTIPLIER = -10.0f;

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
            for(int y = 0; y < GameBoard.BOARD_HEIGHT; y++){
                if(gb.getBlock(x, y)){
                    height = y;
                }
            }
            histogram[x] = height;
        }

        // Calculate the smoothness of the histogram
        float smoothness = 0;
        for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
            float sum = 0;
            int n = 1;
            int height = histogram[x];
            if(x > 0){
                n++;
                sum += Math.abs(height - histogram[x-1]);
            }
            if(x < GameBoard.BOARD_WIDTH - 1){
                n++;
                sum += Math.abs(height - histogram[x+1]);
            }
            smoothness += sum / n;
        }

        return smoothness * SMOOTHNESS_RATING_MULTIPLIER + rows * ROWS_RATING_MULTIPLIER + holes * HOLES_RATING_MULTIPLIER;
    }

	public GameBoard getBoard() {
		return board;
	}
	
	public void setBoard(GameBoard board){
		this.board = board;
	}
}
