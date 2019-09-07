package game.sprites.enemies;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.Attack;
import game.sprites.Boss;
import game.sprites.HitBox;
import game.sprites.attacks.IceFist;
import game.sprites.attacks.IceWave;

public class StrobeKing extends Boss {

	private final static double HEALTH = 2000;
	private final static int SPEED = 2;
	private final static int DELAY = 1000;
	private final static boolean ISBOSS = true;
	private List<Attack> attacks;
	private int atkCounter = 0; //Tracks number of attacks for pattern
	
	private boolean moveUp = true;//Variable that confirms what direction to move based on the coin flip.
	private int pixelsMoved = 0; //Tracks how many pixels the boss moves to then change directions.
	
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
		loadImage("animations/enemies/strobeking.gif");
		getImageDimensions(1);	
	}
	
	public void attack() {
		Attack attack = null;
		atkCounter++;
		if(health < 1000 && atkCounter <= 3) {
			//Creates new attack
			attack = new IceFist(x - width, y + height/2);
			
			//Sets the attack delay for the boss attack timer (might be unnecessary)
			attack.setDelay((int)(attack.getDelay()*.75));
			
			//Sets the attack speed
			attack.setSpeed((int)(attack.getSpeed()*1.25));
			
			//Sets the boss attack timer to the attack delay
			setTimerDelay(attack.getDelay());
			
			//Adds attack to list
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
		pixelsMoved += SPEED;
		if(pixelsMoved > 150) {
			moveUp = flipCoin();
			pixelsMoved = 0;
		}
		
		if(y >= Game.BHEIGHT-150) {
			moveUp = false;
			
		} else if(y < Game.BHEIGHT-150 && moveUp) {
			y += SPEED;	
		}
		
		if(!moveUp && y >= 5) {
			y -= SPEED;
		} else if(y < 50) {
			moveUp = true;
		}
		
		
	}
	
	//flips a coin (50/50)
	public boolean flipCoin() {
		double x = Math.random();
		if(x >= .5)
			return true;
		else if(x < .5) {
			return false;
		}
		return false;
	}
	public HitBox getHitBox() {
		return new HitBox(x , y, Math.min(getWidth()/2, getHeight()/2), 0);
	}

}
