package world;

import java.awt.Color;
import java.util.ArrayList;

import game.Box;
import game.Player;
import main.Manager;

public class Map {
	
	private ArrayList<String> rawInputMap = new ArrayList<String>();
	private String worldName;
	
	private Box[][] boxMap;
	
	public Map(String worldName) {
		this.worldName = worldName;
		rawInputMap = FileManager.getWorld(worldName);
		if (rawInputMap != null) {
			Manager.getJFrame().setTitle("Zombie Game (" + worldName + ")");
			String[] rawWidthHeight = rawInputMap.get(1).split(",");
			boxMap = new Box[Integer.parseInt(rawWidthHeight[0])][Integer.parseInt(rawWidthHeight[1])];
			ArrayList<String[]> queueBoxGeneration = new ArrayList<String[]>();
			int[] startingLocation = new int[2];
			for (int i=2; i<rawInputMap.size(); i++) {
				String[] brokenInput = rawInputMap.get(i).split(",");
				if (brokenInput[0].equals("2")) {
					Manager.GAME_BACKGROUND = new Color(Integer.parseInt(brokenInput[1]), Integer.parseInt(brokenInput[2]), Integer.parseInt(brokenInput[3]));
				}
				if (brokenInput[0].equals("0")) {
					queueBoxGeneration.add(brokenInput);
				}
				if (brokenInput[0].equals("3")) {
					startingLocation[0] = Integer.parseInt(brokenInput[1]);
					startingLocation[1] = Integer.parseInt(brokenInput[2]);
				}
			}
			for (String[] array: queueBoxGeneration) {
				int x = Integer.parseInt(array[1]);
				int y = Integer.parseInt(array[2]);
				double r = Double.parseDouble(array[3]);
				Color color = new Color(Integer.parseInt(array[4]), Integer.parseInt(array[5]), Integer.parseInt(array[6]));
				Box box = new Box((Manager.WINDOW_WIDTH/2 + (x - startingLocation[0])*Manager.BOX_SIZE) - (Manager.BOX_SIZE/2),
						(Manager.WINDOW_HEIGHT/2 + (y - startingLocation[1])*Manager.BOX_SIZE) - (Manager.BOX_SIZE/2), color);
				box.setR(r);
				boxMap[x][y] = box;
			}
			
		}
		
	}
	
}
