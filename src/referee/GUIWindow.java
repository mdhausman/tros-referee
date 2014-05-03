/**
 * @author Maura Hausman 2012
 * 
 *         This class defines the window in which the GUI is housed.
 */
package referee;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

class GUIWindow extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L; // parent appeasement
	
	private Dice diceBag;
	
	public GUIWindow() {
		this.diceBag = new Dice();
		this.setTitle("Referee");
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e){
			System.out.println("Error setting native LAF: " + e);
		}
		/*
		 * This object is for reading the damage table. It needs the type of
		 * damage, the level of damage, the location of the wound, and the level of
		 * accuracy (which determines the number of wounds to display).
		 */
		DamageFinder damageFunction = new DamageFinder(diceBag);
		
		/*
		 * This object is for rolling tests. It takes the number of dice and the
		 * target number, then rolls them when the roll button is pressed, and
		 * presents the result.
		 */
		TestRoller diceRoller = new TestRoller(diceBag);
		
		this.add(diceRoller, BorderLayout.PAGE_START);
		this.add(damageFunction, BorderLayout.PAGE_END);
	}
	
	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}
	
	// interface appeasement methods
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	
}
