package game.sprites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Boss extends Enemy implements ActionListener {

	
	private Timer timer;
	public Boss(int x, int y, int speed, int delay, double health, double maxHealth, boolean isBoss) {
		super(x, y);
	}
	public void startTimer(int delay) {
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void setTimerDelay(int delay) {
		if(timer == null) {
			timer = new Timer(delay, this);
			timer.start();
		}
		if(!timer.isRunning()) {
			timer.start();
		} else {
			timer.setDelay(delay);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		attack();
	}
}
