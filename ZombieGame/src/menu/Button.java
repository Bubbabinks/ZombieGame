package menu;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Manager;

public class Button implements Render {
	
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
	
	private double x, y;
	private double width, height;
	private String text;
	
	private ActionEvent actionEvent;
	
	public Button(String text, double x, double y, double width, double height, JPanel panel) {
		this.x = x; this.y = y;
		this.width = width; this.height = height;
		this.text = text;
		actionEvent = new ActionEvent(this, 0, text);
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (e.getButton() == MouseEvent.BUTTON1) {
					Point point = e.getPoint();
					if (point.x < (int)((x+width/2)+Manager.WINDOW_WIDTH/2) && point.x > (int)((x-width/2)+Manager.WINDOW_WIDTH/2)) {
						if (point.y < (int)((y+height/2)+Manager.WINDOW_HEIGHT/2) && point.y > (int)((y-height/2)+Manager.WINDOW_HEIGHT/2)) {
							for (ActionListener actionListener: actionListeners) {
								actionListener.actionPerformed(actionEvent);
							}
						}
					}
				}
			}
		});
	}
	
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	
	public void drawCall(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		//Background
		g2D.setColor(MenuManager.BUTTON_BACKGROUND);
		g2D.fillRect((int)((x-width/2)+Manager.WINDOW_WIDTH/2), (int)((y-height/2)+Manager.WINDOW_HEIGHT/2), (int)width, (int)height);
		
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
