package game.sprites.ammunition;

import game.sprites.Ammunition;
import game.sprites.HitBox;

public class Missile extends Ammunition {

	private final static int VELOCITY = 5;
	private final static int AMMO_NUM = 1;
	private final static int DELAY = 600;
	private final static int DAMAGE = 65;
	
	public Missile(int x, int y) {
		super(x, y, AMMO_NUM, VELOCITY, DELAY, DAMAGE);
		initGFX();
	}
	
	public void initGFX() {
		 loadImage("animations/ship/missile.png");
		 getImageDimensions(1);
	}
	
	public HitBox getHitBox() {
		return new HitBox(x, y, Math.min(getWidth(), getHeight()), 0);
	}
}
