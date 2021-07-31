package menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import game.GameManager;
import main.Manager;
import world.FileManager;

public class WorldPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public WorldPanel() {
		setBackground(MenuManager.BACKGROUND);
		setLayout(new GridBagLayout());
		
		FileManager.init();
		HashMap<String, ArrayList<String>> worlds = FileManager.getWorlds();
		GridBagConstraints GC;
		int y = 0;
		for (String name: worlds.keySet()) {
			GC = new GridBagConstraints();
			GC.gridy = y;
			GC.anchor = GridBagConstraints.CENTER;
			GC.weightx = 1;
			GC.weighty = 1;
			GC.insets = new Insets(10, 10, 10, 10);
			LButton button = new LButton(name);
			button.setPreferredSize(new Dimension(300, 75));
			button.setOpaque(true);
			button.setBackground(MenuManager.BUTTON_BACKGROUND);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Manager.setMenuManager(null);
					Manager.setGameManager(new GameManager(e.getActionCommand()));
					Manager.getGameManager().generateMap();
					Manager.setPanel(Manager.getGameManager());
				}
			});
			y++;
			add(button, GC);
		}
	}
	
}
