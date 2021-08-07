package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Manager;
import menu.MenuManager;

public class Player extends Box implements MouseMotionListener, MouseListener, PhysicsUpdate {
	
	private static final double DIAGONAL = Math.sin(0.7853982);
	
	private KeyManager keyManager;
	private double projectileOffset = 20;
	private boolean isFiring = false;
	private boolean fire = false;
	
	public Player(double x, double y) {
		super(x, y, Color.BLACK);
		keyManager = new KeyManager();
		Manager.getGameManager().addMouseMotionListener(this);
		Manager.getGameManager().addMouseListener(this);
		Manager.getGameManager().addKeyListener(keyManager);
		Manager.getGameManager().addPhysicsUpdate(this);
		Manager.getGameManager().removeShiftingObject(this);
	}

	public void mouseDragged(MouseEvent e) {
		pointTowards(e.getPoint().x, e.getPoint().y);
	}
	public void mouseMoved(MouseEvent e) {
		pointTowards(e.getPoint().x, e.getPoint().y);
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		
	}
	public void keyReleased(KeyEvent e) {}

	public void update() {
		for (Enemy enemy: Enemy.enemys) {
			if (Collider.dynamicCollision(this, GameManager.BOX_SIZE, enemy, GameManager.BOX_SIZE)) {
				Manager.getGameManager().closeToMenu();
				return;
			}
		}
		if (fire) {
			new Projectile(x + (double)(GameManager.BOX_SIZE/4) + (Math.cos(r)*(projectileOffset+GameManager.BOX_SIZE/2)),
					y + (double)(GameManager.BOX_SIZE/4) + (Math.sin(r)*(projectileOffset+GameManager.BOX_SIZE/2)), Color.YELLOW, r, 20);
			if (!GameManager.AUTO_FIRE) {
				fire = false;
			}
		}
		int v = 0;
		int h = 0;
		double sprint = 1;
		if (keyManager.input(KeyEvent.VK_SHIFT)) {
			sprint = GameManager.PLAYER_SPRINT_MULTIPLIER;
		}
		if (keyManager.input(KeyEvent.VK_W)) {
			v++;
		}
		if (keyManager.input(KeyEvent.VK_S)) {
			v--;
		}
		if (keyManager.input(KeyEvent.VK_A)) {
			h++;
		}
		if (keyManager.input(KeyEvent.VK_D)) {
			h--;
		}
		if (h == 0) {
			if (!Collider.checkCollision(x, y - (GameManager.PLAYER_SPEED * v * sprint), GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(0, GameManager.PLAYER_SPEED * v * sprint);
			}else if (!Collider.checkCollision(x, y - v, GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(0, v);
			}
		}else if (v == 0) {
			if (!Collider.checkCollision(x - (GameManager.PLAYER_SPEED * h * sprint), y, GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(GameManager.PLAYER_SPEED * h * sprint, 0);
			}else if (!Collider.checkCollision(x - h, y, GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(h, 0);
			}
		}else {
			if (!Collider.checkCollision(x - (GameManager.PLAYER_SPEED * h * DIAGONAL * sprint), y - (GameManager.PLAYER_SPEED * v * DIAGONAL * sprint), GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(GameManager.PLAYER_SPEED * h * DIAGONAL * sprint, GameManager.PLAYER_SPEED * v * DIAGONAL * sprint);
			}else if (!Collider.checkCollision(x - h, y, GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(h, 0);
				if (!Collider.checkCollision(x, y - v, GameManager.BOX_SIZE)) {
					Manager.getGameManager().shiftboxes(0, v);
				}
			}else if (!Collider.checkCollision(x, y - v, GameManager.BOX_SIZE)) {
				Manager.getGameManager().shiftboxes(0, v);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!isFiring) {
				fire = true;
				isFiring = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (isFiring) {
				fire = false;
				isFiring = false;
			}
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
