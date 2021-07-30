package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.Manager;
import world.World;

public class GameManager extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private World world;
	private Timer gameLoop;
	
	private List<Box> boxes = new ArrayList<Box>();
	private List<Render> renders = new ArrayList<Render>();
	private List<PhysicsUpdate> physicsUpdaters = new ArrayList<PhysicsUpdate>();
	
	public GameManager() {
		setPreferredSize(new Dimension(Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT));
		gameLoop = new Timer(1000/Manager.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<physicsUpdaters.size(); i++) {
					physicsUpdaters.get(i).update();
				}
				repaint();
			}
		});
	}
	
	public void generateMap() {
		world = new World("default");
		gameLoop.start();
	}
	
	public void addRenderer(Render render) {
		renders.add(render);
	}
	
	public void addPhysicsUpdate(PhysicsUpdate physicsUpdate) {
		physicsUpdaters.add(physicsUpdate);
	}
	
	public void addBox(Box box) {
		boxes.add(box);
	}
	
	public void removeRenderer(Render render) {
		renders.remove(render);
	}
	
	public void removePhysicsUpdate(PhysicsUpdate physicsUpdate) {
		physicsUpdaters.remove(physicsUpdate);
	}
	
	public void removeBox(Box box) {
		boxes.remove(box);
	}
	
	public World getWorld() {
		return world;
	}
	
	public void shiftboxes(double xShift, double yShift) {
		for (int i=0; i<boxes.size(); i++) {
			boxes.get(i).setX(boxes.get(i).getX()+xShift);
			boxes.get(i).setY(boxes.get(i).getY()+yShift);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Manager.GAME_BACKGROUND);
		g.fillRect(0, 0, Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT);
		for (int i=0; i<renders.size(); i++) {
			renders.get(i).drawCall(g);
		}
	}
	
}
