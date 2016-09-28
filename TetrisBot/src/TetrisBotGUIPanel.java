import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Robot;

import javax.swing.JPanel;


public class TetrisBotGUIPanel extends JPanel{

	/** For some reason this is needed for JPanel to work */
	private static final long serialVersionUID = -8712841791705290046L;
	private Robot robot;
	private String statusMessage = "";
	
	public TetrisBotGUIPanel() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int mx = MouseInfo.getPointerInfo().getLocation().x;
        int my = MouseInfo.getPointerInfo().getLocation().y;
        Color c = robot.getPixelColor(mx, my);
        
        g.drawString("Color at (" + mx + ";" + my + "): " + c.toString(), 20, 20);
        
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 35));
        g.drawString(statusMessage, 50, 130);
        
        
    }
	
	public void setStatusMessage(String msg){
		statusMessage = msg;
	}
	
}
