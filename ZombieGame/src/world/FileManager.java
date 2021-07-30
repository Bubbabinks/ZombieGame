package world;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.Manager;

public class FileManager {
	
	private static final String MAIN_FOLDER = System.getProperty("user.home") + "/Documents/ZombieGame";
	private static final String WORLDS_FOLDER = MAIN_FOLDER + "/Worlds";
	
	private static final String IMPROPER_CHARS = "#%&{}\\<>*?/ $!\'\":@+`|=";
	
	private static HashMap<String, ArrayList<String>> worlds = new HashMap<String, ArrayList<String>>();
	
	private static boolean initHasRan = false;
	
	public static void init() {
		if (!initHasRan) {
			File currentFile = new File(MAIN_FOLDER);
			if (!currentFile.exists()) {
				currentFile.mkdir();
			}
			currentFile = new File(WORLDS_FOLDER);
			if (!currentFile.exists()) {
				currentFile.mkdir();
			}
			if (currentFile.listFiles().length == 0) {
				currentFile = new File(WORLDS_FOLDER+"/default");
				currentFile.mkdir();
				try {
					Files.copy(Manager.getGameManager().getClass().getClassLoader().getResourceAsStream("default_world/0.map"), new File(currentFile.toPath()+"/0.map").toPath(), (CopyOption)StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {}
				currentFile = new File(WORLDS_FOLDER);
			}
			for (File file: currentFile.listFiles()) {
				File mapFile = new File(file.toString()+"/0.map");
				if (file.isDirectory()) {
					if (mapFile.exists()) {
						ArrayList<String> lines = new ArrayList<String>();
						try {
							BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));
							String line = "";
							while ((line = reader.readLine()) != null) {
								lines.add(line);
							}
							reader.close();
						} catch (FileNotFoundException e) {} catch (IOException e) {}
						worlds.put(lines.get(0), lines);
					}
				}
				
			}
			initHasRan = true;
		}
	}
	
	public static boolean saveNewMap(String name, ArrayList<String> boxes, Color backgroundColor, ArrayList<String> colliders, int mapWidth, int mapHeight, ArrayList<String> enemySpawns, int[] playerSpawn) {
		if (Arrays.asList(new File(WORLDS_FOLDER).list()).contains(fixStringForFileName(name)) || worlds.containsKey(name)) {
			return false;
		}
		File currentFile = new File(WORLDS_FOLDER+"/"+fixStringForFileName(name));
		currentFile.mkdir();
		currentFile = new File(currentFile.toString()+"/0.map");
		try {
			FileWriter writer = new FileWriter(currentFile);
			writer.write(name+"\n");
			writer.write((mapWidth+","+mapHeight+"\n"));
			for (String string: boxes) {
				writer.write(string+"\n");
			}
			for (String string: colliders) {
				writer.write(string+"\n");
			}
			writer.write("2,"+backgroundColor.getRed()+","+backgroundColor.getGreen()+","+backgroundColor.getBlue()+"\n");
			writer.write("3,"+playerSpawn[0]+","+playerSpawn[1]+"\n");
			for (String string: enemySpawns) {
				writer.write(string+"\n");
			}
			writer.close();
		} catch (IOException e) {}
		
		return true;
	}
	
	public static HashMap<String, ArrayList<String>> getWorlds() {
		return worlds;
	}
	
	public static ArrayList<String> getWorld(String name) {
		return worlds.get(name);
	}
	
	public static String fixStringForFileName(String original) {
		String fixed = original;
		for (char c: IMPROPER_CHARS.toCharArray()) {
			fixed = fixed.replace(c, ' ');
		}
		fixed = fixed.toLowerCase();
		return fixed;
	}
	
	
}
