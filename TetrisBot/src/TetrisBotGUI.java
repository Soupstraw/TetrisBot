import javax.swing.JFrame;

public class TetrisBotGUI extends JFrame{
	
	/** For some reason this is needed for JFrame to function correctly */
	private static final long serialVersionUID = 5459532085525547128L;

	public TetrisBotGUI() {
		setTitle("TetrisBot v0.1 [Made by Jürgen, Karl and Joosep]");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
}
