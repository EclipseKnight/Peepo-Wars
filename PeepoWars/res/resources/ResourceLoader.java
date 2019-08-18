package resources;
import java.awt.Image;
import java.io.InputStream;

import javax.swing.ImageIcon;

import game.CrashHandler;

public class ResourceLoader {
	public static ResourceLoader rl = new ResourceLoader();
	
	public static Image loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(rl.getClass().getResource(imageName));
		return ii.getImage();
	}
	
	public static InputStream loadMusic(String musicName) {
		InputStream in = rl.getClass().getResourceAsStream(musicName);
		if(in != null) {
			return in;
		}
		CrashHandler.throwError(musicName + " not found");
		return null;
	}
}
