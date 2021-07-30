package game;

import main.Manager;

public class Enemy extends Box implements PhysicsUpdate {
	
	private double speed;
	
	public Enemy(double x, double y, double speed) {
		super(x, y, Manager.GAME_ENEMY);
		this.speed = speed;
		Manager.getGameManager().addPhysicsUpdate(this);
	}

	public void update() {
		pointTowards(Manager.getGameManager().getWorld().getPlayer().x+Manager.BOX_SIZE/2, Manager.getGameManager().getWorld().getPlayer().y+Manager.BOX_SIZE/2);
		x += Math.cos(r) * speed;
		y += Math.sin(r) * speed;
	}
	
}
