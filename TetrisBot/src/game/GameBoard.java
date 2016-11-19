package game;

public class GameBoard {
	
	// Board dimensions
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;

    private boolean[][] board;

    public GameBoard() {
        board = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
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
}
