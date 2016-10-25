import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TetrisBotGUI extends JWindow{
	
	/** For some reason this is needed for JFrame to function correctly */
	private static final long serialVersionUID = 5459532085525547128L;
	public static final int LEFT_SIDEBAR_WIDTH = 10;
	public static final int TOP_SIDEBAR_HEIGHT = 50;
	public static final int BOTTOM_SIDEBAR_HEIGHT = 10;
	public static final int RIGHT_SIDEBAR_WIDTH = 300;
	private TetrisBotGUIPanel panel;
	private TetrisBotMain main;
	
	public TetrisBotGUI(TetrisBotMain main) {
		this.main = main;
		panel = new TetrisBotGUIPanel(main);
		
		setSize(500, 300);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setBackground(new Color(0,0,0,0));
        setContentPane(panel);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setVisible(true);
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	panel.onClick(e.getX(), e.getY());
            }
        });
		
		new Thread(){
			public void run() {
				//this infinite loop is automatically exited when the JWindow is closed
				while(true){
					TetrisBotGUI.this.main.rec.analyzeGameState();
					repaint();
					try {sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
				}
			};
		}.start();
	}
	
	public void setStatusMessage(String msg){
		panel.setStatusMessage(msg);
		System.out.println(msg);
	}
	
	public void setLocationRectangle(Rectangle location){
		setLocation(location.x-LEFT_SIDEBAR_WIDTH, location.y-TOP_SIDEBAR_HEIGHT);
		setSize(location.width + LEFT_SIDEBAR_WIDTH + RIGHT_SIDEBAR_WIDTH, location.height + TOP_SIDEBAR_HEIGHT + BOTTOM_SIDEBAR_HEIGHT);
		panel.setLocationRectangle(location);
	}
	
}
