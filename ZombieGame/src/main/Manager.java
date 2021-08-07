package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import editor.EditorManager;
import game.GameManager;
import menu.MenuManager;

public class Manager {
	
	public static final int WINDOW_WIDTH = 900, WINDOW_HEIGHT = 750;
	
	public static Color GAME_BACKGROUND = Color.WHITE;
	
	private static JFrame frame;
	private static GameManager gameManager;
	private static EditorManager editorManager;
	private static MenuManager menuManager;
	
	public static void main(String[] args) {
		frame = new JFrame("Zombies");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		menuManager = new MenuManager();
		setPanel(menuManager);
		
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
	
	public static MenuManager getMenuManager() {
		return menuManager;
	}
	
	public static EditorManager getEditorManager() {
		return editorManager;
	}
	
	public static void setGameManager(GameManager gameManager) {
		Manager.gameManager = gameManager;
	}
	
	public static void setEditorManager(EditorManager editorManager) {
		Manager.editorManager = editorManager;
	}
	
	public static void setMenuManager(MenuManager menuManager) {
		Manager.menuManager = menuManager;
	}
	
	public static JFrame getJFrame() {
		return frame;
	}
}
