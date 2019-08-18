package game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrashHandler extends JFrame {
	static CrashHandler ch = new CrashHandler();
	
	public static void throwError(String error) {
		JOptionPane.showMessageDialog(ch, error);
	}
}
