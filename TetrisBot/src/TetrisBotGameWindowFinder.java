import java.awt.AWTException;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * 
 * The parameters of the game window:
 * 
 * (Side view:)
 * The window is contained in a white area (read: has white borders)(255, 255, 255)
 * The window is surrounded by a black-ish border (usually 2 pixels wide) (0, 51, 102)
 * Inside the black-ish border is a blue area (usually 4 pixels) (151, 197, 240)
 * Inside of that is another black-ish rectangle (2 pixels) (0, 51, 102)
 * 
 * 
 * */
public class TetrisBotGameWindowFinder {
	
	private static final boolean DEBUG_MSG_ON = false;
	
	/** @return the location and size of the game window as a Rectangle if the game has been found, otherwise null.*/
	public Rectangle getGameWindowLocation(){
		try{
			Robot robot = new Robot();
			
			//Get a screenshow of the screen
			Rectangle maxWindow = getMaximumWindowSize();
            final BufferedImage screen = robot.createScreenCapture(maxWindow);
            
            Point p = getGameWindowSideCoordinates(screen, true);
            boolean extendedSearch = false;
            if(p == null){
            	//The game window wasn't found, using extended (no so precise) search
            	if(DEBUG_MSG_ON) System.out.println("Window not found, using extended search");
            	extendedSearch = true;
            	p = getGameWindowSideCoordinates(screen, false);
            }
            
            if(p == null){
            	//The game window wasn't found
            	return null;
            }else{
            	//The game window was found, now get the exact boundaries of it
            	Rectangle r = getGameWindowRectangle(screen, p, extendedSearch);
            	if(DEBUG_MSG_ON) System.out.println("Found a game window: " + r);
            	return r;
            }
		}catch(AWTException e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Rectangle getMaximumWindowSize(){
		int width = 0;
		int height = 0;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		for (GraphicsDevice curGs : gs)
		{
		  DisplayMode mode = curGs.getDisplayMode();
		  width += mode.getWidth();
		  height = mode.getHeight();
		}
		
		return new Rectangle(0, 0, width, height);
	}
	
	/**@param reliableResults it this is set to true the program is only able to find the game in the menu screen, but false-positives are very unlikely 
	 * @Return a point that has the coordinates to a point on the right side of the game screen or null if the game window wasn't found */
	private Point getGameWindowSideCoordinates(BufferedImage screen, boolean reliableResults){
		//Only check one pixel in every 100 pixels to speed up the processing
        boolean hasFoundNonWhitePixels = false;
        for (int y = 0; y < screen.getHeight(); y+=10) {
            for (int x = 100; x < screen.getWidth(); x+=10) {
                Color testPoint = new Color(screen.getRGB(x, y));
                
                if(hasFoundNonWhitePixels){
                	//Start checking if this pixel is the right one
                	if(testPoint.getRed() == 255  &&  testPoint.getGreen() == 255  &&  testPoint.getBlue() == 255){
                		boolean failed = true;
                		int testx = x;
                		
                		if(reliableResults){
	                		//move left: if correct, we should see a black-ish pixel
	                		for(int i = 0; i < 11; i++){
	                			testx--;
	                			if((new Color(screen.getRGB(testx, y))).equals(new Color(0, 51, 102))){
	                				failed = false;
	                				break;
	                			}
	                		}
	                		
	                		if(failed){
	                			hasFoundNonWhitePixels = false;
	                		}else{
	                			//Testing even further: move left: if correct, we should see a blue pixel
	                			failed = true;
	                    		for(int i = 0; i < 5; i++){
	                    			testx--;
	                    			if((new Color(screen.getRGB(testx, y))).equals(new Color(151, 197, 240))){
	                    				failed = false;
	                    				break;
	                    			}
	                    		}
	                    		
	                    		if(failed){
	                    			hasFoundNonWhitePixels = false;
	                    		}else{
	                    			//And the final test: move left: if correct, we should see a black-ish pixel
	                    			failed = true;
	                        		for(int i = 0; i < 8; i++){
	                        			testx--;
	                        			if((new Color(screen.getRGB(testx, y))).equals(new Color(0, 51, 102))){
	                        				failed = false;
	                        				break;
	                        			}
	                        		}
	                        		
	                        		if(failed){
	                        			hasFoundNonWhitePixels = false;
	                        		}else{
	                        			//The point has been found!
	                        			testx = x;
	                            		for(int i = 0; i < 11; i++){
	                            			testx--;
	                            			if((new Color(screen.getRGB(testx, y))).equals(new Color(0, 51, 102))){
	                            				break;
	                            			}
	                            		}
	                            		return new Point(testx, y);
	                        		}
	                    		}
                			}
                		}else{
                			//move left: if correct, we should see a black pixel
	                		for(int i = 0; i < 11; i++){
	                			testx--;
	                			if((new Color(screen.getRGB(testx, y))).equals(new Color(0, 0, 0))){
	                				failed = false;
	                				break;
	                			}
	                		}
	                		
	                		if(failed){
	                			hasFoundNonWhitePixels = false;
	                		}else{
	            				//As the results don't need to be very reliable, we have skipped some checks
	            				Rectangle r = getGameWindowRectangle(screen, new Point(testx, y), true);
	            				if(DEBUG_MSG_ON) System.out.println("Testing: " + r);
	            				if(r != null  &&  r.width > 700  &&  r.width < 800  &&  r.height > 650  &&  r.height < 750){
	            					//Should be the correct location
	            					return new Point(testx, y);
	            				}
	                		}
                		}
                	}
                }else{
                	if(testPoint.getRed() == 255  &&  testPoint.getGreen() == 255  &&  testPoint.getBlue() == 255){
                		hasFoundNonWhitePixels = true;
                	}
                }
            }
            hasFoundNonWhitePixels = false;
        }
        return null;
	}
	
	/**
	 * @param screen a screenshot of the user's screen
	 * @param point a point that is on the right border of the game window 
	 * @return the Rectangle that surrounds the game on the screen or null if an error occurres*/
	private Rectangle getGameWindowRectangle(BufferedImage screen, Point point, boolean realBlack){
		Color colorOfBlack = realBlack? new Color(0, 0, 0): new Color(0, 51, 102);
		int bottom_y, top_y, left_x, right_x;
		
		right_x = point.x;
		
		try{
			int y = point.y;
			int x = point.x;
			while(new Color(screen.getRGB(point.x, y)).equals(colorOfBlack)){
				y--;
			}
			y++;
			top_y = y;
			while(y < screen.getHeight()  &&  new Color(screen.getRGB(point.x, y)).equals(colorOfBlack)){
				while(y < screen.getHeight()  &&  new Color(screen.getRGB(point.x, y)).equals(colorOfBlack)){
					y++;
				}
				if(y + 30 < screen.getHeight()  &&  new Color(screen.getRGB(point.x, y+30)).equals(colorOfBlack)){
					y += 30;
				}
			}
			y--;
			bottom_y = y;
			while(new Color(screen.getRGB(x, y)).equals(colorOfBlack)){
				x--;
			}
			x++;
			left_x = x;
			return new Rectangle(left_x, top_y, right_x - left_x, bottom_y - top_y);
		}catch(ArrayIndexOutOfBoundsException e){
			//no need to see this, these exceptions are handled separately
			//e.printStackTrace();
			return null;
		}
	}
}
