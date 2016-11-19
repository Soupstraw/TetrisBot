package game;

import java.util.ArrayList;

import game.tetromino.Tetromino;

/**
 * Created by Joosep on 06-Oct-16.
 */
public class TetrisBotGameState {

    // Rating multipliers for game state factors
    private static final float SMOOTHNESS_RATING_MULTIPLIER = 1.0f;
    private static final float ROWS_RATING_MULTIPLIER = 1.0f;
    private static final float HOLES_RATING_MULTIPLIER = -1.0f;

    private GameBoard board;

    private Tetromino currentTetromino;

    public TetrisBotGameState(){
    	this.board = new GameBoard();
    }
    
    // Try every possible move using commands left, right, rotate and fastdrop
    public ArrayList<BotCommand> findBestMove(){
    	Tetromino testTetromino = currentTetromino.clone();
    	
    	ArrayList<BotCommand> bestSequence = null;
    	float bestScore = Float.NEGATIVE_INFINITY;
    	
    	for(int r = 0; r < 4; r++){
    		ArrayList<BotCommand> moves = new ArrayList<>();
    		// Add 1 - 4 rotation commands and rotate test tetromino
    		for(int i = 0; i < r; i++){
    			moves.add(BotCommand.ROTATE);
    			testTetromino.rotateClockwise();
    		}
    		
    		// Drop from all possible x coordinates
    		for(int x = -testTetromino.getWidth(); x < getBoard().getWidth(); x++){
    			// Add x move right commands and move test tetromino
        		for(int i = 0; i < x; i++){
        			moves.add(BotCommand.RIGHT);
        			testTetromino.moveRight();
        		}
        		
        		// Test whether block collides with anything
        		if(!checkTetrominoCollision(testTetromino)){
	        		// Use fast drop for now
	        		moves.add(BotCommand.FAST_DROP);
	        		fastDrop(testTetromino);
        		}
        	}
    	}
    	
    	return bestSequence;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public void setCurrentTetromino(Tetromino currentTetromino) {
        this.currentTetromino = currentTetromino;
    }

    private float calculateBoardRating(){
        // Count how many empty blocks are covered by at least one filled block
        int holes = 0;
        for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
            boolean covered = false;
            for(int y = GameBoard.BOARD_HEIGHT - 1; y >= 0; y--){
                if(getBoard().getBlock(x, y) == true){
                    covered = true;
                }else if(covered){
                    holes++;
                }
            }
        }

        // Count the number of filled rows
        int rows = 0;
        for(int y = 0; y < GameBoard.BOARD_HEIGHT; y++){
            rows++;
            for(int x = 0; x < GameBoard.BOARD_WIDTH; x++){
                if(!getBoard().getBlock(x, y)){
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
                if(getBoard().getBlock(x, y)){
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
    
    protected boolean checkTetrominoCollision(Tetromino tet){
    	for(int i = 0; i < tet.getWidth(); i++){
    		for(int j = 0; j < tet.getHeight(); j++){
    			if(tet.getBlock(i, j) == 1 && getBoard().getBlock(i + tet.getX(), j + tet.getY())){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    protected void freezeTetromino(Tetromino tet){
    	for(int i = 0; i < tet.getWidth(); i++){
    		for(int j = 0; j < tet.getHeight(); j++){
    			if(tet.getBlock(i, j) == 1){
    				try{
    					getBoard().setBlock(i + tet.getX(), j + tet.getY(), true);
    				}catch(ArrayIndexOutOfBoundsException ex){
    					// Ignore, we don't need to worry about it
    				}
    			}
    		}
    	}
    }
    
    protected void fastDrop(Tetromino tet){
    	while(!checkTetrominoCollision(tet)){
    		tet.moveDown();
    	}
    	tet.moveUp();
    }

	public GameBoard getBoard() {
		return board;
	}
}
