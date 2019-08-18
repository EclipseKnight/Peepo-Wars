package game.sprites.attacks;

import java.awt.geom.Rectangle2D;

import game.sprites.Attack;

public class IceFist extends Attack {

	private final static int SPEED = 8;
	private final static int DELAY = 700;
	private final static int DAMAGE = 35;
	
	public IceFist(int x, int y) {
		super(x, y, SPEED, DELAY, DAMAGE);
		initGFX();
	}
	
	public void initGFX() {
		loadImage("animations/attacks/fistleft.gif");
		getImageDimensions(1);
	}
	
	public void move() {
		x -= speed;
		
		if(x < 0 - getWidth()) {
			visible = false;
		}
	}
	
	public Rectangle2D getHitBox() {
		return new Rectangle2D.Double(x+8, y+18, getWidth()*.55, getHeight()*.7);
	}

}
