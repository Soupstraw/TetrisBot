import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class TetrisBotBoardRecognition {
	
	int board[][] = new int[10][20];
	int decodedBoard[][] = new int[10][20];
	
	/**
	 * Usage guide:
	 * Create a new Object and pass it the game window rectangle:
	 * 				TetrisBotBoardRecognition rec = new TetrisBotBoardRecognition(game);
	 * Analyze the game state:
	 * 				rec.analyzeGameState();
	 * Now get the game state ( getGameState() ) and get the board data ( getBoardData() )
	 * 
	 * */
	
	public enum GameState{
		//menu pages
		MAIN_MENU, BATTLE_2P_PLUS, GAME_ONGOING, 
		//Advertisement
		AD_WAIT_TIME_FINISHED,
		//Default
		UNKNOWN
	}
	
	private BufferedImage game;
	private Rectangle gameWindow;
	private Robot robot;
	private GameState currentGameState = GameState.UNKNOWN;
	
	public TetrisBotBoardRecognition(Rectangle gameWindow) {
		this.gameWindow = gameWindow;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		for(int x = 0; x < 10; x++){
			for(int y = 0; y < 20; y++){
				board[x][y] = 0;
				decodedBoard[x][y] = 0;	
			}
		}
	}
	
	public GameState getGameState(){
		return currentGameState;
	}
	
	public int[][] getBoardData(){
		return decodedBoard;
	}
	
	public void analyzeGameState(){
		if(gameWindow != null){
			game = robot.createScreenCapture(gameWindow);
		}else{
			System.err.println("TetrisBotBoardRecognition.java -> Game window location is not set!");
			return;
		}
		
        //Scan the board
		for(int y = 0; y < 20; y++){
    		//System.out.println("\nLine " + y + ": ");
    		for(int x = 0; x < 10; x++){
        		int xpos = 92 + 9 + x*18;
        		int ypos = 152 + 10 + y*18;
        		
        		board[x][y] = game.getRGB(xpos, ypos);
        	}
        }
		
		//Ad checking
		//System.out.println(new Color(game.getRGB(377, 590)).toString());
		
		//determine the state of the game
		if(new Color(game.getRGB(324, 194)).equals(new Color(0, 0, 0))  &&  new Color(game.getRGB(324, 206)).equals(new Color(0, 0, 0))  &&  new Color(game.getRGB(304, 223)).equals(new Color(0, 0, 0))){
			currentGameState = GameState.BATTLE_2P_PLUS;
		}else if(new Color(game.getRGB(331, 588)).equals(new Color(26, 152, 255))){
			currentGameState = GameState.AD_WAIT_TIME_FINISHED;
		}else if(new Color(board[2][11]).equals(new Color(255, 129, 6))  ||  new Color(board[6][11]).equals(new Color(255, 129, 6))){
			currentGameState = GameState.MAIN_MENU;
		}else if((!(new Color(game.getRGB(324, 200)).equals(new Color(0, 0, 0)))  ||  new Color(game.getRGB(304, 223)).equals(new Color(0, 0, 0)))  &&  !(new Color(board[0][0]).equals(new Color(0, 0, 0)))){
			currentGameState = GameState.GAME_ONGOING;
		}else{
			currentGameState = GameState.UNKNOWN;
		}
		
		int undefined = 0;
		if(currentGameState == GameState.GAME_ONGOING){
			//decode the board
			for(int y = 0; y < 20; y++){
	    		for(int x = 0; x < 10; x++){
	        		Color c = new Color(board[x][y]);
	        		
	        		if(c.equals(new Color(43, 43, 43)) ||
	        		   c.equals(new Color(47, 47, 47)) ||
	        		   c.equals(new Color(77, 77, 77))){
	        			//this is the background of the board
	        			decodedBoard[x][y] = 0;
	        		}else if(c.equals(new Color(124, 212, 36))){
	        			//this is a green block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(255, 194, 37))){
	        			//this is a yellow block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(255, 126, 37))){
	        			//this is a orange block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(250, 50, 90))){
	        			//this is a red/purple block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(210, 76, 173))){
	        			//this is a violet block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(50, 190, 250))){
	        			//this is a light-blue block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(68, 100, 233))){
	        			//this is a dark-blue block
	        			decodedBoard[x][y] = 1;
	        		}else if(c.equals(new Color(188, 188, 188))){
	        			//this is concrete
	        			decodedBoard[x][y] = 2;
	        		}else{
	        			if(x != 0 && new Color(board[x-1][y]).equals(new Color(188, 188, 188))  ||  x != 9 && new Color(board[x+1][y]).equals(new Color(188, 188, 188))){
	        				//probably a blinking bomb
	        				decodedBoard[x][y] = 3;
	        			}else{
	        				decodedBoard[x][y] = -1;
	        				undefined++;
	        			}
	        		}
	        	}
	        }
		}
		if(undefined > 2*10){
			//Over 10% of blocks are not recognized, probably in an unknown state
			currentGameState = GameState.UNKNOWN;
			System.out.println("Jump to unknown!");
		}
	}
	
	public void drawDebugInformation(Graphics2D g2d){
		if(gameWindow == null)
			return;
		
		//Draw the board out of raw data
		for(int x = 0; x < 10; x++){
			for(int y = 0; y < 20; y++){
				g2d.setColor( new Color(board[x][y]) );
				g2d.fillRect(x*10 + gameWindow.width + 50, y*10+400, 10, 10);
			}
		}
		
		//Draw the board with processed data
		for(int x = 0; x < 10; x++){
			for(int y = 0; y < 20; y++){
				if(decodedBoard[x][y] == -1){
					g2d.setColor( Color.RED );
					//unknown
				}else if(decodedBoard[x][y] == 0){
					g2d.setColor( new Color(50, 50, 50) );
					//background
				}else if(decodedBoard[x][y] == 1){
					g2d.setColor( Color.GREEN );
					//block
				}else if(decodedBoard[x][y] == 2){
					g2d.setColor( Color.YELLOW );
					//concrete
				}else if(decodedBoard[x][y] == 3){
					g2d.setColor( Color.CYAN );
					//bomb
				}
				g2d.fillRect(x*10 + gameWindow.width + 170, y*10+400, 10, 10);
			}
		}
		
		//Draw labels
		int sidebarX = TetrisBotGUI.LEFT_SIDEBAR_WIDTH + gameWindow.width + 10;
        g2d.setColor( Color.BLACK );
		g2d.drawString("State: " + currentGameState.toString(), sidebarX, 190);
		g2d.drawString("Raw data", gameWindow.width + 70, 395);
		g2d.drawString("processed", gameWindow.width + 189, 395);
	}
}
