package game;

import java.util.ArrayList;

import main.Manager;

public class Enemy extends Box implements PhysicsUpdate {
	
	public static ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	
	private double speed;
	
	public Enemy(double x, double y, double speed) {
		super(x, y, GameManager.GAME_ENEMY);
		this.speed = speed;
		Manager.getGameManager().addPhysicsUpdate(this);
		enemys.add(this);
	}

	public void update() {
		pointTowards(Manager.getGameManager().getWorld().getPlayer().x+GameManager.BOX_SIZE/2, Manager.getGameManager().getWorld().getPlayer().y+GameManager.BOX_SIZE/2);
		x += Math.cos(r) * speed;
		y += Math.sin(r) * speed;
	}
	
	public void destroy() {
		super.Destroy();
		Manager.getGameManager().removePhysicsUpdate(this);
		enemys.remove(this);
	}
}
