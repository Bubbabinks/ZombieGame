package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LButton extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
	private ActionEvent actionEvent;

	public LButton(String text) {
		super(text, SwingConstants.CENTER);
		setFont(MenuManager.MAIN_FONT);
		addMouseListener(this);
		actionEvent = new ActionEvent(this, 0, text);
	}
	
	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	
	public void mousePressed(MouseEvent e) {
		for (ActionListener actionListener: actionListeners) {
			actionListener.actionPerformed(actionEvent);
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
