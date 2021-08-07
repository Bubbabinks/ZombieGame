package game;

import main.Manager;

public class EnemySpawner implements ShiftingObject, PhysicsUpdate {
	
	private double x, y;
	
	private int cycles = 0;
	
	public EnemySpawner(double x, double y) {
		this.x = x;
		this.y = y;
		Manager.getGameManager().addShiftingObject(this);
		Manager.getGameManager().addPhysicsUpdate(this);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void update() {
		if (cycles >= GameManager.FPS*GameManager.SECONDS_ENEMY_SPAWN) {
			new Enemy(x, y, GameManager.SLOW_ENEMY_SPEED);
			cycles = -1;
		}
		cycles++;
	}
	
}
