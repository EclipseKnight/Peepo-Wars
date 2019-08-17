package game.sprites.enemies;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.Attack;
import game.sprites.Boss;
import game.sprites.attacks.IceFist;
import game.sprites.attacks.IceWave;

public class StrobeKing extends Boss {

	private final static double HEALTH = 2000;
	private final static int SPEED = 2;
	private final static int DELAY = 1000;
	private final static boolean ISBOSS = true;
	private List<Attack> attacks;
	private int atkCounter = 0;
	private boolean moveUp = true;
	public StrobeKing(int x, int y) {
		super(x, y, SPEED, DELAY, HEALTH, HEALTH, ISBOSS);
		initBoss();
		setBossStats();
		attack();
		
	}
	
	public List<Attack> getAttacks() {
		return attacks;
	}
	
	public void setBossStats() {
		super.setHealth(HEALTH);
		super.setMaxHealth(HEALTH);
		super.setSpeed(SPEED);
		super.setIsBoss(ISBOSS);
	}
	
	public void initBoss() {
		attacks = new ArrayList<Attack>();
		loadImage("resources/strobeking.gif");
		getImageDimensions(1);	
	}
	
	public void attack() {
		Attack attack = null;
		atkCounter++;
		if(health < 1000 && atkCounter <= 3) {
			attack = new IceFist(x - width, y + height/2);
			attack.setDelay((int)(attack.getDelay()*.75));
			attack.setSpeed((int)(attack.getSpeed()*1.25));
			setTimerDelay(attack.getDelay());
			attacks.add(attack);
		} else if(atkCounter <=3 ) {
			attack = new IceFist(x - width, y + height / 2);
			setTimerDelay(attack.getDelay());
			attacks.add(attack);
		} else if(atkCounter > 3) {
			atkCounter = 0;
			attack = new IceWave(x - width, y + height/2);
			setTimerDelay(attack.getDelay());
			attacks.add(attack);
		}
	}
	
	public void move() {
		
		if(y >= Game.BHEIGHT-95) {
			moveUp = false;
			
		} else if(y < Game.BHEIGHT-95 && moveUp) {
			y += SPEED;
		}
		
		if(!moveUp && y >= 10) {
			y -= SPEED;
		} else if(y < 50) {
			moveUp = true;
		}
		
		
	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(x, y+20, getWidth(), getHeight()*.8);
	}

}
