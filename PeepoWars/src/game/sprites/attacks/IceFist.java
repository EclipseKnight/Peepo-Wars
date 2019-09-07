package game.sprites.attacks;

import game.sprites.Attack;
import game.sprites.HitBox;

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
	
	public HitBox getHitBox() {
		return new HitBox(x+6 , y+16, (int) (Math.min(getWidth(), getHeight())/2.5), 0);
	}

}
