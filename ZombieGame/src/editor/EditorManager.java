package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.Manager;
import world.FileManager;

public class EditorManager extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JLabel[][] grid;
	
	private ImageIcon brushIcon;
	private ImageIcon backgroundIcon;
	private ImageIcon emptyIcon;
	private ImageIcon colliderIcon;
	private ImageIcon playerSpawnerIcon;
	private ImageIcon enemySpawnerIcon;
	
	private Color backgroundColor = Color.WHITE;
	
	private int mapWidth;
	private int mapHeight;
	
	private Brush currentTool = Brush.paintBrush;
	
	private enum Brush {
		paintBrush,
		collider,
		playerSpawn,
		enemySpawner;
	}
	
	public EditorManager() {
		try {
			brushIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/brush.png")));
			emptyIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/empty.png")));
			backgroundIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/background.png")));
			colliderIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/collider.png")));
			playerSpawnerIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/playerSpawn.png")));
			enemySpawnerIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("editorPictures/EnemySpawn.png")));
		} catch (IOException e2) {}
		setPreferredSize(new Dimension(Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT));
		setLayout(new GridBagLayout());
		
		boolean successful = false;
		mapWidth = 0;
		while (!successful) {
			try {
				mapWidth = Integer.parseInt(JOptionPane.showInputDialog(Manager.getJFrame(), "Map Width (a number)"));
				successful = true;
			}catch (Exception e) {}
		}
		
		successful = false;
		mapHeight = 0;
		while (!successful) {
			try {
				mapHeight = Integer.parseInt(JOptionPane.showInputDialog(Manager.getJFrame(), "Map Height (a number)"));
				successful = true;
			}catch (Exception e) {}
		}
		
		grid = new JLabel[mapWidth][mapHeight];
		
		GridBagConstraints GC = new GridBagConstraints();
		JPanel toolsPanel = new JPanel();
		toolsPanel.setBackground(Color.LIGHT_GRAY);
		toolsPanel.setPreferredSize(new Dimension(100,449));
		toolsPanel.setLayout(new GridLayout(10,1));
		JLabel brushLabel = new JLabel(brushIcon);
		brushLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				currentTool = Brush.paintBrush;
			}
		});
		toolsPanel.add(brushLabel);
		JLabel colliderLabel = new JLabel(colliderIcon);
		colliderLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				currentTool = Brush.collider;
			}
		});
		toolsPanel.add(colliderLabel);
		JLabel playerSpawnerLabel = new JLabel(playerSpawnerIcon);
		playerSpawnerLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				currentTool = Brush.playerSpawn;
			}
		});
		toolsPanel.add(playerSpawnerLabel);
		JLabel enemySpawner = new JLabel(enemySpawnerIcon);
		enemySpawner.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				currentTool = Brush.enemySpawner;
			}
		});
		toolsPanel.add(enemySpawner);
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				Color newColor = JColorChooser.showDialog(Manager.getJFrame(), "Background Color Picker", backgroundColor);
				if (newColor != null) {
					backgroundColor = newColor;
					for (int x=0; x<grid.length; x++) {
						for (int y=0; y<grid[0].length; y++) {
							if (grid[x][y].getIcon() == emptyIcon) {
								grid[x][y].setBackground(newColor);
							}
						}
					}
				}
			}
		});
		toolsPanel.add(backgroundLabel);
		JLabel saveButton = new JLabel("Save & Close", SwingConstants.CENTER);
		saveButton.setOpaque(true);
		saveButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				FileManager.init();
				ArrayList<String> boxes = new ArrayList<String>();
				ArrayList<String> colliders = new ArrayList<String>();
				ArrayList<String> enemySpawn = new ArrayList<String>();
				int[] playerSpawn = new int[2];
				for (int x=0; x<grid.length; x++) {
					for (int y=0; y<grid[0].length; y++) {
						if ((ImageIcon)grid[x][y].getIcon() != emptyIcon) {
							Color color = grid[x][y].getBackground();
							boxes.add("0,"+x+","+y+",0.0,"+color.getRed()+","+color.getGreen()+","+color.getBlue());
						}
						if ((ImageIcon)grid[x][y].getIcon() == colliderIcon) {
							colliders.add("1,"+x+","+y);
						}
						if ((ImageIcon)grid[x][y].getIcon() == playerSpawnerIcon) {
							playerSpawn[0] = x;
							playerSpawn[1] = y;
						}
						if ((ImageIcon)grid[x][y].getIcon() == enemySpawnerIcon) {
							enemySpawn.add("4,"+x+","+y);
						}
					}
				}
				boolean successful = false;
				while (!successful) {
					String name = JOptionPane.showInputDialog(Manager.getJFrame(), "Enter world name");
					if (name == null) {
						return;
					}
					successful = FileManager.saveNewMap(name, boxes, backgroundColor, colliders, mapWidth, mapHeight, enemySpawn, playerSpawn);
				}
				
				System.exit(0);
			}
		});
		toolsPanel.add(saveButton);
		add(toolsPanel, GC);
		
		GC = new GridBagConstraints();
		GC.gridx = 1;
		JPanel editorPanel = new JPanel();
		editorPanel.setBackground(Color.WHITE);
		editorPanel.setLayout(new GridLayout(mapHeight, mapWidth));
		JScrollPane scrollPane = new JScrollPane(editorPanel);
		scrollPane.setPreferredSize(new Dimension(800, 449));
		add(scrollPane, GC);
		
		GC = new GridBagConstraints();
		GC.gridwidth = 2;
		GC.gridy = 1;
		JColorChooser colorChooser = new JColorChooser(Color.GRAY);
		colorChooser.setPreferredSize(new Dimension(900,300));
		add(colorChooser, GC);
		
		for (int y=0; y<mapHeight; y++) {
			for (int x=0; x<mapWidth; x++) {
				grid[x][y] = new JLabel();
				grid[x][y].setPreferredSize(new Dimension(30,30));
				grid[x][y].setOpaque(true);
				grid[x][y].setBackground(backgroundColor);
				grid[x][y].setIcon(emptyIcon);
				editorPanel.add(grid[x][y]);
				grid[x][y].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						super.mousePressed(e);
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (currentTool == Brush.paintBrush) {
								JLabel label = (JLabel)e.getSource();
								if ((ImageIcon)label.getIcon() == emptyIcon) {
									label.setIcon(null);
									label.revalidate();
								}
								label.setBackground(colorChooser.getColor());
								label.repaint();
							}else if (currentTool == Brush.collider) {
								JLabel label = (JLabel)e.getSource();
								label.setIcon(colliderIcon);
								label.revalidate();
							}else if (currentTool == Brush.playerSpawn) {
								for (int x=0; x<grid.length; x++) {
									for (int y=0; y<grid[0].length; y++) {
										if (grid[x][y].getIcon() == playerSpawnerIcon) {
											if (grid[x][y].getBackground().equals(backgroundColor)) {
												grid[x][y].setIcon(emptyIcon);
												grid[x][y].revalidate();
											} else {
												grid[x][y].setIcon(null);
												grid[x][y].revalidate();
											}
										}
									}
								}
								JLabel label = (JLabel)e.getSource();
								label.setIcon(playerSpawnerIcon);
								label.revalidate();
							}else if (currentTool == Brush.enemySpawner) {
								JLabel label = (JLabel)e.getSource();
								label.setIcon(enemySpawnerIcon);
								label.revalidate();
							}
						}else if (e.getButton() == MouseEvent.BUTTON3) {
							JLabel label = (JLabel)e.getSource();
							label.setIcon(emptyIcon);
							label.setBackground(backgroundColor);
						}
						
					}
				});
			}
		}
	}
	
}
