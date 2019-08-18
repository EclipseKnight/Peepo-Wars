package game.sprites.ammunition;

import java.awt.geom.Rectangle2D;

import game.sprites.Ammunition;

public class Laser extends Ammunition {

	private final static int VELOCITY = 6;
	private final static int AMMO_NUM = 0;
	private final static int DELAY = 200;
	private final static int DAMAGE = 20;
	
	public Laser(int x, int y) {
		super(x, y, AMMO_NUM, VELOCITY, DELAY, DAMAGE);
		initGFX();
	}

	public void initGFX() {
		 loadImage("animations/ship/laser.png");
		 getImageDimensions(1);
	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(x, y, getWidth(), getHeight());
	}
}
