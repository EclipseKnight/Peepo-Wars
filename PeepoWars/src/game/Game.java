package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Game extends JFrame {

	private static final long serialVersionUID = -5100548670827443559L;

	public static final int BWIDTH = 1000;
	public static final int BHEIGHT = 400;
	public static int gameState = 0; //0=running, 1=restart screen, 2=pause
	public static int lastGameState = gameState;
	public static String status = "playing"; // playing, paused
	Board board;

	public Game() {
		//Initializes the UI
		initUI();
	}

	private void initUI() {
		//Create a new Board object to be attached to the frame
		board = new Board();
		//Attaches panel to frame.
		add(board);
		//Sets the title of the window
		setTitle("Peepo Wars");
		//Sets the borders of the frame
		setSize(BWIDTH, BHEIGHT + 135);
		//Sets location to the center of the screen
		setLocationRelativeTo(null);
		//Sets resizable to false so the borders dont change and break the game.
		setResizable(false);
		//Sets default close to exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Game ex = new Game();
				ex.setVisible(true);
			}
		});
	}
}
