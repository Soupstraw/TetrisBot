import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisBotGUI extends JFrame{
	
	/** For some reason this is needed for JFrame to function correctly */
	private static final long serialVersionUID = 5459532085525547128L;
	private JPanel panel = new TetrisBotGUIPanel();
	
	public TetrisBotGUI() {
		setTitle("TetrisBot v0.1 [Made by Jürgen, Karl and Joosep]");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(panel, BorderLayout.CENTER);
		
		new Thread(){
			public void run() {
				//this infinite loop is automatically exited when the JFrame is closed
				while(true){
					panel.repaint();
					try {sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
				}
			};
		}.start();
	}
	
	public void setStatusMessage(String msg){
		((TetrisBotGUIPanel) panel).setStatusMessage(msg);
	}
	
}
