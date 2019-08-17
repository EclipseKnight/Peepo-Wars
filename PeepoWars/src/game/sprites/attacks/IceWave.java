package game.sprites.attacks;

import java.awt.geom.Rectangle2D;

import game.sprites.Attack;

public class IceWave extends Attack {

	private static final int SPEED = 8;
	private static final int DELAY = 500;
	private static final int DAMAGE = 20;
	public IceWave(int x, int y) {
		super(x, y, SPEED, DELAY, DAMAGE);
		initGFX();
	}
	
	public void initGFX() {
		loadImage("resources/testwaveatk.png");
		getImageDimensions(8);
	}
	
	public void move() {
		x -= speed;
		
		if(x < 0 - getWidth()) {
			visible = false;
		}
	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(x+8, y+8, getWidth()*.9, getHeight()*.9);
	}

}
