import java.awt.AWTException;
import java.awt.Color;
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
	
	/** @return the location and size of the game window as a Rectangle if the game has been found, otherwise null.*/
	public Rectangle getGameWindowLocation(){
		try{
			Robot robot = new Robot();
			
			//Get a screenshow of the screen
			Rectangle maxWindow = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            final BufferedImage screen = robot.createScreenCapture(maxWindow);
            
            //System.out.println((new Color(screen.getRGB(10, 160))).toString());
            //robot.mouseMove(10, 160);
            
            Point p = getGameWindowSideCoordinates(screen);
            if(p == null){
            	//The game window wasn't found
            	return null;
            }else{
            	//The game window was found, now get the exact boundaries of it
            	Rectangle r = getGameWindowRectangle(screen, p);
            	return r;
            }
		}catch(AWTException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/** @Return a point that has the coordinates to a point on the right side of the game screen or null if the game window wasn't found */
	private Point getGameWindowSideCoordinates(BufferedImage screen){
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
	 * @return the Rectangle that surrounds the game on the screen */
	private Rectangle getGameWindowRectangle(BufferedImage screen, Point point){
		int bottom_y, top_y, left_x, right_x;
		
		right_x = point.x;
				
		int y = point.y;
		int x = point.x;
		while(new Color(screen.getRGB(point.x, y)).equals(new Color(0, 51, 102))){
			y--;
		}
		y++;
		top_y = y;
		while(new Color(screen.getRGB(point.x, y)).equals(new Color(0, 51, 102))){
			while(new Color(screen.getRGB(point.x, y)).equals(new Color(0, 51, 102))){
				y++;
			}
			if(new Color(screen.getRGB(point.x, y+30)).equals(new Color(0, 51, 102))){
				y += 30;
			}
		}
		y--;
		bottom_y = y;
		while(new Color(screen.getRGB(x, y)).equals(new Color(0, 51, 102))){
			x--;
		}
		x++;
		left_x = x;
		return new Rectangle(left_x, top_y, right_x - left_x, bottom_y - top_y);
	}
}
