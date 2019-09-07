package game.sprites;
import java.awt.Image;

import javax.swing.ImageIcon;

import game.CrashHandler;
import resources.ResourceLoader;

public class Sprite {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible; //Crucial for removing sprites from lists.
	protected Image image;
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	protected void loadImage(String imageName) {
		if(ResourceLoader.loadImage(imageName) != null) {
			image = ResourceLoader.loadImage(imageName);
		} else {
			CrashHandler.throwError(imageName + " now found");
		}
		
	}
	
	protected Image setImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		return ii.getImage();
	}
	
	protected void getImageDimensions(double scale) {
		width = (int)(image.getWidth(null)*scale);
		height = (int)(image.getHeight(null)*scale);
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public HitBox getHitBox() {
		return null;
	}
	
//	public List<Point2D> getCircle(HitBox hb) {
//		List<Point2D> points = new ArrayList<>();
//		int theta = 0;
//		double x;
//		double y;
//		while(theta < 360) {
//			x = ((hb.getX() + hb.getRadius()) + hb.getRadius() * Math.cos(theta));
//			y = ((hb.getY() + hb.getRadius()) + hb.getRadius() * Math.sin(theta));
//			points.add(new Point2D.Double(x, y));
//			theta++;
//		}
//		return points;
//	}
}
