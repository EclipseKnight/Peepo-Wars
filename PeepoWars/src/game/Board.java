package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.sprites.Ammunition;
import game.sprites.Attack;
import game.sprites.Enemy;
import game.sprites.HitBox;
import game.sprites.ammunition.Laser;
import game.sprites.ammunition.Missile;
import game.sprites.enemies.StrobeKing;
import game.sprites.icons.MusicOff;
import game.sprites.icons.MusicOn;
import game.sprites.icons.Pause;
import game.sprites.icons.Play;
import game.sprites.ship.Ship;
import resources.ResourceLoader;

public class Board extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -2451749377672043233L;
	
	private final static int ICRAFT_X = 40;
    private final static int ICRAFT_Y = 60;
    private final int UPDATE_DELAY = 20;
    public static final double SCALE = 2;
    public static boolean DEVMENU = false;
    public static boolean SHOWHITBOX = false;
    private static List<Enemy> enemies;
    private static List<Ship> ships;
	private static Timer updateTimer;
	private static Ship ship;
	public static InputHandler input;
	public static AudioPlayer audioPlayer;
	public static Font gameFont;
	
	long lastTime = System.nanoTime();
	double nanoSecondConversion = 1000000000.0/60;
	double deltaSeconds = 0;
	
	private static double startTime;
	private static double endTime;
	
	//Constructor for the Board object, initializes resources, ships, enemies, audio, and timer.		
	public Board() {
		
		initResources();
		initBoard();
		initShips();
		initEnemies();
		initAudioPlayer();
		initTimer();
		
		startTime = System.currentTimeMillis();
	}
	
	//Pauses updateTimer, enemy timers, and sets Game status to "Paused".
	public static void pause() {
		updateTimer.stop();
		for(Enemy e : enemies) {
			e.stopTimer();
		}
		Game.status = "paused";
	}
	
	//Starts updateTimer, enemy timers, and sets Game status to "playing".
	public static void resume() {
		updateTimer.start();
		for(Enemy e : enemies) {
			e.startTimer();
		}
		Game.status = "playing";
	}
	
	//Restarts and clears all lists and timers to be initialized again.
	public static void restart() {
		enemies.clear();
		ships.clear();
		startTime = System.currentTimeMillis();
		endTime = 0;
		Game.gameState = 0;
		initShips();
 		initEnemies();
	}
	
	//Initializes the board and registers the input handler 
	private void initBoard() {
		input = new InputHandler(this);
		setBackground(Color.black);
		setFocusable(true);
		setBounds(0, 0, Game.BWIDTH, Game.BHEIGHT);
		
	}
	
	//Creates the ship object and adds it to the ship list.
	private static void initShips() {
		ship = new Ship(ICRAFT_X, ICRAFT_Y, input);
		ships = new ArrayList<>();
		ships.add(ship);
	}
	
	//Creates the starting list of enemies to spawn.
	private static void initEnemies() {
		enemies = new ArrayList<>();
		enemies.add(new StrobeKing(Game.BWIDTH-180, Game.BHEIGHT/3));
	}
	
	//Starts the updateTimer for sprite movements and actions.
	private void initTimer() {
		updateTimer = new Timer(UPDATE_DELAY, this);
		updateTimer.start();
		
	}
	
	//Starts the AudioPlayer for the background music.
	private static void initAudioPlayer() {
		audioPlayer = new AudioPlayer();
		audioPlayer.play();
	}
	
	//Loads the resources such as fonts.
	private void initResources() {
		try {
			gameFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.loadStream("fonts/prstartk.ttf")).deriveFont(30f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			CrashHandler.throwError(e.toString());
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(gameFont);
			
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		FPSCounter.StartCounter();
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
		FPSCounter.StopAndPost();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(gameFont.deriveFont(11f));
		
		drawBackground(g2d);
		drawShip(g2d);
		drawEnemies(g2d);
		drawProjectiles(g2d);
		drawHealthBar(g2d);
		drawWeaponBar(g2d);
		drawSettingsBar(g2d);
		
		if(DEVMENU) {
			drawDevMenu(g2d);
		}
		drawFPSCounter(g2d);
		drawTimer(g2d);
		drawBorder(g2d);
		
		g2d.setColor(Color.cyan);
	}
	
	private void drawDevMenu(Graphics2D g2d) {
		// Draws the x and y coordinate of the ship
		g2d.setColor(Color.darkGray);
		g2d.drawString("x=" + ship.getX() + "y=" + ship.getY(), 130, 14);
		
		// Draws the gameStates
		g2d.setColor(Color.darkGray);
		g2d.drawString("gameState : " + Game.gameState, 400, 14);
	}
	
	private void drawFPSCounter(Graphics2D g2d) {
		g2d.setColor(Color.darkGray);
		g2d.drawString("FPS:" + FPSCounter.fps, 0, 14);
	}
	
	private void drawBackground(Graphics2D g2d) {
		g2d.drawImage(ResourceLoader.loadImage("animations/stage/starbackground.gif"), 0, 0, null);
	}
	
	//Draws Settings Bar (music on or off, game paused or playing)
	private void drawSettingsBar(Graphics2D g2d) {
		
		Image music = null;
		Image status = null;
		
		g2d.setColor(Color.lightGray);
		g2d.fillRoundRect(Game.BWIDTH/2-45, Game.BHEIGHT+50, 80, 40, 10, 10);
		
		if(audioPlayer.getStatus().equals("play")) 
			music = new MusicOn(0, 0).getImage();
		else if(audioPlayer.getStatus().equals("paused")) 
			music = new MusicOff(0,0).getImage();
		
		
		if(Game.status.equals("playing")) 
			status = new Play(0, 0).getImage();
		else if(Game.status.equals("paused")) 
			status = new Pause(0, 0).getImage();
		
		
		g2d.drawImage(music, Game.BWIDTH/2-40, Game.BHEIGHT+55, music.getWidth(null)*2, music.getHeight(null)*2,  null);
		g2d.drawImage(status, Game.BWIDTH/2, Game.BHEIGHT+55, music.getWidth(null)*2, music.getHeight(null)*2,  null);

		
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
		g2d.drawString("Time: " + (System.currentTimeMillis()-startTime)/1000.0, 100, 14);
		
		pause(g2d);
		win(g2d);
		lose(g2d);
	}
	
	private void drawBorder(Graphics2D g2d) {
		g2d.drawRect(0, Game.BHEIGHT, Game.BWIDTH, 2);
		g2d.drawRect(0, 20, Game.BWIDTH, 2);
	}
	
	//Checks for pause scenario
	private void pause(Graphics2D g2d) {
		if(Game.status.equals("paused")) {
			g2d.setFont(gameFont);
			g2d.setColor(Color.GRAY);
			g2d.drawString("Paused...", 200, 150);
		}
	}
	
	//Checks for win scenario
	private void win(Graphics2D g2d) {
		if(enemies.size() <= 0) {
			g2d.setFont(gameFont.deriveFont(50f));
			g2d.setColor(Color.cyan);
			g2d.drawString("You Win!", 270, 120);
			g2d.drawString("Time: " + endTime + " secs", 50, 250);
			g2d.setFont(gameFont);
			g2d.drawString("Press R To Restart...", 200, 390);
			for(Enemy e: enemies) {
				e.stopTimer();
			}
			Game.gameState = 1;
			ship.win();
		}
	}
	
	//Checks for lose scenario
	private void lose(Graphics2D g2d) {
		if(ships.size() <=0 ) {
			g2d.setFont(gameFont.deriveFont(50f));
			g2d.setColor(Color.red);
			g2d.drawString("You Lose!", 270, 120);
			g2d.drawString("Time: " + endTime + " secs", 50, 250);
			g2d.setFont(gameFont);
			g2d.drawString("Press R To Restart...", 200, 390);
			for(Enemy e: enemies) {
				e.stopTimer();
			}
			Game.gameState = 1;
		}
		
	}
	
	private void drawProjectiles(Graphics2D g2d) {
		Iterator<?> it2; 
		Iterator<?> it1 = ship.getAmmoFired().iterator();
		
		while(it1.hasNext()){
			Ammunition ammo = (Ammunition) it1.next();
			g2d.drawImage(ammo.getImage(), ammo.getX(), ammo.getY(), (int)(ammo.getWidth() * (SCALE/2)), (int)(ammo.getHeight() * (SCALE/2)), this);
			if(SHOWHITBOX) {
				g2d.setColor(Color.green);
				HitBox hitbox = ammo.getHitBox();
				g2d.setFont(new Font("Arial", Font.BOLD, 13));
				g2d.drawOval(hitbox.getX(), hitbox.getY(), (int)(hitbox.getRadius()*SCALE), (int)(hitbox.getRadius()*SCALE));
				g2d.drawString("hitbox: " + hitbox.getX() + ", " + hitbox.getY(), ammo.getX()+20, ammo.getY()-10);
				g2d.drawString("radius: " + hitbox.getRadius(), ammo.getX()+20, ammo.getY());
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
					HitBox hitbox = a.getHitBox();
					g2d.setFont(new Font("Arial", Font.BOLD, 13));
					g2d.drawOval(hitbox.getX(), hitbox.getY(), (int)(hitbox.getRadius()*SCALE), (int)(hitbox.getRadius()*SCALE));
					g2d.drawString("hitbox: " + hitbox.getX() + ", " + hitbox.getY(), a.getX()+20, a.getY()-10);
					g2d.drawString("radius: " + hitbox.getRadius(), a.getX()+20, a.getY());
				}
			}	
		}
	}
	
	private void drawEnemies(Graphics2D g2d) {
		for(Enemy e : enemies) {
			g2d.drawImage(e.getImage(), e.getX(), e.getY(), e.getWidth(), e.getHeight(), this);
			if(SHOWHITBOX) {
				g2d.setColor(Color.green);
				
				HitBox hitbox = e.getHitBox();
				g2d.setFont(new Font("Arial", Font.BOLD, 13));
				g2d.drawOval(hitbox.getX(), hitbox.getY(), (int)(hitbox.getRadius()*SCALE), (int)(hitbox.getRadius()*SCALE));
				g2d.drawString("hitbox: " + hitbox.getX() + ", " + hitbox.getY(), e.getX()+20, e.getY()-10);
				g2d.drawString("radius: " + hitbox.getRadius(), e.getX()+20, e.getY());
			}
		}
	}
	
	private void drawShip(Graphics2D g2d) {
		g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), (int)(ship.getWidth() * SCALE), (int)(ship.getHeight() * SCALE), this);
		if(SHOWHITBOX) {
			g2d.setColor(Color.green);
			g2d.setFont(new Font("Arial", Font.BOLD, 13));
			HitBox hitbox = ship.getHitBox();
			g2d.drawOval(hitbox.getX(), hitbox.getY(), (int)(hitbox.getRadius()*SCALE), (int)(hitbox.getRadius()*SCALE));
			g2d.drawString("hitbox: " + hitbox.getX() + ", " + hitbox.getY(), ship.getX()+20, ship.getY());
			g2d.drawString("radius: " + hitbox.getRadius(), ship.getX()+20, ship.getY()+10);
			}
	}
	
	private void drawHealthBar(Graphics2D g2d) {
		
		Rectangle bar;
		Rectangle health;
		g2d.setFont(gameFont.deriveFont(10f));
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
			g2d.drawString(e.getHealth() + "/" + e.getMaxHealth(), (int)(bar.x*1.01), bar.y+14);
			
		}
		
		bar = new Rectangle( 10, Game.BHEIGHT+65, 350, 20);
		health = new Rectangle( bar.x+1, bar.y+1, barCalculateShip(ship, bar.width)-1, bar.height-1);
		g2d.setColor(Color.gray);
		g2d.draw(bar);
		g2d.setColor(Color.red);
		g2d.fill(health);
		g2d.setColor(Color.white);
		g2d.drawString(ship.getHealth() + "/" + ship.getMaxHealth(), (int)(bar.x*1.01), bar.y+14);
		
	}
	
	//Calculates Enemy HP bar size
	private int barCalculate(Enemy e) {
		return (int)(e.getHealth()/e.getMaxHealth() * e.getWidth());
	}
	
	//Calculates Boss HP bar size
	private int barCalculateBoss(Enemy e, int width) {
		return (int)(e.getHealth()/e.getMaxHealth() * width);
	}
	
	//Calculates Ship HP bar size
	private int barCalculateShip(Ship ship, int width) {
		return (int)(ship.getHealth()/ship.getMaxHealth() * width);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(Game.gameState == 0) {
			ship.tick();
			updateProjectiles();
			updateShip();
			updateEnemies();
			checkCollision();
		}
		if(Game.gameState == 1 && input.r.isPressed()) {
			restart();
		}
		repaint();
	}
	
	//Checks for HitBox collision
	private void checkCollision() {
		
		HitBox hb1;
		HitBox hb2;
		
		Iterator<Enemy> it1 = enemies.iterator();
		Iterator<Ammunition> it2 = ship.getAmmoFired().iterator();
		
		while(it1.hasNext()) {
			Enemy e = it1.next();
			hb2 = e.getHitBox();
			
			while(it2.hasNext()) {
				Ammunition a = it2.next();
				hb1 = a.getHitBox();
				
				//TODO multiple hitboxes on a single enemy
				if(hb1.intersects(hb2)) {
					e.damage(a.getDamage());
					a.setVisible(false);
				} 
			}
 		}

		hb1 = ship.getHitBox();
		for(Enemy en : enemies) {
			for(Attack at : en.getAttacks()) {
				hb2 = at.getHitBox();
				
				if(hb1.intersects(hb2)) {
					at.setVisible(false);
					ship.damage(at.getDamage());
				}
			}
		}
		
		hb1 = ship.getHitBox();
		for(Enemy en : enemies) {
			hb2 = en.getHitBox();
			
			if(hb1.intersects(hb2)) {
				ship.damage(1);
			}
		}
	}
	
	//Updates Enemy x and y positions
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
	
	//Updates Ship x and y position
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
	
	//Updates Projectile x and y position
	private void updateProjectiles() {
		
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
