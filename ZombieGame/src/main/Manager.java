package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import editor.EditorManager;
import game.GameManager;

public class Manager {
	
	public static final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 750;
	public static final int BOX_SIZE = 40;
	public static final int FPS = 60;
	
	public static final double PLAYER_SPEED = 5;
	public static final double PLAYER_SPRINT_MULTIPLIER = 1.75;
	public static final double SLOW_ENEMY_SPEED = 2;
	
	public static Color GAME_BACKGROUND = Color.WHITE;
	public static final Color GAME_ENEMY = new Color(121, 63, 73);
	
	private static JFrame frame;
	private static GameManager gameManager;
	private static EditorManager editorManager;
	
	public static void main(String[] args) {
		frame = new JFrame("Zombie Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		gameManager = new GameManager();
		gameManager.generateMap();
		setPanel(gameManager);
		
		//editorManager = new EditorManager();
		//setPanel(editorManager);
		
		frame.setVisible(true);
	}
	
	public static void setPanel(JPanel panel) {
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
		frame.pack();
		frame.setLocationRelativeTo(null);
		panel.requestFocus();
	}
	
	public static GameManager getGameManager() {
		return gameManager;
	}
	
	public static JFrame getJFrame() {
		return frame;
	}
}