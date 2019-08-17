package game.sprites.ammunition;

import java.awt.geom.Rectangle2D;

import game.sprites.Ammunition;

public class Missile extends Ammunition {

	private final static int VELOCITY = 4;
	private final static int AMMO_NUM = 1;
	private final static int DELAY = 600;
	private final static int DAMAGE = 65;
	
	public Missile(int x, int y) {
		super(x, y, AMMO_NUM, VELOCITY, DELAY, DAMAGE);
		initGFX();
	}
	
	public void initGFX() {
		 loadImage("resources/missile.png");
		 getImageDimensions(1);
	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(x, y, getWidth(), getHeight());
	}
}
