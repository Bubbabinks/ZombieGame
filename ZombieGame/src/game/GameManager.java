package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.Manager;
import menu.Button;
import menu.MenuManager;
import world.World;

public class GameManager extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String worldName;
	
	private World world;
	private Timer gameLoop;
	
	private boolean excapePressed = false;
	private boolean pause = false;
	
	private List<Box> boxes = new ArrayList<Box>();
	private List<Render> renders = new ArrayList<Render>();
	private List<menu.Render> pauseMenu = new ArrayList<menu.Render>();
	private List<PhysicsUpdate> physicsUpdaters = new ArrayList<PhysicsUpdate>();
	
	public GameManager(String worldName) {
		this.worldName = worldName;
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (!excapePressed) {
						if (pause) {
							gameLoop.restart();
						}else {
							gameLoop.stop();
						}
						pause = !pause;
						excapePressed = true;
						repaint();
					}
				}
			}
			
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					excapePressed = false;
				}
			}
		});
		setPreferredSize(new Dimension(Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT));
		gameLoop = new Timer(1000/Manager.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<physicsUpdaters.size(); i++) {
					physicsUpdaters.get(i).update();
				}
				repaint();
			}
		});
		
		Button resumeButton = new Button("Resume", 0, -52.5, 250, 75, this);
		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pause) {
					gameLoop.restart();
					pause = false;
				}
			}
		});
		pauseMenu.add(resumeButton);
		
		Button quitButton = new Button("Quit", 0, 52.5, 250, 75, this);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pause) {
					gameLoop.stop();
					Manager.setGameManager(null);
					Manager.setMenuManager(new MenuManager());
					Manager.setPanel(Manager.getMenuManager());
				}
			}
		});
		pauseMenu.add(quitButton);
	}
	
	public void generateMap() {
		world = new World(worldName);
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
		if (pause) {
			for (int i=0; i<pauseMenu.size(); i++) {
				pauseMenu.get(i).drawCall(g);
			}
		}
	}
	
}
