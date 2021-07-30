package game;

import java.awt.Color;

import main.Manager;

public class Projectile extends Box implements PhysicsUpdate {
	
	private double d;
	private double speed;
	
	private int cycleCounter = 0;
	private int maxCycles = 3000;
	
	public Projectile(double x, double y, Color color, double direction, double speed) {
		super(x, y, color, Manager.BOX_SIZE/2);
		r = direction;
		this.d = direction;
		this.speed = speed;
		Manager.getGameManager().addPhysicsUpdate(this);
	}

	public void update() {
		cycleCounter++;
		x += (Math.cos(d) * speed);
		y += (Math.sin(d) * speed);
		if (cycleCounter > maxCycles) {
			Destroy();
		}
	}
	
	public void Destroy() {
		super.Destroy();
		Manager.getGameManager().removePhysicsUpdate(this);
	}
	
}
