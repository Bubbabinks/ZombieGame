package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import editor.EditorManager;
import main.Manager;

public class MenuManager extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final Color BACKGROUND = Color.WHITE;
	public static final Color BUTTON_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color BUTTON_TEXT = Color.BLACK;
	public static Font MAIN_FONT;
	
	private ArrayList<Render> renders = new ArrayList<Render>();
	
	private MenuManager menuManager = this;
	
	public MenuManager() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT));
		try {
			MAIN_FONT = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("fonts/ATTACKGRAFFITI-3ZRBM.TTF"));
			MAIN_FONT = MAIN_FONT.deriveFont(12f);
		} catch (FontFormatException e) {} catch (IOException e) {}
		
		Button playButton = new Button("Play", 0, 0, 200, 75, this);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renders.removeAll(renders);
				JScrollPane scrollPane = new JScrollPane(new WorldPanel());
				add(scrollPane, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		renders.add(playButton);
		
		Button editorButton = new Button("Map Editor", 0, 95, 200, 75, this);
		editorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manager.setEditorManager(new EditorManager());
				if (Manager.getMenuManager() == menuManager) {
					Manager.setPanel(Manager.getEditorManager());
				}
			}
		});
		renders.add(editorButton);
		
		Label title = new Label("Zombies", 0, -187.5, 200, 200, false);
		renders.add(title);
		
		repaint();
	}
	
	public void addRenderer(Render render) {
		renders.add(render);
	}
	
	public void removeRenderer(Render render) {
		renders.remove(render);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, Manager.WINDOW_WIDTH, Manager.WINDOW_HEIGHT);
		for (int i=0; i<renders.size(); i++) {
			renders.get(i).drawCall(g);
		}
	}
	
}
