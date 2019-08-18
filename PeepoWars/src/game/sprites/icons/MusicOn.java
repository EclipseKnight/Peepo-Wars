package game.sprites.icons;

import game.sprites.Sprite;

public class MusicOn extends Sprite {

	public MusicOn(int x, int y) {
		super(x, y);
		initGFX();
	}
	
	public void initGFX() {
		loadImage("icons/bgmon.png");
		getImageDimensions(1);
	}
	
	

}
