import java.awt.Rectangle;
import java.util.ArrayList;

import game.BotCommand;
import game.GameBoard;
import game.TetrisBotGameState;
import game.tetromino.Tetromino;


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
					gui.repaint();
					if(rec.getGameState() == TetrisBotBoardRecognition.GameState.GAME_ONGOING){
						GameBoard gb = rec.getGameBoard();
						Tetromino plokk = rec.getTetromino();
						
						//System.out.println("GameBoard: " + gb);
						//System.out.println("Plokk: " + plokk);
						
						gameState.setBoard(gb);
						gameState.setCurrentTetromino(plokk);
						
						ArrayList<BotCommand> commands = gameState.findBestMove();
						gui.panel.setStatusMessage(commands.toString());
						
						if(gui.panel.isAIActivated()){
							System.out.println(commands);
							for(BotCommand c : commands){
								robot.doCommand(c);
							}
							gui.panel.setStatusMessage("Ended: " + commands);
						}
					}
					try {sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
				}
			};
		}.start();
	}
}
