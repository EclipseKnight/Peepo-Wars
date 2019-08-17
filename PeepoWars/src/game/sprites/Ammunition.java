package game.sprites;

import game.Game;

public class Ammunition extends Sprite {
	protected int type;
	protected int speed;
	protected int delay;
	protected int damage;
	
	
	public Ammunition(int x, int y, int type, int speed, int delay, int damage) {
		super(x, y);
		this.type = type;
		this.speed = speed;
		this.delay = delay;
		this.damage = damage;
	}
	
	public int getType() {
		return type;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void move() {
		
		x += speed;
		
		if(x > Game.BWIDTH) {
			visible = false;
		}
	}

}
