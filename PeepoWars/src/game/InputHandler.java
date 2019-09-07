package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public InputHandler(Board board) {
        board.addKeyListener(this);
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) numTimesPressed++;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();
    public Key one = new Key();
    public Key two = new Key();
    public Key h = new Key();
    public Key r = new Key();
    public Key m = new Key();
    public Key p = new Key();

    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
        
        //Pause the music
        if(e.getKeyCode() == KeyEvent.VK_M) {
        	if(Board.audioPlayer.status.equals("play")) {
        		Board.audioPlayer.pause();
        	} else if(Board.audioPlayer.status.equals("paused")) {
        		Board.audioPlayer.resume();
        	}
        }
        
        //Pauses the Game
        if(e.getKeyCode() == KeyEvent.VK_P) {
        	
        	if(Game.gameState != 2 && Game.gameState != 1) {
        		Game.lastGameState = Game.gameState;
        		Game.gameState = 2;
        		Board.pause();
        	} else if(Game.gameState == 2) {
        		Game.gameState = Game.lastGameState;
        		Board.resume();
        	}
        }
        if(e.getKeyCode() == KeyEvent.VK_H) {
        	if(Board.SHOWHITBOX) {
        		Board.SHOWHITBOX = false;
        		Board.DEVMENU = false;
        	}
        		
        	else {
        		Board.SHOWHITBOX = true;
        		Board.DEVMENU = true;
        	}
        		
        }
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
    	
    }
    
    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_SPACE) {
        	space.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_1) {
        	one.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_2) {
        	two.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_R) {
        	r.toggle(isPressed);
        }
        
    }
}
