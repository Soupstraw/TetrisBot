import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import game.BotCommand;

public class TetrisBotRobot {
	Robot robot;
	
	public TetrisBotRobot(){
		try{
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void tapKey(int keyEvent){
		System.out.println("KeyEvent: " + keyEvent);
		robot.keyPress(keyEvent);
		try{
			Thread.sleep(50);
		} catch (InterruptedException e) {}
		robot.keyRelease(keyEvent);
		try{
			Thread.sleep(250);
		} catch (InterruptedException e) {}
	}
	
	public void left(){
		tapKey(KeyEvent.VK_LEFT);
	}
	
	public void right(){
		tapKey(KeyEvent.VK_RIGHT);
	}
	
	public void rotate(){
		tapKey(KeyEvent.VK_UP);
	}
	
	public void drop(){
		tapKey(KeyEvent.VK_SPACE);
	}
	
	public void doCommand(BotCommand command){
		switch(command){
		case LEFT:
			left();
			break;
		case RIGHT:
			right();
			break;
		case ROTATE:
			rotate();
			break;
		case FAST_DROP:
			drop();
			break;
		}
	}
}
