package game.sprites.ship;
import java.util.ArrayList;
import java.util.List;

import game.Board;
import game.Game;
import game.InputHandler;
import game.sprites.Ammunition;
import game.sprites.HitBox;
import game.sprites.Sprite;
import game.sprites.ammunition.Laser;
import game.sprites.ammunition.Missile;

public class Ship extends Sprite {
	private int dx;
	private int dy;
	private int weapon = 1;
	private InputHandler input;
	private final int SHIP_SPEED = 4;
	private long shootTime = 0;
	private double health = 100;
	private double maxHealth = 100;
	private List<Ammunition> ammoFired;
	
	public Ship(int x, int y, InputHandler input) {
		super(x, y);
		this.input = input;

		initShip();
	}
	
	public void win() {
		loadImage("animations/ship/shipwin.gif");
		getImageDimensions(.6);
		setVisible(false);
	}
	
	public void lose() {
		loadImage("animations/ship/shipdie.gif");
		getImageDimensions(.3);
		setVisible(false);
	}
	
	public void damage(int damage) {
		health -= damage;
		if(health <= 0) {
			lose();
		}
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public int getSpeed() {
		return SHIP_SPEED;
	}
	
	public int getWeapon() {
		return weapon;
	}
	
	private void initShip() {
		ammoFired = new ArrayList<>();
		
		loadImage("animations/ship/ship.png");
		getImageDimensions(1);
	}
	
	public List<Ammunition> getAmmoFired() {
		return ammoFired;
	}
	
	public void move() {
		x += dx;
		y += dy;
		
		if(getX() >= Game.BWIDTH-20) {
			setX(getX() - getSpeed());
		}
		if(getX() <= 0) {
			setX(getX() + getSpeed());
		}
		if(getY() >= Game.BHEIGHT-10) {
			setY(getY() - getSpeed());
		}
		if(getY() <= 20) {
			setY(getY() + getSpeed());
		}
	}
	
	 public void tick() {
		 dx = 0;
		 dy = 0;
	        if(input != null) {
	        	if(input.up.isPressed()) {
	        		dy = -SHIP_SPEED;
	        	}
	        	if(input.down.isPressed()) {
	        		dy = SHIP_SPEED;
	        	}
	        	if(input.right.isPressed()) {
	        		dx = SHIP_SPEED;
	        	}
	        	if(input.left.isPressed()) {
	        		dx = -SHIP_SPEED;
	        	}
	        	if(input.space.isPressed()) {
	        		fire();
	        	}
	        	if(input.one.isPressed()) {
	        		weapon = 1;
	        	}
	        	if(input.two.isPressed()) {
	        		weapon = 2;
	        	}
	        	if(input.h.isPressed()) {
	        		if(Board.SHOWHITBOX) {
	        			Board.SHOWHITBOX = false;
	        		} else {
	        			Board.SHOWHITBOX = true;
	        		}
	        	}
	        }
	    }
	 
	 public void fire() {
		 Ammunition ammo = null;
		 switch(weapon) {
		 case 1:
			 ammo = new Laser(x + width, y + height / 2);
			 break;
		 case 2:
			 ammo = new Missile(x + width, y + height / 2);
			 break;
		 }
		 
		 if(System.currentTimeMillis() - shootTime > ammo.getDelay()) {
			 shootTime = System.currentTimeMillis();
			 ammoFired.add(ammo);
		 }
	 }
	 
	public HitBox getHitBox() {
		return new HitBox(x+2 ,y+2 ,  (int) (Math.min(getWidth(), getHeight())/1.7), 0);
	}
}
