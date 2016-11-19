import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
		robot.keyPress(keyEvent);
		robot.keyRelease(keyEvent);
		try{
			Thread.sleep(5);
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
}
