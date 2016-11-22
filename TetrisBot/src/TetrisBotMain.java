import java.awt.Rectangle;
import java.util.ArrayList;

import game.BotCommand;
import game.GameBoard;
import game.TetrisBotGameState;
import game.tetromino.Tetromino;
import game.tetromino.TetrominoBuilder;
import game.tetromino.TetrominoBuilder.TetrominoShape;


public class TetrisBotMain {
	
	TetrisBotBoardRecognition rec;
	
	public static void main(String args[]){
		new TetrisBotMain();
	}
	
	public TetrisBotMain(){
		/**
		 * 		OPEN THIS IN BROWSER: https://apps.facebook.com/tetris_battle
		 * */
				
		//Find the game
		TetrisBotGameWindowFinder windowFinder = new TetrisBotGameWindowFinder();
		Rectangle game = windowFinder.getGameWindowLocation();
		rec = new TetrisBotBoardRecognition(game);
		
		//Create the GUI
		TetrisBotGUI gui = new TetrisBotGUI(this);
				
		//Do something reasonable
		if(game == null){
			gui.setStatusMessage("Game window not found!");
		}else{
			//Overlay the game with the GUI
			gui.setStatusMessage("Window found");	
			gui.setLocationRectangle(game);
		}
		
		new Thread(){
			public void run() {
				TetrisBotGameState gameState = new TetrisBotGameState();
				TetrisBotRobot robot = new TetrisBotRobot();
				
				//this infinite loop is automatically exited when the JWindow is closed
				while(true){
					gui.panel.setStatusMessage("Analüüsin " + System.currentTimeMillis()%1000);
					rec.analyzeGameState();
					if(rec.getGameState() == TetrisBotBoardRecognition.GameState.GAME_ONGOING){
						int[][] brd = rec.getBoardData();
						GameBoard gb = new GameBoard();
						int highestBlockY = gb.getHeight()-1;
						boolean emptyLineFound = false;
						for(int y = gb.getHeight()-1; y >= 0; y--){
							int blocks = 0;
							for(int x = 0; x < gb.getWidth(); x++){
								boolean value = (brd[x][y] > 10  &&  brd[x][y] < 18)  &&  !emptyLineFound;
								gb.setBlock(x, y, value);
								if(value){
									blocks++;
								}
							}
							if(!emptyLineFound){
								highestBlockY = y;
							}
							if(blocks == 0){
								emptyLineFound = true;
							}
						}
						Tetromino plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_2x2);
						for(int y = highestBlockY-1; y >= 0; y--){
							for(int x = 0; x < gb.getWidth(); x++){
								boolean value = (brd[x][y] > 10  &&  brd[x][y] < 18);
								if(value){
									switch(brd[x][y]){
									case 11:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_S);
										break;
									case 12:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_2x2);
										break;
									case 13:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_L);
										break;
									case 14:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_Z);
										break;
									case 15:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_T);
										break;
									case 16:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_4x1);
										break;
									case 17:
										plokk = TetrominoBuilder.buildTetromino(TetrominoShape.T_J);
										break;
									
									}
								}
							}
						}
						
						
						//System.out.println("GameBoard: " + gb);
						//System.out.println("Plokk: " + plokk);
						
						gameState.setBoard(gb);
						gameState.setCurrentTetromino(plokk);
						
						ArrayList<BotCommand> commands = gameState.findBestMove();
						System.out.println(commands);
						for(BotCommand c : commands){
							robot.doCommand(c);
						}
						gui.panel.setStatusMessage("Ended: " + commands);
					}
					gui.repaint();
					try {sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
			};
		}.start();
	}
}
