package menu;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Manager;

public class Label implements Render {
	
	private double x, y;
	private double width, height;
	private String text;
	private boolean drawBackground;
	
	public Label(String text, double x, double y, double width, double height, boolean drawBackground) {
		this.x = x; this.y = y;
		this.width = width; this.height = height;
		this.text = text;
		this.drawBackground = drawBackground;
	}
	
	public void drawCall(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		//Background
		if (drawBackground) {
			g2D.setColor(MenuManager.BUTTON_BACKGROUND);
			g2D.fillRect((int)((x-width/2)+Manager.WINDOW_WIDTH/2), (int)((y-height/2)+Manager.WINDOW_HEIGHT/2), (int)width, (int)height);
		}
		
		//finding font size
		g2D.setFont(MenuManager.MAIN_FONT);
		FontMetrics fontMetrics = g2D.getFontMetrics();
		while (fontMetrics.getHeight() < height-40) {
			g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize2D()+1f));
			fontMetrics = g2D.getFontMetrics();
		}
		g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize2D()-1f));
		fontMetrics = g2D.getFontMetrics();
		
		//Text
		g2D.setColor(MenuManager.BUTTON_TEXT);
		g2D.drawString(text, (int)((x-fontMetrics.stringWidth(text)/2)+Manager.WINDOW_WIDTH/2), (int)((y+fontMetrics.getAscent()/2)+Manager.WINDOW_HEIGHT/2));
	}
	
}
