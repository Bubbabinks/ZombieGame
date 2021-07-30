package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Manager;

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
		Manager.getGameManager().removeBox(this);
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
		if (fire) {
			new Projectile(x + (double)(Manager.BOX_SIZE/4) + (Math.cos(r)*(projectileOffset+Manager.BOX_SIZE/2)),
					y + (double)(Manager.BOX_SIZE/4) + (Math.sin(r)*(projectileOffset+Manager.BOX_SIZE/2)), Color.YELLOW, r, 20);
			fire = false;
		}
		int v = 0;
		int h = 0;
		double sprint = 1;
		if (keyManager.input(KeyEvent.VK_SHIFT)) {
			sprint = Manager.PLAYER_SPRINT_MULTIPLIER;
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
			Manager.getGameManager().shiftboxes(0, Manager.PLAYER_SPEED * v * sprint);
		}else if (v == 0) {
			Manager.getGameManager().shiftboxes(Manager.PLAYER_SPEED * h * sprint, 0);
		}else {
			Manager.getGameManager().shiftboxes(Manager.PLAYER_SPEED * h * DIAGONAL * sprint, Manager.PLAYER_SPEED * v * DIAGONAL * sprint);
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
