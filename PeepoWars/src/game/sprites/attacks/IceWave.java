package game.sprites.attacks;

import game.sprites.Attack;
import game.sprites.HitBox;

public class IceWave extends Attack {

	private static final int SPEED = 8;
	private static final int DELAY = 500;
	private static final int DAMAGE = 20;
	public IceWave(int x, int y) {
		super(x, y, SPEED, DELAY, DAMAGE);
		initGFX();
	}
	
	public void initGFX() {
		loadImage("animations/attacks/testwaveatk.png");
		getImageDimensions(8);
	}
	
	public void move() {
		x -= speed;
		
		if(x < 0 - getWidth()) {
			visible = false;
		}
	}
	
	public HitBox getHitBox() {
		return new HitBox(x , y, Math.min(getWidth(), getHeight()), 0);
	}

}
