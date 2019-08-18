package game.sprites.icons;

import game.sprites.Sprite;

public class MusicOff extends Sprite {

	public MusicOff(int x, int y) {
		super(x, y);
		initGFX();
	}
	
	public void initGFX() {
		loadImage("icons/bgmoff.png");
		getImageDimensions(1);
	}
	
	

}
