package game.sprites.ship;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import game.Board;
import game.InputHandler;
import game.sprites.Ammunition;
import game.sprites.Sprite;
import game.sprites.ammunition.Laser;
import game.sprites.ammunition.Missile;

public class Ship extends Sprite {
	private int dx;
	private int dy;
	private int weapon = 1;
	private InputHandler input;
	private final int SHIP_SPEED = 3;
	private long shootTime = 0;
	private double health = 100;
	private double maxHealth = 100;
	private List<Ammunition> ammoFired;
	
	public Ship(int x, int y, InputHandler input) {
		super(x, y);
		this.input = input;

		initShip();
	}
	
	public void damage(int damage) {
		health -= damage;
		if(health <= 0) {
			loadImage("resources/shipdie.gif");
			getImageDimensions(.3);
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
	
	private void initShip() {
		ammoFired = new ArrayList<>();
		
		loadImage("resources/ship.png");
		getImageDimensions(1);
	}
	
	public List<Ammunition> getAmmoFired() {
		return ammoFired;
	}
	
	public void move() {
		x += dx;
		y += dy;
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
	        	if(input.fire.isPressed()) {
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
	 
	 public Rectangle2D getHitBox() {
			return new Rectangle2D.Double(x, y, getWidth()*Board.SCALE, getHeight()*Board.SCALE);
		}
}
