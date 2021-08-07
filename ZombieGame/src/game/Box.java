package game;

import java.awt.Color;
import java.awt.Graphics;

import main.Manager;

public class Box implements Render, ShiftingObject {
	
	public static double A = (((double)GameManager.BOX_SIZE)/(Math.cos(0.785398)*2d));
	
	protected double startingX, startingY;
	
	protected double x, y;
	protected double r; //angle in radians
	protected Color color;
	
	private boolean customSize = false;
	private int size;
	private double customA;
	
	public Box(double x, double y, Color color) {
		this.x = x; this.y = y;
		this.startingX = x; this.startingY = y;
		this.color = color;
		r = 0;
		Manager.getGameManager().addRenderer(this);
		Manager.getGameManager().addShiftingObject(this);
	}
	
	public Box(double x, double y, Color color, int size) {
		this.x = x; this.y = y;
		this.startingX = x; this.startingY = y;
		this.color = color;
		customSize = true;
		this.size = size;
		customA = (((double)size)/(Math.cos(0.785398)*2d));
		r = 0;
		Manager.getGameManager().addRenderer(this);
		Manager.getGameManager().addShiftingObject(this);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setR(double r) {
		this.r = r;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getR() {
		return r;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setStartingX() {
		
	}
	
	public void setStartingY() {
		
	}
	
	public void drawCall(Graphics g) {
		g.setColor(color);
		if (customSize) {
			if (r == 0) {
				g.fillRect((int)x, (int)y, size, size);
			}else {
				double xOffset = x + ((double)size)/2;
				double yOffset = y + ((double)size)/2;
				int[] xPoints = new int[] {
						(int)((Math.cos(r+0.7853982)*customA) + xOffset),
						(int)((Math.cos(r+2.356194)*customA) + xOffset),
						(int)((Math.cos(r+3.926991)*customA) + xOffset),
						(int)((Math.cos(r+5.497787)*customA) + xOffset)
				};
				int[] yPoints = new int[] {
						(int)((Math.sin(r+0.7853982)*customA) + yOffset),
						(int)((Math.sin(r+2.356194)*customA) + yOffset),
						(int)((Math.sin(r+3.926991)*customA) + yOffset),
						(int)((Math.sin(r+5.497787)*customA) + yOffset)
				};
				g.fillPolygon(xPoints, yPoints, 4);
			}
		}else {
			if (r == 0) {
				g.fillRect((int)x, (int)y, GameManager.BOX_SIZE, GameManager.BOX_SIZE);
			}else {
				double xOffset = x + (double)(GameManager.BOX_SIZE/2);
				double yOffset = y + (double)(GameManager.BOX_SIZE/2);
				int[] xPoints = new int[] {
						(int)((Math.cos(r+0.7853982)*A) + xOffset),
						(int)((Math.cos(r+2.356194)*A) + xOffset),
						(int)((Math.cos(r+3.926991)*A) + xOffset),
						(int)((Math.cos(r+5.497787)*A) + xOffset)
				};
				int[] yPoints = new int[] {
						(int)((Math.sin(r+0.7853982)*A) + yOffset),
						(int)((Math.sin(r+2.356194)*A) + yOffset),
						(int)((Math.sin(r+3.926991)*A) + yOffset),
						(int)((Math.sin(r+5.497787)*A) + yOffset)
				};
				g.fillPolygon(xPoints, yPoints, 4);
			}
		}
		
		
	}
	
	public void pointTowards(double x, double y) {
		r = Math.atan2((y - (this.y+(double)(GameManager.BOX_SIZE/2))), (x - (this.x+(double)(GameManager.BOX_SIZE/2))));
	}
	
	public void Destroy() {
		Manager.getGameManager().removeRenderer(this);
		Manager.getGameManager().removeShiftingObject(this);
	}
}
