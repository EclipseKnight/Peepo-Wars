package game.sprites;

import java.util.List;

public class Enemy extends Sprite {

	protected double maxHealth; //What its max health should be, mainly used to calculate HP bar drawing
	protected double health; //Health of the enemy
	protected int speed; //Speed of the enemy
	protected int delay; //Delay for enemy attack timer, how fast it attacks.
	protected boolean isBoss; //boolean for if the enemy is a boss or not
	protected List<Attack> attacks; //List of attacks in order of how they are added.
	public Enemy(int x, int y) {
		super(x, y);
	}
	
	protected void setEnemyStats(int speed, int delay, double health, double maxHealth, boolean isBoss, List<Attack> attacks) {
		this.speed = speed;
		this.delay = delay;
		this.health = health;
		this.maxHealth = maxHealth;
		this.isBoss = isBoss;
		this.attacks = attacks;
		
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public void setMaxHealth(double health) {
		this.maxHealth = health;
	}
	
	public void setIsBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public double getHealth() {
		return this.health;
	}
	
	public double getMaxHealth() {
		return this.maxHealth;
	}
	
	public boolean isBoss() {
		return isBoss;
	}
	
	public List<Attack> getAttacks(){
		return attacks;
	}
	
	public void setTimerDelay(int delay) {}
	
	public void stopTimer() {}
	
	public void startTimer() {}
	
	public void move() {}
	
	public void attack() {}
	
	public void damage(int damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
		}
	}

	
	

}
