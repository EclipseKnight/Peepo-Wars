package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.sprites.Ammunition;
import game.sprites.Attack;
import game.sprites.Enemy;
import game.sprites.ammunition.Laser;
import game.sprites.ammunition.Missile;
import game.sprites.enemies.StrobeKing;
import game.sprites.ship.Ship;

public class Board extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -2451749377672043233L;
	
	private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int UPDATE_DELAY = 20;
    private final int RENDER_DELAY = 0;
    public static final double SCALE = 2;
    private final boolean DEVMENU = true;
    public static boolean SHOWHITBOX = false;
    private List<Enemy> enemies;
    private List<Ship> ships;
	private Timer updateTimer;
	private Timer renderTimer;
	private Ship ship;
	public InputHandler input;
	
	private double startTime;
	private double endTime;
	public Board() {
		
		initBoard();
		startTime = System.currentTimeMillis();
	}
	
	private void initBoard() {
		input = new InputHandler(this);
		setBackground(Color.black);
		setFocusable(true);
		setBounds(0, 0, Game.BWIDTH, Game.BHEIGHT);
		
		ship = new Ship(ICRAFT_X, ICRAFT_Y, input);
		ships = new ArrayList<>();
		ships.add(ship);
		initEnemies();
		updateTimer = new Timer(UPDATE_DELAY, this);
		updateTimer.start();
	}
	
	public void initEnemies() {
		enemies = new ArrayList<>();
		enemies.add(new StrobeKing(Game.BWIDTH-150, Game.BHEIGHT/2));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, Game.BHEIGHT, Game.BWIDTH, 2);
		if(DEVMENU) {
			drawDevMenu(g2d);
		}

		borderCollision();
		drawShip(g2d);
		drawEnemies(g2d);
		drawProjectiles(g2d);
		drawHealthBar(g2d);
		drawWeaponBar(g2d);
		drawTimer(g2d);
		
		
		
	}
	
	private void borderCollision() {
		if(ship.getX() >= Game.BWIDTH-20) {
			ship.setX(ship.getX() - ship.getSpeed());
		}
		if(ship.getX() <= 0) {
			ship.setX(ship.getX() + ship.getSpeed());
		}
		if(ship.getY() >= Game.BHEIGHT-20) {
			ship.setY(ship.getY() - ship.getSpeed());
		}
		if(ship.getY() <= 0) {
			ship.setY(ship.getY() + ship.getSpeed());
		}
	}
	
	private void drawWeaponBar(Graphics2D g2d) {
		List<Image> images = new ArrayList<>();
		
		Image laser = new Laser(0, 0).getImage();
		Image missile = new Missile(0, 0).getImage();
		images.add(0, laser);
		images.add(1, missile);
		
		Rectangle2D slot;
		for(int i = 0; i < images.size(); i++) {
			slot = new Rectangle2D.Double((40 * i)+10, Game.BHEIGHT+20, 30, 30);
			g2d.drawImage(images.get(i), (int)(slot.getX())+5, (int)(slot.getY())+10, (int)(images.get(i).getWidth(null)*SCALE), (int)(images.get(i).getHeight(null)*SCALE), null);
			if(ship.getWeapon() == i+1) {
				g2d.setColor(Color.blue);
			} else 
				g2d.setColor(Color.darkGray);
			g2d.draw(slot);
			
		}
	}
	
	private void drawTimer(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("Time: " + (System.currentTimeMillis()-startTime)/1000.0, 100, 10);
		
		win(g2d);
		lose(g2d);
	}
	
	private void win(Graphics2D g2d) {
		if(enemies.size() <= 0) {
			g2d.setFont(new Font("Consolas", 100, 100));
			g2d.setColor(Color.cyan);
			g2d.drawString("You Win!", 200, 90);
			g2d.drawString("Time: " + endTime + " secs", 50, 250);
			updateTimer.stop();
			
		}
	}
	
	private void lose(Graphics2D g2d) {
		if(ships.size() <=0 ) {
			g2d.setFont(new Font("Consolas", 100, 100));
			g2d.setColor(Color.cyan);
			g2d.drawString("You Lose!", 200, 90);
			g2d.drawString("Time: " + endTime + " secs", 50, 250);
			updateTimer.stop();
			for(Enemy e: enemies) {
				e.stopTimer();
			}
		}
	}
	private void drawProjectiles(Graphics2D g2d) {
		Iterator it2; 
		Iterator it1 = ship.getAmmoFired().iterator();
		
		while(it1.hasNext()){
			Ammunition ammo = (Ammunition) it1.next();
			g2d.drawImage(ammo.getImage(), ammo.getX(), ammo.getY(), (int)(ammo.getWidth() * (SCALE/2)), (int)(ammo.getHeight() * (SCALE/2)), this);
			if(SHOWHITBOX) {
				g2d.setColor(Color.green);
				g2d.draw(ammo.getHitBox());
			}
		}
		
		it1 = enemies.iterator();
		while(it1.hasNext()) {
			Enemy e = (Enemy) it1.next();
			it2 = e.getAttacks().iterator();
			while(it2.hasNext()) {
				Attack a = (Attack) it2.next();
				g2d.drawImage(a.getImage(), a.getX(), a.getY(), (int)(a.getWidth() * (SCALE/2)), (int)(a.getHeight() * (SCALE/2)), this);
				if(SHOWHITBOX) {
					g2d.draw(a.getHitBox());
				}
			}	
		}
	}
	
	private void drawEnemies(Graphics2D g2d) {
		for(Enemy e : enemies) {
			g2d.drawImage(e.getImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), this);
			if(SHOWHITBOX) {
				g2d.setColor(Color.green);
				g2d.draw(e.getHitBox());
			}
		}
	}
	
	private void drawShip(Graphics2D g2d) {
		g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), (int)(ship.getWidth() * SCALE), (int)(ship.getHeight() * SCALE), this);
		if(SHOWHITBOX) {
			g2d.setColor(Color.green);
			g2d.draw(ship.getHitBox());
		}
	}
	
	private void drawHealthBar(Graphics2D g2d) {
		Rectangle bar;
		Rectangle health;
		for(Enemy e : enemies) {
			if(e.isBoss()) {
				bar = new Rectangle( Game.BWIDTH-370, Game.BHEIGHT+65, 350, 20);
				health = new Rectangle( bar.x+1, bar.y+1, barCalculateBoss(e, bar.width)-1, bar.height-1);
			} else {
				bar = new Rectangle( e.getX(), e.getY() + e.getHeight()+10, e.getWidth(), 10);
				health = new Rectangle( bar.x+1, bar.y+1, (int)(barCalculate(e))-1 , 10);
				
			}
			g2d.setColor(Color.gray);
			g2d.draw(bar);
			g2d.setColor(Color.red);
			g2d.fill(health);
			g2d.setColor(Color.white);
			g2d.drawString(e.getHealth() + "/" + e.getMaxHealth(), (int)(bar.x*1.01), bar.y+10);
			
		}
		
		bar = new Rectangle( 10, Game.BHEIGHT+65, 350, 20);
		health = new Rectangle( bar.x+1, bar.y+1, barCalculateShip(ship, bar.width)-1, bar.height-1);
		g2d.setColor(Color.gray);
		g2d.draw(bar);
		g2d.setColor(Color.red);
		g2d.fill(health);
		g2d.setColor(Color.white);
		g2d.drawString(ship.getHealth() + "/" + ship.getMaxHealth(), (int)(bar.x*1.01), bar.y+10);
		
	}
	
	private int barCalculate(Enemy e) {
		return (int)(e.getHealth()/e.getMaxHealth() * e.getWidth());
	}
	
	private int barCalculateBoss(Enemy e, int width) {
		return (int)(e.getHealth()/e.getMaxHealth() * width);
	}
	
	private int barCalculateShip(Ship ship, int width) {
		return (int)(ship.getHealth()/ship.getMaxHealth() * width);
	}
	
	
	private void drawDevMenu(Graphics2D g2d) {
		
		//This draws the ships current x and y position.
		g2d.drawString("x = " + ship.getX() + "y = " + ship.getY(), 0, 10);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 ship.tick();
		 updateMissiles();
		 updateShip();
		 updateEnemies();
		 checkCollision();
		 repaint();
	}
	
	private void checkCollision() {
		
//		Iterator<Enemy> it1 = enemies.iterator();
//		Iterator<Ammunition> it2 = ship.getAmmoFired().iterator();
//		while(it1.hasNext()) {
//			Rectangle2D r2 = it1.next().getHitBox();
//			Enemy e = it1.next();
//			while(it2.hasNext()) {
//				Rectangle2D r1 = it2.next().getHitBox();
//				Ammunition a = it2.next();
//				if(r1.intersects(r2)) {
//					e.damage(a.getDamage());
//					a.setVisible(false);
//				} 
//			}
// 		}
		
		Rectangle2D r1;
		Rectangle2D r2;
		List<Ammunition> ammunition = ship.getAmmoFired();
		for(Enemy e : enemies) {
			r2 = e.getHitBox();
			
			for(Ammunition m : ammunition) {
				r1 = m.getHitBox();
				
				if(r1.intersects(r2)) {
					m.setVisible(false);
					e.damage(m.getDamage());
				}
			}
		}

		r1 = ship.getHitBox();
		for(Enemy e : enemies) {
			for(Attack a : e.getAttacks()) {
				r2 = a.getHitBox();
				
				if(r1.intersects(r2)) {
					a.setVisible(false);
					ship.damage(a.getDamage());
				}
				
			}
		}
		
		r1 = ship.getHitBox();
		for(Enemy e : enemies) {
			r2 = e.getHitBox();
			
			if(r1.intersects(r2)) {
				ship.damage(1);
			}
		}
		
	}
	
	private void updateEnemies() {
		for(int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			
			if(enemy.isVisible()) {
				enemy.move();
			} else {
				enemy.stopTimer();
				enemies.remove(i);
				endTime = (System.currentTimeMillis()-startTime)/1000.0;
			}
		}
		
		for(Enemy e : enemies) {
			
			e.move();
		}
	}
	
	private void updateShip() {
		for(int i = 0; i < ships.size(); i++) {
			if(ships.get(i).isVisible()) {
				ship.move();  
			} else {
				ships.remove(i);
				endTime = (System.currentTimeMillis()-startTime)/1000.0;
			}
		}
	      
	    
	}    
	
	private void updateMissiles() {
		
		List<Ammunition> ammunition = ship.getAmmoFired();
		
		for(int i = 0; i < ammunition.size(); i++) {
			
			Ammunition ammo = ammunition.get(i);
			
			if(ammo.isVisible()) {
				ammo.move();
			} else {
				ammunition.remove(i);
			}
		}
		
		
		for(int i = 0; i < enemies.size(); i++) {
			
			List<Attack> attacks = enemies.get(i).getAttacks();
			
			for(int j = 0; j < attacks.size(); j++) {
				
				Attack attack = attacks.get(j);
				
				if(attack.isVisible()) {
					attack.move();
				} else {
					attacks.remove(j);
				}
			}
		}
			
	}
}
