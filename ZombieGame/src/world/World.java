package world;

import game.Player;
import main.Manager;

public class World {
	
	private Player player;
	private Map map;
	
	public World(String worldName) {
		FileManager.init();
		map = new Map(worldName);
		player = new Player(Manager.WINDOW_WIDTH/2-Manager.BOX_SIZE/2, Manager.WINDOW_HEIGHT/2-Manager.BOX_SIZE/2);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	protected void setPlayer(Player player) {
		this.player = player;
	}
	
	public Map getMap() {
		return map;
	}
}
