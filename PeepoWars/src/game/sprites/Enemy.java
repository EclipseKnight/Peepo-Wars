package game.sprites;

import java.util.List;

public class Enemy extends Sprite {

	protected double maxHealth;
	protected double health;
	protected int speed;
	protected int delay;
	protected boolean isBoss;
	protected List<Attack> attacks;
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
	
	public void setTimerDelay(int delay) {
		
	}
	
	public void stopTimer() {
		
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
	
	public void move() {
		
	}
	
	public void attack() {
		
	}
	
	public void damage(int damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
		}
	}

	
	

}
