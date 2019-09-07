package game.sprites;

public class HitBox{

	private double distSq;
	private double radSq;
	
	private int x;
	private int y;
	private int radius;
	private int alpha;
	
	public HitBox(int x, int y, int radius, int alpha) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.alpha = alpha;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public double getRadSq() {
		return radSq;
	}
	
	public double getDistSq() {
		return distSq;
	}
	
	//Checks if the two HitBoxes intersect.
	public boolean intersects(HitBox hb) {
		distSq = Math.pow(((this.x + this.radius) - (hb.x + hb.radius)), 2) + Math.pow(((this.y + this.radius) - (hb.y + hb.radius)), 2);
		radSq = Math.pow((this.radius + hb.radius), 2);
		if(distSq <= radSq) {
			return true;
		}
		return false;
	}
}
