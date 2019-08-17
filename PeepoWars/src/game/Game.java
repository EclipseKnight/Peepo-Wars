package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = -5100548670827443559L;
	
	public static final int BWIDTH = 1000;
	public static final int BHEIGHT = 300;
	

	
	public Game() {
		 initUI();
	}
	
	private void initUI() {
		
		add(new Board());
		setTitle("Peepo Wars");
		setSize(BWIDTH, BHEIGHT+100);
	
		
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
