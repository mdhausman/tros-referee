package referee;

/**
 * @author Matthew Hausman 2012
 * 
 *         This is the runnable class. This is where the GUI Window is built
 *         and displayed.
 */

public class Referee {
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIWindow window = new GUIWindow();
				window.pack();
				window.setVisible(true);
			}
		});
		
	}
	
}
