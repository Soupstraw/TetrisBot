import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;


public class TetrisBotMain {
	
	public static void main(String args[]){
		
		/**
		 * 		OPEN THIS IN BROWSER: https://apps.facebook.com/tetris_battle
		 * */
				
		//Find the game
		TetrisBotGameWindowFinder windowFinder = new TetrisBotGameWindowFinder();
		Rectangle game = windowFinder.getGameWindowLocation();
		
		//Create the GUI
		TetrisBotGUI gui = new TetrisBotGUI();
				
		//Do something reasonable
		if(game == null){
			gui.setStatusMessage("Game window not found!");
		}else{
			//Overlay the game with the GUI
			gui.setStatusMessage("Window found");
			System.out.println(game.toString());
			gui.setLocationRectangle(game);
			try {
				//move the mouse to the lower-right corner
				new Robot().mouseMove(game.x + game.width, game.y+game.height);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
	}
}
