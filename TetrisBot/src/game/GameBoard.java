package game;

import game.tetromino.Tetromino;

public class GameBoard extends Object{
	
	// Board dimensions
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;

    private boolean[][] board;

    public GameBoard() {
        board = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    }
    
    protected void freezeTetromino(Tetromino tet){
    	for(int i = 0; i < tet.getWidth(); i++){
    		for(int j = 0; j < tet.getHeight(); j++){
    			if(tet.getBlock(i, j) == 1){
    				try{
    					setBlock(i + tet.getX(), j + tet.getY(), true);
    				}catch(ArrayIndexOutOfBoundsException ex){
    					// Ignore, we don't need to worry about it
    				}
    			}
    		}
    	}
    }

    protected boolean checkTetrominoCollision(Tetromino tet){
    	for(int i = 0; i < tet.getWidth(); i++){
    		for(int j = 0; j < tet.getHeight(); j++){
    			if(tet.getBlock(i, j) == 1 && getBlock(i + tet.getX(), j + tet.getY())){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    protected void fastDrop(Tetromino tet){
    	while(!checkTetrominoCollision(tet)){
    		tet.moveDown();
    	}
    	tet.moveUp();
    }
    
    public boolean getBlock(int x, int y) {
    	if(x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT){
    		return true;
    	}else if(y < 0){
    		return false;
    	}
        return board[x][y];
    }

    public void setBlock(int x, int y, boolean val) {
        board[x][y] = val;
    }
    
    public int getWidth(){
    	return BOARD_WIDTH;
    }
    
    public int getHeight(){
    	return BOARD_HEIGHT;
    }
    
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	for(int y = 0; y < BOARD_HEIGHT; y++){
    		for(int x = 0; x < BOARD_WIDTH; x++){
    			if(board[x][y]){
    				sb.append("X");
    			}else{
    				sb.append(".");
    			}
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }
    
    public GameBoard clone(){
    	GameBoard gb = new GameBoard();
    	gb.board = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    	for(int y = 0; y < BOARD_HEIGHT; y++){
    		for(int x = 0; x < BOARD_WIDTH; x++){
    			gb.board[x][y] = board[x][y];
    		}
    	}
    	return gb;
    }
}
