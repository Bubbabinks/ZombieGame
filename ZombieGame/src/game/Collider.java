package game;

import java.util.ArrayList;

import main.Manager;

public class Collider implements ShiftingObject {
	
	private static ArrayList<Collider> colliders = new ArrayList<Collider>();
	
	public static boolean dynamicCollision(Box object1, double size1, Box object2, double size2) {
		if (object1.getX() <= object2.getX() + size2 && object1.getX() + size1 >= object2.getX()) {
			if (object1.getY() <= object2.getY() + size2 && object1.getY() + size1 >= object2.getY()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkCollision(double x, double y, double size) {
		for (Collider collider: colliders) {
			if (x <= collider.getX() + GameManager.BOX_SIZE && x + size >= collider.getX()) {
				if (y <= collider.getY() + GameManager.BOX_SIZE && y + size >= collider.getY()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void clear() {
		colliders.clear();
	}
	
	private double x, y;
	
	public Collider(double x, double y) {
		this.x = x; this.y = y;
		Manager.getGameManager().addShiftingObject(this);
		colliders.add(this);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
}
