import java.awt.Rectangle;


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
	}
}
