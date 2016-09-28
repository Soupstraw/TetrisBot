import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;


public class TetrisBotMain {
	
	public static void main(String args[]){
		
		/**
		 * 		AVA BROWSERIS https://apps.facebook.com/tetris_battle
		 * */
		
		//Find the game
		TetrisBotGameWindowFinder windowFinder = new TetrisBotGameWindowFinder();
		Rectangle game = windowFinder.getGameWindowLocation();
		
		//Create a GUI
		TetrisBotGUI gui = new TetrisBotGUI();
		
		//Do something reasonable
		if(game == null){
			gui.setStatusMessage("Game window not found!");
		}else{
			//Overlay the game with the GUI
			gui.setLocation(game.x, game.y);
			gui.setSize(game.width, game.height);
			try {
				//move the mouse to the lower-right corner
				new Robot().mouseMove(game.x + game.width, game.y+game.height);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
	}
}
