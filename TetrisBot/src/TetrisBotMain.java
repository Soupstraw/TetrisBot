import java.awt.Rectangle;


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
			gui.setLocationRectangle(game);
		}
		
	}
}
