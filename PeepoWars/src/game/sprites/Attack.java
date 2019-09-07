package game.sprites;

public class Attack extends Sprite {

	protected int speed;
	protected int delay;
	protected int damage;
	
	public Attack(int x, int y, int speed, int delay, int damage) {
		super(x, y);
		this.speed = speed;
		this.delay = delay;
		this.damage = damage;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	//How the attack moves
	public void move() {
		
	}
	

}
