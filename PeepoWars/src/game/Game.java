package game;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {
	
	private static final long serialVersionUID = -5100548670827443559L;
	
	public static final int BWIDTH = 1000;
	public static final int BHEIGHT = 400;
	public static int gameState = 0;
	Board board;


	
	public Game() {
		 initUI();
	}
	
	private void initUI() {
		board = new Board();
		add(board);
		setTitle("Peepo Wars");
		setSize(BWIDTH, BHEIGHT+135);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Game ex = new Game();
			ex.setVisible(true);
		});
	}
}
