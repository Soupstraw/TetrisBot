import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.JPanel;


public class TetrisBotGUIPanel extends JPanel{

	/** For some reason this is needed for JPanel to work */
	private static final long serialVersionUID = -8712841791705290046L;
	private Robot robot;
	private static String statusMessage = "Default Status Message";
	private static Rectangle locationRectangle;
	private static String windowTitle = "TetrisBot v0.1 [Made by Jürgen, Karl and Joosep]";
	
	public TetrisBotGUIPanel() {
		setOpaque(false);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void setLocationRectangle(Rectangle rectangle){
		locationRectangle = rectangle;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int mx = MouseInfo.getPointerInfo().getLocation().x;
        int my = MouseInfo.getPointerInfo().getLocation().y;
        Color c = robot.getPixelColor(mx, my);
        
        if(locationRectangle == null){
        	g2d.setColor(Color.RED);
            g2d.setComposite(AlphaComposite.SrcOver.derive(1f));//1 - non-seethrough; 0 - invisible
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
        	g2d.setColor(Color.BLACK);
            g2d.drawString("Color at (" + mx + ";" + my + "): " + c.toString(), 20, 20);
            
            g2d.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 35));
            g2d.drawString(statusMessage, 50, 130);
        }else{
        	int sidebarX = TetrisBotGUI.LEFT_SIDEBAR_WIDTH + locationRectangle.width + 10;
            
        	g2d.setColor(Color.GREEN);
            g2d.setComposite(AlphaComposite.SrcOver.derive(1f));//1 - non-seethrough; 0 - invisible
            
            //left sidebar
            g2d.fillRect(0, 0, TetrisBotGUI.LEFT_SIDEBAR_WIDTH, TetrisBotGUI.TOP_SIDEBAR_HEIGHT + TetrisBotGUI.BOTTOM_SIDEBAR_HEIGHT + locationRectangle.height);
            //top sidebar
            g2d.fillRect(0, 0, TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width, TetrisBotGUI.TOP_SIDEBAR_HEIGHT);
            //bottom sidebar
            g2d.fillRect(0, 
            		TetrisBotGUI.TOP_SIDEBAR_HEIGHT + locationRectangle.height, 
            		TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width, 
            		TetrisBotGUI.TOP_SIDEBAR_HEIGHT + TetrisBotGUI.BOTTOM_SIDEBAR_HEIGHT + locationRectangle.height);
            //right sidebar
            g2d.fillRect(TetrisBotGUI.LEFT_SIDEBAR_WIDTH + locationRectangle.width, 
            		0, 
            		TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width, 
            		TetrisBotGUI.TOP_SIDEBAR_HEIGHT + TetrisBotGUI.BOTTOM_SIDEBAR_HEIGHT + locationRectangle.height);
            
            //Draw the titlebar
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 25));
            g2d.drawString(windowTitle, TetrisBotGUI.LEFT_SIDEBAR_WIDTH, 35);
            
            //Draw the 'X' icon
            g2d.setColor(Color.RED);
            g2d.fillRect(TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width - 40, 0, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawString("X", TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width - 30, 30);
            
            //Draw status information
            g2d.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 15));
            g2d.drawString("Color at (" + mx + ";" + my + "): (" + c.getRed() + "; " + c.getGreen() + "; " + c.getBlue() + ")", sidebarX, 70);
            g2d.drawString("Status: " + statusMessage, sidebarX, 100);
            g2d.drawString("AI: Stopped", sidebarX, 130);
        }
    }
	
	public void setStatusMessage(String msg){
		statusMessage = msg;
	}
	
	public void onClick(int x, int y){
		if(locationRectangle == null  ||  y < 40 && x > TetrisBotGUI.LEFT_SIDEBAR_WIDTH + TetrisBotGUI.RIGHT_SIDEBAR_WIDTH + locationRectangle.width - 40){
			System.exit(0);
		}
	}
	
}
